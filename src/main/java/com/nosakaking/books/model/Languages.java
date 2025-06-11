package com.nosakaking.books.model;

public enum Languages {
    EN("en", "Español"),
    ES("es", "Ingles");

    private String language;
    private String languageSpanish;
    Languages(String language, String languageSpanish) {
        this.language = language;
        this.languageSpanish = languageSpanish;
    }

    public static Languages fromLanguage(String language) {
        for (Languages l : Languages.values()) {
            if (l.language.equalsIgnoreCase(language)) {
                return l;
            }
        }
        throw new IllegalArgumentException("Ninguna lemguaje encontrado: " + language);
    }

    public static Languages fromLanguageSpanish(String languageSpanish) {
        for (Languages l : Languages.values()) {
            if (l.languageSpanish.equalsIgnoreCase(languageSpanish)) {
                return l;
            }
        }
        throw new IllegalArgumentException("Ninguna lemguaje encontrado: " + languageSpanish);
    }
}
