package fr.destiny.benedict.web.model;

public enum StatEnum {
    MOBILITY(
            2996146975L,
            3961599962L
    ),
    RESILIENCE(
            392767087L,
            2850583378L
    ),
    RECOVERY(
            1943323491L,
            2645858828L
    ),
    DISCILPLINE(
            1735777505L,
            4048838440L
    ),
    INTELLECT(
            144602215L,
            3355995799L
    ),
    STRENGTH(
            4244567218L,
            3253038666L
    );
    private final long hash;
    private final long modHash;

    StatEnum(long hash, long modHash) {
        this.hash = hash;
        this.modHash = modHash;
    }

    public static StatEnum modOf(long modHash) {
        for (StatEnum stat : StatEnum.values()) {
            if (stat.modHash == modHash) {
                return stat;
            }
        }
        return null;
    }

    public long getHash() {
        return hash;
    }

    public String getHashString() {
        return Long.toString(hash);
    }
}
