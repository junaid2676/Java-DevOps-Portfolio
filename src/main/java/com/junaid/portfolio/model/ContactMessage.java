package com.junaid.portfolio.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContactMessage {

    private final String name;
    private final String email;
    private final String subject;
    private final String message;
    private final LocalDateTime receivedAt;

    public ContactMessage(String name, String email, String subject, String message) {
        this.name = sanitize(name);
        this.email = sanitize(email);
        this.subject = sanitize(subject);
        this.message = sanitize(message);
        this.receivedAt = LocalDateTime.now();
    }

    private static String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("[<>]", "").trim();
    }

    public boolean isValid() {
        return !name.isEmpty() && !email.isEmpty() && !message.isEmpty()
                && email.contains("@") && email.contains(".");
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] From: %s <%s>%nSubject: %s%nMessage: %s%n",
                receivedAt.format(fmt), name, email, subject, message);
    }
}
