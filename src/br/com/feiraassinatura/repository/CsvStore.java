package br.com.feiraassinatura.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

final class CsvStore {
    private final Path file;
    private final List<String> header;

    CsvStore(Path dataDir, String fileName, String... header) {
        this.file = dataDir.resolve(fileName);
        this.header = List.of(header);
        ensureFile();
    }

    boolean isEmpty() {
        return readRows().isEmpty();
    }

    long nextLongId() {
        return readRows().stream()
                .map(row -> row.isEmpty() ? "0" : row.get(0))
                .mapToLong(value -> {
                    try {
                        return Long.parseLong(value);
                    } catch (NumberFormatException exception) {
                        return 0L;
                    }
                })
                .max()
                .orElse(0L) + 1L;
    }

    List<List<String>> readRows() {
        ensureFile();
        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            List<List<String>> rows = new ArrayList<>();
            for (int index = 1; index < lines.size(); index++) {
                String line = lines.get(index);
                if (!line.isBlank()) {
                    rows.add(parseLine(line));
                }
            }
            return rows;
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel ler o arquivo " + file, exception);
        }
    }

    void append(List<String> values) {
        ensureFile();
        try {
            Files.write(
                    file,
                    List.of(toLine(values)),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND);
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel gravar no arquivo " + file, exception);
        }
    }

    void rewrite(List<List<String>> rows) {
        ensureFile();
        List<String> lines = new ArrayList<>();
        lines.add(toLine(header));
        for (List<String> row : rows) {
            lines.add(toLine(row));
        }
        try {
            Files.write(
                    file,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel regravar o arquivo " + file, exception);
        }
    }

    private void ensureFile() {
        try {
            Files.createDirectories(file.getParent());
            if (Files.notExists(file)) {
                Files.write(file, List.of(toLine(header)), StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel preparar o arquivo " + file, exception);
        }
    }

    private static String toLine(List<String> values) {
        List<String> escaped = new ArrayList<>();
        for (String value : values) {
            escaped.add(escape(value));
        }
        return String.join(";", escaped);
    }

    private static String escape(String value) {
        String normalized = value == null ? "" : value.replace('\r', ' ').replace('\n', ' ');
        boolean needsQuotes = normalized.contains(";") || normalized.contains("\"");
        normalized = normalized.replace("\"", "\"\"");
        return needsQuotes ? "\"" + normalized + "\"" : normalized;
    }

    private static List<String> parseLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean quoted = false;
        for (int index = 0; index < line.length(); index++) {
            char character = line.charAt(index);
            if (character == '"') {
                if (quoted && index + 1 < line.length() && line.charAt(index + 1) == '"') {
                    current.append('"');
                    index++;
                } else {
                    quoted = !quoted;
                }
            } else if (character == ';' && !quoted) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(character);
            }
        }
        values.add(current.toString());
        return values;
    }
}
