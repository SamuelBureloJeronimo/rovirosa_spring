package com.rovirosa.rovirosa_spring.controllers.public_routes;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

@RestController
@RequestMapping("/api/ocr")
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
            Pattern curpPattern = Pattern.compile("\\b([A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]{2})\\b");
            Pattern fechaPattern = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[/-](0[1-9]|1[0-2])[/-](19|20)\\d{2}");

            Matcher curpMatcher = curpPattern.matcher(cleanText);
            Matcher fechaMatcher = fechaPattern.matcher(cleanText);

            String curp = curpMatcher.find() ? curpMatcher.group() : "";
            String fechaNacimiento = fechaMatcher.find() ? fechaMatcher.group() : "";

            String sexo = "";
            Pattern sexoPattern = Pattern.compile("SEXO\\s*([HF])", Pattern.CASE_INSENSITIVE);
            Matcher sexoMatcher = sexoPattern.matcher(cleanText);
            if (sexoMatcher.find())
                sexo = sexoMatcher.group(1).toUpperCase();

            String apellidoP = "", apellidoM = "", nombre = "";

            // Buscar la línea de "NOMBRE"
            Pattern nombreLinePattern = Pattern.compile("NOMBRE\\s+([A-ZÁÉÍÓÚÑ ]+?)(?=DOMICILIO|CLAVE|CURP|FECHA|SECCION|VIGENCIA|$)");
            Matcher nombreLineMatcher = nombreLinePattern.matcher(cleanText);
            if (nombreLineMatcher.find()) {
                String fullName = nombreLineMatcher.group(1).trim();
                String[] parts = fullName.split("\\s+");

                // Lista de partículas para apellidos compuestos
                Set<String> particles = Set.of("DE", "DEL", "LA", "LOS", "LAS", "SAN", "SANTA");

                List<String> tokens = new ArrayList<>();
                for (int i = 0; i < parts.length; i++) {
                    String token = parts[i];
                    // Si es partícula, une con el anterior
                    if (particles.contains(token) && i + 1 < parts.length) {
                        tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + " " + token + " " + parts[i + 1]);
                        i++; // saltar el siguiente
                    } else {
                        tokens.add(token);
                    }
                }

                // Heurística según número de bloques
                if (tokens.size() >= 3) {
                    apellidoP = tokens.get(0);
                    apellidoM = tokens.get(1);
                    nombre = String.join(" ", tokens.subList(2, tokens.size()));
                } else if (tokens.size() == 2) {
                    apellidoP = tokens.get(0);
                    nombre = tokens.get(1);
                } else if (tokens.size() == 1) {
                    apellidoP = tokens.get(0);
                }
            }

            Map<String, String> result = new HashMap<>();
            result.put("curp", curp);
            result.put("sexo", sexo);
            result.put("fechaNacimiento", fechaNacimiento);
            result.put("apellidoP", apellidoP);
            result.put("apellidoM", apellidoM);
            result.put("nombre", nombre);

            System.out.println(result);
            if (curp.isEmpty() && nombre.isEmpty() && apellidoM.isEmpty() && apellidoP.isEmpty() && fechaNacimiento.isEmpty()) {
                return (ResponseEntity<?>) ResponseEntity.badRequest();
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error procesando INE: " + e.getMessage());
        }
    }

    @PostMapping("/ine-reverso")
    public ResponseEntity<?> processINEReverso(@RequestParam("ine") MultipartFile file) {
        try {
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

            String qrText = "";
            try {
                Result result = new MultiFormatReader().decode(bitmap);
                qrText = result.getText();
            } catch (Exception e) {
                qrText = "";
            }

            // Parseo
            Map<String, String> parsed = parseINE(text);

            if (parsed.isEmpty() || qrText.isEmpty())
                return (ResponseEntity<?>) ResponseEntity.badRequest();

            // Respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("qr_text", qrText);
            response.putAll(parsed);

            System.out.println(response);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();

        }
    }

    private Map<String, String> parseINE(String ocrText) {
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
