package com.rovirosa.rovirosa_spring.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

@RestController
@RequestMapping("/api/v1/ocr")
public class OcrController {

    @PostMapping("/ine")
    public ResponseEntity<?> processINE_2(@RequestParam("ine") MultipartFile file)
            throws IOException, InterruptedException {
        // Guardar archivo temporal
        File tempFile = File.createTempFile("ine_", ".jpeg");
        file.transferTo(tempFile);

        // Archivo de salida temporal
        File outputFile = File.createTempFile("ocr_output_", ".txt");

        // Construir comando Tesseract (igual que en terminal)
        String[] cmd = {
                "tesseract",
                tempFile.getAbsolutePath(),
                outputFile.getAbsolutePath().replaceAll("\\.txt$", ""), // tesseract agrega .txt automáticamente
                "-l", "spa"
        };

        // Ejecutar comando
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true); // unifica salida de error
        Process process = pb.start();
        process.waitFor();

        try {

            // Leer resultado
            String text = Files.readString(outputFile.toPath());
            String cleanText = text.replaceAll("\\s+", " ").toUpperCase();
            System.out.println(cleanText);

            // Expresiones regulares
            Pattern curpPattern = Pattern.compile("\\b[A-Z0-9]{18}\\b");
            Pattern fechaPattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");

            Matcher curpMatcher = curpPattern.matcher(cleanText.substring(220));
            Matcher fechaMatcher = fechaPattern.matcher(cleanText);

            String curp = curpMatcher.find() ? curpMatcher.group() : "";
            String fechaNacimiento = fechaMatcher.find() ? fechaMatcher.group() : "";

            String sexo = "";
            Pattern sexoPattern = Pattern.compile("SEXO\\s*([HF])", Pattern.CASE_INSENSITIVE);
            Matcher sexoMatcher = sexoPattern.matcher(cleanText);
            if (sexoMatcher.find())
                sexo = sexoMatcher.group(1).toUpperCase();

            // Buscar la sección del nombre
            String apellidoP = "";
            String apellidoM = "";
            String nombre = "";

            String[] lineas = text.split("\n");
            for (int i = 0; i < lineas.length; i++) {
                if (lineas[i].toUpperCase().contains("NOMBRE")) {
                    if (i + 1 < lineas.length)
                        apellidoP = lineas[i + 1].trim();
                    if (i + 2 < lineas.length)
                        apellidoM = lineas[i + 2].trim();
                    if (i + 3 < lineas.length)
                        nombre = lineas[i + 3].trim();
                    break;
                }
            }

            Map<String, String> result = new HashMap<>();
            result.put("curp", curp);
            result.put("sexo", sexo);
            result.put("fechaNacimiento", fechaNacimiento);
            result.put("apellidoP", apellidoP);
            result.put("apellidoM", apellidoM);
            result.put("nombre", nombre);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error procesando INE: " + e.getMessage());
        }
    }

    @PostMapping("/ine-reverso")
    public ResponseEntity<?> processINEReverso(@RequestParam("ine") MultipartFile file)
            throws IOException, InterruptedException {
        // Guardar archivo temporal
        File tempFile = File.createTempFile("ine_", ".jpeg");
        file.transferTo(tempFile);

        // Archivo de salida temporal
        File outputFile = File.createTempFile("ocr_output_", ".txt");

        // Ejecutar tesseract
        String[] cmd = {
                "tesseract",
                tempFile.getAbsolutePath(),
                outputFile.getAbsolutePath().replaceAll("\\.txt$", ""),
                "-l", "spa" // mejor para MRZ
        };
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();

        // Leer OCR sin quitar saltos de línea
        String text = Files.readString(outputFile.toPath()).toUpperCase().trim();
        System.out.println("OCR CRUDO: " + text);

        // === Lectura QR ===
        BufferedImage bufferedImage = ImageIO.read(tempFile);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        String qrText;
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            qrText = result.getText();
        } catch (Exception e) {
            qrText = "";
        }

        // Parseo
        Map<String, String> parsed = parseINE(text);

        // Respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("qr_text", qrText);
        response.putAll(parsed);

        return ResponseEntity.ok(response);
    }

    public Map<String, String> parseINE(String ocrText) {
        Map<String, String> data = new HashMap<>();

        // Dividir por líneas (no eliminar \n en el cleanText)
        String[] lines = ocrText.split("\\r?\\n");

        String mrzLine1 = null;
        String mrzLine2 = null;

        // Detectar las dos últimas líneas con "<"
        for (String line : lines) {
            line = line.trim();
            if (line.contains("<")) {
                if (mrzLine1 == null) {
                    mrzLine1 = line;
                } else {
                    mrzLine2 = line;
                }
            }
        }

        // Parseo de MRZ 1 (datos técnicos)
        if (mrzLine1 != null) {
            // Ejemplo: 0308061H3112319MEX<00<<11563<5
            if (mrzLine1.length() > 15) {
                char sexo = mrzLine1.charAt(7); // posición típica
                String nacionalidad = mrzLine1.substring(13, 16).replaceAll("[^A-Z]", "");
                data.put("sexo", String.valueOf(sexo));
                data.put("nacionalidad", nacionalidad);
            }
        }

        // Parseo de MRZ 2 (nombres y apellidos)
        if (mrzLine2 != null) {
            String[] parts = mrzLine2.split("<+");
            if (parts.length >= 3) {
                data.put("apellido_paterno", parts[0].replaceAll("[^A-ZÑ]", ""));
                data.put("apellido_materno", parts[1].replaceAll("[^A-ZÑ]", ""));
                data.put("nombre", parts[2].replaceAll("[^A-ZÑ]", ""));
            }
        }

        return data;
    }

}
