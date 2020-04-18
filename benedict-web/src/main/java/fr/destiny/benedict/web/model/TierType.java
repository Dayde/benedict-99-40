package fr.destiny.benedict.web.model;

public enum TierType {
    LEGENDARY,
    EXOTIC;

    public static TierType fromString(String tierType) {
        try {
            return valueOf(tierType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
