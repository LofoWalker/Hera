package org.example.domain.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Value object representing the system instruction for AI conversations.
 * This is stored in the domain and not provided by users.
 */
public record SystemInstruction(String content) {

    private static final String INSTRUCTION_FILE = "system-instruction.txt";

    public SystemInstruction {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("System instruction cannot be null or empty");
        }
    }

    /**
     * Creates the default system instruction by loading it from a resource file.
     *
     * @return the default system instruction
     * @throws RuntimeException if the instruction file cannot be loaded
     */
    public static SystemInstruction createDefault() {
        try {
            String content = loadInstructionFromFile();
            return new SystemInstruction(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load system instruction from file: " + INSTRUCTION_FILE, e);
        }
    }

    /**
     * Loads the instruction content from the resource file.
     *
     * @return the instruction content
     * @throws IOException if the file cannot be read
     */
    private static String loadInstructionFromFile() throws IOException {
        InputStream inputStream = SystemInstruction.class.getClassLoader().getResourceAsStream(INSTRUCTION_FILE);
        if (inputStream == null) {
            throw new IOException("System instruction file not found: " + INSTRUCTION_FILE);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Gets the instruction content.
     *
     * @return the instruction content
     */
    @Override
    public String content() {
        return content;
    }
}
