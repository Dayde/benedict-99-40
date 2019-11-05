package fr.destiny.benedict.web.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ExtraModEnum {
    OUTLAW(3625698764L, Arrays.asList(
            // Taken Invigoration
            399528760L,
            // Taken Repurposing
            2589105944L,
            // Taken Armaments
            2859541905L,
            // Taken Barrier
            3570105787L
    )),
    FORGE(720857L, Arrays.asList(
            // Fallen Invigoration
            3395883122L,
            // Fallen Repurposing
            2276366746L,
            // Fallen Armaments
            2503471403L,
            // Fallen Barrier
            3099724909L
    )),
    COLLECTOR(3047801520L, Collections.emptyList()),
    SENTRY(2684355120L, Collections.emptyList()),
    INVADER(1233336930L, Collections.emptyList()),
    REAPER(149961592L, Collections.emptyList()),
    OPULENCE(4106547009L, Arrays.asList(
            // Shielding Hand
            499567183L,
            // Giving Hand
            2280136185L,
            // Striking Hand
            2816649701L,
            // Power Overwhelming
            1489470244L,
            // Energized
            4130296084L,
            // Hive Invigoration
            2146600970L,
            // Hive Repurposing
            3302924434L,
            // Hive Armaments
            927348227L,
            // Hive Barrier
            3967424085L
    )),
    UNDYING(2620967748L, Arrays.asList(
            // Dreambane Mod
            3895804619L,
            // Relay Defender
            3415291596L,
            // Enhanced Relay Defender
            3630287380L,
            // Nightmare Banisher
            2874957617L,
            // Enhanced Nightmare Banisher
            1996040463L,
            // Supreme Nightmare Banisher
            3829100654L,
            // Nightmare Breaker
            1560574695L,
            // Enhanced Nightmare Breaker
            4193902249L,
            // Supreme Nightmare Breaker
            2023980600L,
            // Nightmare Crusher
            3736152098L,
            //Enhanced Nightmare Crusher
            1565861116L,
            // Supreme Nightmare Crusher
            2045123179L,
            // Voltaic Mote Collector
            4134680615L,
            // Enhanced Voltaic Mote Collector
            865380761L,
            // Voltaic Ammo Collector
            928186993L,
            // Enhanced Voltaic Ammo Collector
            4020036031L,
            // Resistant Tether
            2887845822L,
            // Enhanced Resistant Tether
            2037533514L
    )),
    NONE(0, Collections.emptyList());

    private final long hash;
    private final List<Long> applicableMods;

    ExtraModEnum(long hash, List<Long> applicableMods) {
        this.hash = hash;
        this.applicableMods = applicableMods;
    }

    public static ExtraModEnum valueOf(long hash) {
        for (ExtraModEnum extraMod : ExtraModEnum.values()) {
            if (extraMod.getHash() == hash || extraMod.getApplicableMods().contains(hash)) {
                return extraMod;
            }
        }
        return null;
    }

    public long getHash() {
        return hash;
    }

    public List<Long> getApplicableMods() {
        return applicableMods;
    }
}
