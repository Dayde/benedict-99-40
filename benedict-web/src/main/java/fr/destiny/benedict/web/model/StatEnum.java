package fr.destiny.benedict.web.model;

public enum StatEnum {
    MOBILITY("2996146975"),
    RESILIENCE("392767087"),
    RECOVERY("1943323491"),
    DISCILPLINE("1735777505"),
    INTELLECT("144602215"),
    STRENGTH("4244567218");

    private final String hash;

    StatEnum(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}
