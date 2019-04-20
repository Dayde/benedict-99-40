package fr.destiny.vacuum.web.model;

public enum ItemCategory {

    WEAPON(1),

    KINETIC_WEAPON(2),
    ENERGY_WEAPON(3),
    POWER_WEAPON(4),

    ARMOR(20),

    HELMET(45),
    GAUNTLETS(46),
    CHEST_ARMOR(47),
    LEG_ARMOR(48),
    CLASS_ARMOR(49);

    private long hash;

    ItemCategory(long hash) {
        this.hash = hash;
    }

    public long getHash() {
        return hash;
    }

    public static ItemCategory fromHash(long hash) {
        for (ItemCategory itemCategory : ItemCategory.values()) {
            if (itemCategory.hash == hash) {
                return itemCategory;
            }
        }
        return null;
    }
}
