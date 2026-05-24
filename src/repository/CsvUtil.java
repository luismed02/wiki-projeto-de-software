package repository;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class CsvUtil {
    public static final String SEP = ";";
    public static final String DATA_DIR = "src/data";

    public static List<String[]> lerLinhas(String arquivo) {
        Path path = Paths.get(arquivo);
        if (!Files.exists(path)) return new ArrayList<>();
        try {
            List<String> linhas = Files.readAllLines(path);
            if (linhas.isEmpty()) return new ArrayList<>();
            List<String[]> resultado = new ArrayList<>();
            for (int i = 1; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                if (linha.isBlank()) continue;
                resultado.add(linha.split(SEP, -1));
            }
            return resultado;
        } catch (IOException e) {
            throw new RuntimeException("Erro lendo " + arquivo, e);
        }
    }

    public static void anexarLinha(String arquivo, String cabecalho, String linha) {
        Path path = Paths.get(arquivo);
        try {
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)) {
                Files.writeString(path, cabecalho + System.lineSeparator());
            }
            Files.writeString(path, linha + System.lineSeparator(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Erro escrevendo " + arquivo, e);
        }
    }

    public static void reescrever(String arquivo, String cabecalho, List<String> linhas) {
        Path path = Paths.get(arquivo);
        try {
            Files.createDirectories(path.getParent());
            StringBuilder sb = new StringBuilder();
            sb.append(cabecalho).append(System.lineSeparator());
            for (String l : linhas) sb.append(l).append(System.lineSeparator());
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erro escrevendo " + arquivo, e);
        }
    }

    public static long proximoId(String arquivo) {
        List<String[]> linhas = lerLinhas(arquivo);
        long max = 0;
        for (String[] cols : linhas) {
            try {
                long id = Long.parseLong(cols[0]);
                if (id > max) max = id;
            } catch (NumberFormatException ignored) {}
        }
        return max + 1;
    }
}
