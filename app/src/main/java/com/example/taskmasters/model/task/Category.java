package com.example.taskmasters.model.task;

public enum Category {
    GERAL("Geral"),
    ENCANADOR("Encanador"),
    MARCENEIRO("Marceneiro"),
    FRETE("Frete"),
    CONSERTO_DE_ELETRONICOS("Conserto de Eletrônicos");

    private String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
