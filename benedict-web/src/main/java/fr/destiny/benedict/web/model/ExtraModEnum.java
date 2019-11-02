package fr.destiny.benedict.web.model;

public enum ExtraModEnum {
    NONE(0),
    UNDYING(2620967748L),
    OUTLAW(3625698764L),
    FORGE(720857L),
    OPULENCE(4106547009L),
    INTRUDER(0),
    REAPER(0),
    COLLECTOR(0),
    SENTINEL(0);

    private final long hash;

    ExtraModEnum(long hash) {
        this.hash = hash;
    }

    public static ExtraModEnum valueOf(long hash) {
        for (ExtraModEnum extraMod : ExtraModEnum.values()) {
            if (extraMod.getHash() == hash) {
                return extraMod;
            }
        }
        return null;
    }

    public long getHash() {
        return hash;
    }
}
