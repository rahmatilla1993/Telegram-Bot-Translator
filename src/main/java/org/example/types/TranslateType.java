package org.example.types;

public enum TranslateType {
    ENG_UZB("Inglizcha-O'zbekcha", "en", "uz"),
    ENG_RUS("Inglizcha-Ruscha", "en", "ru"),
    RUS_UZB("Ruscha-O'zbekcha", "ru", "uz"),
    RUS_ENG("Ruscha-Inglizcha", "ru", "en"),
    UZB_RUS("O'zbekcha-Ruscha", "uz", "ru"),
    UZB_ENG("O'zbekcha-Inglizcha", "uz", "en");

    private final String content;
    private final String from;
    private final String to;

    TranslateType(String content, String from, String to) {
        this.content = content;
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }
}
