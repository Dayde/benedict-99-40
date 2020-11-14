package fr.destiny.benedict.web.model;

import java.util.Set;

import static java.util.Collections.emptySet;

public enum ExtraModEnum {
    OUTLAW(3625698764L, Set.of(
            // Taken Invigoration
            399528760L,
            // Taken Repurposing
            2589105944L,
            // Taken Armaments
            2859541905L,
            // Taken Barrier
            3570105787L
    )),
    FORGE(720857L, Set.of(
            // Fallen Invigoration
            3395883122L,
            // Fallen Repurposing
            2276366746L,
            // Fallen Armaments
            2503471403L,
            // Fallen Barrier
            3099724909L
    )),
    COLLECTOR(3047801520L, emptySet()),
    SENTRY(2684355120L, emptySet()),
    INVADER(1233336930L, emptySet()),
    REAPER(149961592L, emptySet()),
    OPULENCE(4106547009L, Set.of(
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
    UNDYING(2620967748L, Set.of(
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
    DAWN(2357307006L, Set.of(
            // Quick Charge
            1484685884L,
            // Striking Light
            1484685885L,
            // Heavy Handed
            1484685886L,
            // Powerful Friends
            1484685887L,
            // Firepower
            3185435908L,
            // Heal Thyself
            3185435909L,
            // Charged Up
            3185435910L,
            // Blast Radius
            3185435911L,
            // Protective Light
            3523075120L,
            // Extra Reserves
            3523075121L,
            // Precisely Charged
            3523075122L,
            // Stacks on Stacks
            3523075123L,
            // Empowered Finish
            3632726236L,
            // High-Energy Fire
            3632726237L,
            // Taking Charge
            3632726238L,
            // Shield Break Charge
            3632726239L
    )),
    WORTHY(2655746324L, Set.of(
            // Global reach
            179977572L,
            // Blessing of Rasputin
            179977573L,
            // Light of the Fire
            179977574L,
            // Warmind's Light
            179977575L,
            // Grasp of the Warmind
            179977568L,
            // Modular Lightning
            1789319806L,
            // Strength of Rasputin
            1789319807L,
            // Sheltering Energy
            1789319804L,
            // Chosen of the Warmind
            1789319805L,
            // Burning Cells
            2216063963L,
            // Wrath of Rasputin
            2216063962L,
            // Fireteam Medic
            2216063961L,
            // Rage of the Warmind
            2216063960L,
            // Incinerating Light
            2216063967L,
            // Cellular Suppression
            2597888510L,
            // Power of Rasputin
            2597888511L,
            // Warmind's Protection
            2597888508L,
            // Warmind's Longevity
            2597888509L,
            // Light from  Darkness
            2597888506L
    )),
    ARRIVALS(4153634494L, Set.of(
            // ARGENT ORDNANCE
            4272180932L,
            // CHARGE HARVESTER
            2263321587L,
            // ENERGY CONVERTER
            2263321586L,
            // KINDLING THE FLAME
            4272180935L,
            // LUCENT BLADE
            2979815165L,
            // PRECISION CHARGE
            2263321584L,
            // RADIANT LIGHT
            2979815167L,
            // REACTIVE PULSE
            2979815166L,
            // SUPERCHARGED
            4272180934L,
            // SURPRISE ATTACK
            2263321585L,
            // SUSTAINED CHARGE
            4272180933L,
            // SWIFT CHARGE
            2979815164L
    )),
    COMBAT_STYLE(2493100093L, emptySet()),

    NONE(0, emptySet());

    private final long hash;
    private final Set<Long> applicableMods;

    ExtraModEnum(long hash, Set<Long> applicableMods) {
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

    public Set<Long> getApplicableMods() {
        return applicableMods;
    }
}
