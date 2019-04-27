package fr.destiny.benedict.web.model;

public enum ItemCategory {

    WEAPON(1, 0),

    KINETIC_WEAPON(2, 0),
    ENERGY_WEAPON(3, 0),
    POWER_WEAPON(4, 0),

    ARMOR(20, 0),

    HELMET(45, 26),
    GAUNTLETS(46, 27),
    CHEST_ARMOR(47, 28),
    LEG_ARMOR(48, 29),
    CLASS_ARMOR(49, 30);

    private long hash;
    private int subType;

    ItemCategory(long hash, int subType) {
        this.hash = hash;
        this.subType = subType;
    }

    public long getHash() {
        return hash;
    }

    public static ItemCategory fromSubType(int itemSubType) {
        for (ItemCategory itemCategory : ItemCategory.values()) {
            if (itemCategory.subType == itemSubType) {
                return itemCategory;
            }
        }
        return null;
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
