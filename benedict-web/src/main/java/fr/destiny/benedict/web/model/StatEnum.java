package fr.destiny.benedict.web.model;

import java.util.Map;

public enum StatEnum {
    MOBILITY(
            2996146975L,
            Map.of(
                    3961599962L, 10,
                    204137529L, 5,
                    1484685887L, 20
            )
    ),
    RESILIENCE(
            392767087L,
            Map.of(
                    2850583378L, 10,
                    3682186345L, 5
            )
    ),
    RECOVERY(
            1943323491L,
            Map.of(
                    2645858828L, 10,
                    555005975L, 5,
                    3523075123L, -10
            )
    ),
    DISCIPLINE(
            1735777505L,
            Map.of(
                    4048838440L, 10,
                    2623485440L, 5,
                    3523075122L, -10,
                    2263321586L, -10
            )
    ),
    INTELLECT(
            144602215L,
            Map.of(
                    3355995799L, 10,
                    1227870362L, 5,
                    3523075121L, -10,
                    2263321585L, -10
            )
    ),
    STRENGTH(
            4244567218L,
            Map.of(
                    3253038666L, 10,
                    3699676109L, 5,
                    2979815167L, 20,
                    3523075120L, -10,
                    2263321584L, -10
            )
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
