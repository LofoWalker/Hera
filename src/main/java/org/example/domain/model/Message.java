package org.example.domain.model;

import java.util.List;

public record Message(String role, List<String> content) {

    public static Message system(List<String> content) {
        return new Message("system", content);
    }

    public static Message user(List<String> content) {
        return new Message("user", content);
    }

    public static Message assistant(List<String> content) {
        return new Message("assistant", content);
    }
}
