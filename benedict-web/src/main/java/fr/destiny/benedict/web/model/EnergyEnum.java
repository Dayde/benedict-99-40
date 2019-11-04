package fr.destiny.benedict.web.model;

public enum EnergyEnum {
    ARC(728351493L),
    SOLAR(591714140L),
    VOID(4069572561L),
    NONE(null);

    private final Long hash;

    EnergyEnum(Long hash) {
        this.hash = hash;
    }

    public static EnergyEnum valueOf(Long hash) {
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
