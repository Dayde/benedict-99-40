package fr.destiny.benedict.web.model;

import java.util.Map;

public enum StatEnum {
    MOBILITY(
            2996146975L,
            Map.of(
                    3961599962L, 10,
                    1708067044L, 5
            )
    ),
    RESILIENCE(
            392767087L,
            Map.of(2850583378L, 10)
    ),
    RECOVERY(
            1943323491L,
            Map.of(2645858828L, 10)
    ),
    DISCIPLINE(
            1735777505L,
            Map.of(4048838440L, 10)
    ),
    INTELLECT(
            144602215L,
            Map.of(3355995799L, 10)
    ),
    STRENGTH(
            4244567218L,
            Map.of(3253038666L, 10)
    );
    private final long hash;
    private final Map<Long, Integer> modValueByHash;

    StatEnum(long hash, Map<Long, Integer> modValueByHash) {
        this.hash = hash;
        this.modValueByHash = modValueByHash;
    }

    public static StatEnum modOf(long modHash) {
        for (StatEnum stat : StatEnum.values()) {
            if (stat.modValueByHash.containsKey(modHash)) {
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

    public int modValue(long modHash) {
        return modValueByHash.get(modHash);
    }
}
