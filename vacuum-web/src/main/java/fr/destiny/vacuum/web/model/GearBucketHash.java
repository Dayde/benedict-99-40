package fr.destiny.vacuum.web.model;

public enum GearBucketHash {

    KINETIC_WEAPON(1498876634L),
    ENERGY_WEAPON(2465295065L),
    POWER_WEAPON(953998645L),

    HELMET(3448274439L),
    CHEST_ARMOR(14239492L),
    GAUNTLETS(3551918588L),
    LEG_ARMOR(20886954L),
    CLASS_ARMOR(1585787867L),

    GENERAL_BUCKET(138197802L);

    private long hash;

    GearBucketHash(long hash) {
        this.hash = hash;
    }

    public long getHash() {
        return hash;
    }

    public static GearBucketHash fromHash(long hash) {
        for (GearBucketHash bucketHash : GearBucketHash.values()) {
            if (bucketHash.hash == hash) {
                return bucketHash;
            }
        }
        return null;
    }
}
