package fr.destiny.benedict.web.model;

public enum EnergyEnum {
    ARC(728351493L),
    SOLAR(591714140L),
    VOID(4069572561L);

    private final long hash;

    EnergyEnum(long hash) {
        this.hash = hash;
    }

    public static EnergyEnum valueOf(long hash) {
        for (EnergyEnum energy : EnergyEnum.values()) {
            if (energy.getHash() == hash) {
                return energy;
            }
        }
        throw new IllegalArgumentException("No energy with hash " + hash);
    }

    public long getHash() {
        return hash;
    }
}
