package com.rovirosa.rovirosa_spring.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.rovirosa.rovirosa_spring.DTOs.ApiResponse;
import com.rovirosa.rovirosa_spring.DTOs.Ocr.OcrFrontDTO;

@Service
public class OcrService {

    public ApiResponse<Map<String, Object>> validateIneFront(OcrFrontDTO dto, MultipartFile file)
            throws IOException, InterruptedException {
        // Guardar archivo temporal
        File tempFile = File.createTempFile("ine_", ".jpeg");
        file.transferTo(tempFile);

        // Archivo de salida temporal
        File outputFile = File.createTempFile("ocr_output_", ".txt");

        // Ejecutar Tesseract
        String[] cmd = {
                "tesseract",
                tempFile.getAbsolutePath(),
                outputFile.getAbsolutePath().replaceAll("\\.txt$", ""),
                "-l", "spa"
        };

        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();

        try {
            // Leer y limpiar el texto OCR
            String text = Files.readString(outputFile.toPath());
            String cleanText = text.replaceAll("\\s+", " ").toUpperCase();
            System.out.println("🔍 OCR result: " + cleanText);

            // Resultado de validación
            Map<String, Object> validation = new LinkedHashMap<>();
            int matchesFound = 0;
            boolean allMatch = true;

            // 🔹 Validar CURP y derivar fecha desde ella
            if (dto.getCurp() != null && !dto.getCurp().isEmpty()) {
                String target = dto.getCurp().toUpperCase();

                boolean curpMatch = text.contains(target);

                validation.put("curp", curpMatch ? "✔ Encontrada" : "❌ No encontrada");

                // Si no se encuentra exacta, buscar subcadenas parecidas (OCR difuso)
                if (!curpMatch && text.length() > target.length()) {
                    for (int i = 0; i <= text.length() - target.length(); i++) {
                        String candidate = text.substring(i, Math.min(i + target.length(), text.length()));
                        double similarity = similarityRatio(target, candidate);
                        if (similarity >= 0.50) { // 50% de similitud
                            curpMatch = true;
                            validation.put("curp",
                                    "⚠ Similar encontrada: " + candidate + " (" + (int) (similarity * 100) + "%)");
                            System.out.println("🔍 CURP similar encontrada: " + candidate + " con similitud "
                                    + (similarity * 100) + "%");
                            break;
                        }
                    }
                }

                if (!curpMatch) {
                    allMatch = false;
                } else {
                    // Derivar fecha de nacimiento desde la CURP
                    String curp = dto.getCurp().toUpperCase();
                    if (curp.length() >= 10) {
                        String yy = curp.substring(4, 6);
                        String mm = curp.substring(6, 8);
                        String dd = curp.substring(8, 10);

                        int year = Integer.parseInt(yy);
                        year += (year <= 24 ? 2000 : 1900);
                        String fechaDerivada = String.format("%02d/%02d/%04d",
                                Integer.parseInt(dd), Integer.parseInt(mm), year);

                        validation.put("fechaNacimiento (derivada)", fechaDerivada);

                        if (dto.getFechaNac() != null && !dto.getFechaNac().isEmpty()) {
                            LocalDate fechaDto = LocalDate.parse(dto.getFechaNac().substring(0, 10));
                            String fechaDtoStr = fechaDto.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                            System.out.println("🔍 Fecha de Nacimiento buscada: " + fechaDtoStr +
                                    " | Derivada de CURP: " + fechaDerivada);

                            boolean coincide = fechaDtoStr.equals(fechaDerivada);
                            validation.put("fechaCoincideConCurp", coincide ? "✔ Sí coincide" : "❌ No coincide");
                            if (!coincide)
                                allMatch = false;
                            else
                                matchesFound++;
                        }
                    }
                }
            }

            // 🔹 Validar Apellido Paterno con regex flexible
            if (dto.getApp() != null && !dto.getApp().isEmpty()) {

                boolean match = containsFlexible(cleanText, dto.getApp());

                validation.put("apellidoP", match ? "✔ Encontrado" : "❌ No encontrado");

                if (!match)
                    allMatch = false;
                else
                    matchesFound++;
            }

            // 🔹 Validar Apellido Materno permitiendo nombres compuestos
            if (dto.getApm() != null && !dto.getApm().isEmpty()) {

                boolean match = containsFlexible(cleanText, dto.getApm());

                validation.put("apellidoM", match ? "✔ Encontrado" : "❌ No encontrado");

                if (!match)
                    allMatch = false;
                else
                    matchesFound++;

            }

            // 🔹 Validar Nombre(s) con regex flexible
            if (dto.getNombre() != null && !dto.getNombre().isEmpty()) {

                boolean match = containsFlexible(cleanText, dto.getNombre());

                validation.put("nombre", match ? "✔ Encontrado" : "❌ No encontrado");

                if (!match)
                    allMatch = false;
                else
                    matchesFound++;
            }

            // 🔹 Resultado general
            if (allMatch)
                return new ApiResponse<>(true, "Todos los datos coinciden", validation);
            else if (matchesFound >= 3)
                return new ApiResponse<>(true, "Algunos datos coinciden", validation);
            else
                return new ApiResponse<>(false, "No todos los datos coinciden", validation);

        } catch (Exception e) {
            return new ApiResponse<>(false, "Error procesando INE: " + e.getMessage(), null);
        } finally {
            // Eliminar archivos temporales
            tempFile.delete();
            outputFile.delete();
        }
    }

    public ApiResponse<Map<String, Object>> validateReverso(OcrFrontDTO dto, MultipartFile file) {
        // Limpiar Ñ
        dto.setApp(dto.getApp().replace('Ñ', 'N'));
        dto.setApm(dto.getApm().replace('Ñ', 'N'));
        dto.setNombre(dto.getNombre().replace('Ñ', 'N'));

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

            // Resultado de validación
            Map<String, Object> validation = new LinkedHashMap<>();
            boolean allMatch = true;

            // Leer OCR sin quitar saltos de línea
            String cleanText = Files.readString(outputFile.toPath()).toUpperCase().trim();
            System.out.println("🔍 Texto OCR limpio: " + cleanText);

            int matchesFound = 0;

            // === Lectura QR ===
            BufferedImage bufferedImage = ImageIO.read(tempFile);
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            String qrText = "";
            try {
                Result result = new MultiFormatReader().decode(bitmap);
                qrText = result.getText();
                System.out.println("🔍 Texto QR: " + qrText);
                matchesFound++;
            } catch (Exception e) {
                qrText = "";
            }

            // 🔹 Validar Apellido Paterno con regex flexible
            if (dto.getApp() != null && !dto.getApp().isEmpty()) {

                boolean match = containsFlexible(cleanText, dto.getApp());

                validation.put("apellidoP", match ? "✔ Encontrado" : "❌ No encontrado");

                if (!match)
                    allMatch = false;
                else
                    matchesFound++;
            }

            // 🔹 Validar Apellido Materno permitiendo nombres compuestos
            if (dto.getApm() != null && !dto.getApm().isEmpty()) {

                boolean match = containsFlexible(cleanText, dto.getApm());

                validation.put("apellidoM", match ? "✔ Encontrado" : "❌ No encontrado");

                if (!match)
                    allMatch = false;
                else
                    matchesFound++;
            }

            // 🔹 Validar Nombre(s) con regex flexible
            if (dto.getNombre() != null && !dto.getNombre().isEmpty()) {

                boolean match = containsFlexible(cleanText, dto.getNombre());

                validation.put("nombre", match ? "✔ Encontrado" : "❌ No encontrado");

                if (!match)
                    allMatch = false;
                else
                    matchesFound++;
            }

            // Parseo
            Map<String, String> parsed = parseINE(cleanText);

            if (parsed.isEmpty() || qrText.isEmpty())
                allMatch = false;

            return new ApiResponse<>(allMatch || matchesFound >= 3,
                    allMatch ? "Todos los datos coinciden"
                            : matchesFound >= 3 ? "Algunos datos coinciden" : "No todos los datos coinciden",
                    Map.of(
                            "validacion", validation,
                            "parsed", parsed,
                            "qrText", qrText));
        } catch (Exception e) {
            return new ApiResponse<>(false, "Error procesando INE: " + e.getMessage(), null);
        }
    }

    private static double similarityRatio(String a, String b) {
        a = a.replace('O', '0').replace('I', '1').replace('L', '1');
        b = b.replace('O', '0').replace('I', '1').replace('L', '1');

        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++)
            dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++)
            dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(
                        dp[i - 1][j] + 1,
                        dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost);
            }
        }
        int distance = dp[a.length()][b.length()];
        return 1 - ((double) distance / Math.max(a.length(), b.length()));
    }

    private boolean containsFlexible(String cleanText, String value) {

        // Normalizar OCR: dejar solo letras y espacios
        String text = cleanText.replaceAll("[^A-Z ]", " ");

        // Dividir el valor en palabras (para nombres o apellidos compuestos)
        String[] parts = value.toUpperCase().split("\\s+");

        for (String part : parts) {
            // REGEX que permite cualquier cosa entre letras
            String regex = ".*" +
                    part.chars()
                            .mapToObj(c -> (char) c)
                            .map(ch -> ch + ".*")
                            .reduce("", String::concat)
                    +
                    ".*";

            if (!text.matches(regex)) {
                return false;
            }
        }

        return true;
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
