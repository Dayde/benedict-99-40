package fr.destiny.benedict.web.utils;

import java.util.*;

public class PerkUtils {

    public static final Map<Long, List<Long>> GENERIC_PERKS;
    public static final List<Long> HELMET_FIRST_PERKS;
    public static final List<Long> HELMET_SECOND_PERKS;
    public static final List<Long> GAUNTLETS_FIRST_PERKS;
    public static final List<Long> GAUNTLETS_SECOND_PERKS;
    public static final List<Long> CHEST_ARMOR_FIRST_PERKS;
    public static final List<Long> CHEST_ARMOR_SECOND_PERKS;
    public static final List<Long> LEG_ARMOR_FIRST_PERKS;
    public static final List<Long> LEG_ARMOR_SECOND_PERKS;
    public static final List<Long> CLASS_ARMOR_FIRST_PERKS;
    public static final List<Long> CLASS_ARMOR_SECOND_PERKS;

    // Helmet
    // First Perks
    // Targeting
    private static final long PRECISION_WEAPON_TARGETING = 2414923993L;
    private static final long HAND_CANNON_TARGETING = 304186389L;
    private static final long SCOUT_RIFLE_TARGETING = 3494979213L;
    private static final long BOW_TARGETING = 3069341742L;
    private static final long LINEAR_FUSION_TARGETING = 3621792645L;
    private static final long SNIPER_RIFLE_TARGETING = 395489854L;

    private static final long SCATTER_PROJECTILE_TARGETING = 3813989326L;
    private static final long AUTO_RIFLE_TARGETING = 249373318L;
    private static final long SUBMACHINE_GUN_TARGETING = 159084247L;
    private static final long PULSE_RIFLE_TARGETING = 3804749722L;
    private static final long SIDEARM_TARGETING = 1134884869L;
    private static final long FUSION_RIFLE_TARGETING = 2138431565L;

    private static final long SHOTGUN_TARGETING = 433757560L;
    private static final long MACHINE_GUN_TARGETING = 942541111L;

    private static final long ENHANCED_BOW_TARGETING = 1576700957L;
    private static final long ENHANCED_SNIPER_RIFLE_TARGETING = 1983861079L;
    private static final long ENHANCED_LINEAR_FUSION_TARGETING = 2073167962L;
    private static final long ENHANCED_HAND_CANNON_TARGETING = 1356474366L;

    private static final long KINETIC_WEAPON_TARGETING = 2839066781L;
    private static final long ENERGY_WEAPON_TARGETING = 351326616L;
    private static final long POWER_WEAPON_TARGETING = 3201772785L;

    // Super
    private static final long LIGHT_REACTOR = 514613051L;
    private static final long HANDS_ON = 1567314459L;
    private static final long PUMP_ACTION = 1608112177L;
    private static final long REMOTE_CONNECTION = 2196878528L;
    private static final long ASHES_TO_ASSETS = 1107740237L;
    private static final long ENHANCED_ASHES_TO_ASSETS = 2910842650L;
    private static final long HEAVY_LIFTING = 2042848612L;
    private static final long ENHANCED_HEAVY_LIFTING = 433323503L;

    // Gauntlets
    // First Perks
    // Loader
    private static final long LIGHT_ARMS_LOADER = 1572280253L;
    private static final long HAND_CANNON_LOADER = 136210645L;
    private static final long SIDEARM_LOADER = 1592543661L;
    private static final long SUBMACHINE_GUN_LOADER = 3520605595L;
    private static final long BOW_RELOADER = 2493794080L;

    private static final long LARGE_WEAPON_LOADER = 1068982181L;
    private static final long ROCKET_LAUNCHER_LOADER = 2052426051L;
    private static final long GRENADE_LAUNCHER_LOADER = 1809014285L;
    private static final long SHOTGUN_LOADER = 1023341166L;

    private static final long RIFLE_LOADER = 2111651017L;
    private static final long AUTO_RIFLE_LOADER = 3489008282L;
    private static final long PULSE_RIFLE_LOADER = 2625406776L;
    private static final long SNIPER_RIFLE_LOADER = 2607559478L;
    private static final long SCOUT_RIFLE_LOADER = 2413867249L;
    private static final long LINEAR_FUSION_RIFLE_LOADER = 1808134965L;
    private static final long FUSION_RIFLE_LOADER = 546936951L;

    private static final long MACHINE_GUN_LOADER = 4070193907L;

    private static final long ENHANCED_HAND_CANNON_LOADER = 3035639340L;
    private static final long ENHANCED_SHOTGUN_LOADER = 3856768043L;
    private static final long ENHANCED_GRENADE_LAUNCHER_LOADER = 994729458L;
    private static final long ENHANCED_ROCKET_LAUNCHER_LOADER = 2433229738L;

    private static final long KINETIC_WEAPON_LOADER = 4043093993L;
    private static final long ENERGY_WEAPON_LOADER = 182444936L;
    private static final long POWER_WEAPON_LOADER = 4255886137L;

    // Ability
    private static final long FASTBALL = 3030206832L;
    private static final long MOMENTUM_TRANSFER = 320680844L;
    private static final long ENHANCED_MOMENTUM_TRANSFER = 2344113285L;
    private static final long IMPACT_INDUCTION = 3938115510L;
    private static final long ENHANCED_IMPACT_INDUCTION = 1463486301L;

    // Chest Armor
    // First Perks
    // Unflinching Aim
    private static final long UNFLINCHING_LIGHT_ARMS_AIM = 3676627761L;
    private static final long UNFLINCHING_SIDEARM_AIM = 3131895545L;
    private static final long UNFLINCHING_HAND_CANNON_AIM = 522919985L;
    private static final long UNFLINCHING_BOW_AIM = 3547610540L;
    private static final long UNFLINCHING_SUBMACHINE_GUN_AIM = 3511642555L;

    private static final long UNFLINCHING_RIFLE_AIM = 2825949957L;
    private static final long UNFLINCHING_AUTO_RIFLE_AIM = 2675237434L;
    private static final long UNFLINCHING_PULSE_RIFLE_AIM = 846801292L;
    private static final long UNFLINCHING_SNIPER_AIM = 1757762246L;
    private static final long UNFLINCHING_SCOUT_RIFLE_AIM = 4008779013L;
    private static final long UNFLINCHING_LINEAR_FUSION_AIM = 4124601641L;
    private static final long UNFLINCHING_FUSION_RIFLE_AIM = 1891880743L;

    private static final long UNFLINCHING_SHOTGUN_AIM = 168673710L;
    private static final long UNFLINCHING_GRENADE_LAUNCHER_AIM = 430361065L;
    private static final long UNFLINCHING_MACHINE_GUN = 1772682177L;

    private static final long ENHANCED_UNFLINCHING_BOW_AIM = 1084281489L;
    private static final long ENHANCED_UNFLINCHING_SNIPER_AIM = 854794537L;
    private static final long ENHANCED_UNFLINCHING_SCOUT_RIFLE_AIM = 2788953856L;
    private static final long ENHANCED_UNFLINCHING_LINEAR_FUSION_AIM = 1325595568L;

    private static final long UNFLINCHING_KINETIC_AIM = 527286589L;
    private static final long UNFLINCHING_ENERGY_AIM = 2317587052L;
    private static final long UNFLINCHING_POWER_AIM = 1204062917L;
    private static final long UNFLINCHING_LARGE_ARMS = 3647557929L;


    // Leg Armor
    // First Perks
    // Dexterity
    private static final long OVERSIZE_WEAPON_DEXTERITY = 3515212050L;
    private static final long ROCKET_LAUNCHER_DEXTERITY = 1334592906L;
    private static final long GRENADE_LAUNCHER_DEXTERITY = 1749896152L;
    private static final long SHOTGUN_DEXTERITY = 1036754881L;
    private static final long BOW_DEXTERITY = 3591739443L;

    private static final long RIFLE_DEXTERITY = 877194565L;
    private static final long AUTO_RIFLE_DEXTERITY = 3177615969L;
    private static final long PULSE_RIFLE_DEXTERITY = 1057027095L;
    private static final long SNIPER_RIFLE_DEXTERITY = 3189772341L;
    private static final long SCOUT_RIFLE_DEXTERITY = 1170148132L;
    private static final long LINEAR_FUSION_DEXTERITY = 3803462520L;
    private static final long FUSION_RIFLE_DEXTERITY = 568667342L;

    private static final long LIGHT_ARMS_DEXTERITY = 301115145L;
    private static final long SIDEARM_DEXTERITY = 2002130360L;
    private static final long HAND_CANNON_DEXTERITY = 2163383276L;
    private static final long SUBMACHINE_GUN_DEXTERITY = 7471106L;

    private static final long MACHINE_GUN_DEXTERITY = 1371918942L;

    private static final long ENHANCED_SHOTGUN_DEXTERITY = 1183779334L;
    private static final long ENHANCED_HAND_CANNON_DEXTERITY = 4140705235L;
    private static final long ENHANCED_ROCKET_LAUNCHER_DEXTERITY = 4282096169L;
    private static final long ENHANCED_SNIPER_RIFLE_DEXTERITY = 1227994396L;

    private static final long KINETIC_DEXTERITY = 2326218464L;
    private static final long ENERGY_DEXTERITY = 377666359L;
    private static final long POWER_DEXTERITY = 952165152L;

    // Ability
    private static final long BOMBER = 1122363303L;
    private static final long DYNAMO = 4048902980L;
    private static final long ENHANCED_BOMBER = 2972736610L;
    private static final long OUTREACH = 737144025L;
    private static final long PERPETUATION = 290114209L;
    private static final long DISTRIBUTION = 2610215900L;
    private static final long TRACTION = 1818103563L;

    // Class Armor
    // First Perks
    private static final long ABSOLUTION = 3317098328L;
    private static final long INNERVATION = 2760701299L;
    private static final long INVIGORATION = 673846245L;
    private static final long INSULATION = 1316177012L;

    private static final long RECUPERATION = 3416944458L;
    private static final long BETTER_ALREADY = 310410239L;


    // Reserves
    // Helmet Second Perk
    private static final long MACHINE_GUN_RESERVES = 901125364L;
    private static final long SWORD_RESERVES = 2318225451L;
    private static final long GRENADE_LAUNCHER_RESERVES = 330650351L;
    private static final long LINEAR_FUSION_RIFLE_RESERVES = 4007230256L;
    private static final long SNIPER_RIFLE_RESERVES = 1180144002L;
    private static final long FUSION_RIFLE_RESERVES = 4034210761L;
    private static final long ROCKET_LAUNCHER_RESERVES = 1812356167L;
    private static final long SHOTGUN_RESERVES = 1068909436L;

    // Chest Armor Second Perk
    private static final long SCOUT_RIFLE_RESERVES = 3910640537L;
    private static final long HAND_CANNON_RESERVES = 833036833L;
    private static final long SUBMACHINE_GUN_RESERVES = 3975312523L;
    private static final long SIDEARM_RESERVES = 2433223313L;
    private static final long BOW_RESERVES = 1879053658L;
    private static final long PULSE_RIFLE_RESERVES = 1659799750L;
    private static final long AUTO_RIFLE_RESERVES = 2928640162L;

    // Scavengers
    // Gauntlets Second Perk
    private static final long ROCKET_LAUNCHER_SCAVENGER = 4245438319L;
    private static final long FUSION_RIFLE_SCAVENGER = 127731051L;
    private static final long LINEAR_FUSION_RIFLE_SCAVENGER = 3054567270L;
    private static final long SWORD_SCAVENGER = 2034622599L;
    private static final long SHOTGUN_SCAVENGER = 1066067378L;
    private static final long GRENADE_LAUNCHER_SCAVENGER = 866518141L;
    private static final long MACHINE_GUN_SCAVENGER = 790090923L;
    private static final long SNIPER_RIFLE_SCAVENGER = 2600330634L;

    // Leg Armor Second Perk
    private static final long SIDEARM_SCAVENGER = 3670792765L;
    private static final long HAND_CANNON_SCAVENGER = 4047893141L;
    private static final long SUBMACHINE_GUN_SCAVENGER = 699706967L;
    private static final long AUTO_RIFLE_SCAVENGER = 3784207446L;
    private static final long PULSE_RIFLE_SCAVENGER = 3081361536L;
    private static final long SCOUT_RIFLE_SCAVENGER = 1507173193L;
    private static final long ARROW_SCAVENGER = 735778136L;

    // Ammo Finder
    private static final long PRIMARY_AMMO_FINDER = 143442373L;
    private static final long SPECIAL_AMMO_FINDER = 2620835322L;
    private static final long HEAVY_AMMO_FINDER = 2867719094L;


    static {
        List<Long> helmetFirstPerks = Arrays.asList(
                PRECISION_WEAPON_TARGETING,
                HAND_CANNON_TARGETING,
                SCOUT_RIFLE_TARGETING,
                BOW_TARGETING,
                LINEAR_FUSION_TARGETING,
                SNIPER_RIFLE_TARGETING,
                SCATTER_PROJECTILE_TARGETING,
                AUTO_RIFLE_TARGETING,
                SUBMACHINE_GUN_TARGETING,
                PULSE_RIFLE_TARGETING,
                SIDEARM_TARGETING,
                FUSION_RIFLE_TARGETING,
                SHOTGUN_TARGETING,
                MACHINE_GUN_TARGETING,
                ENHANCED_BOW_TARGETING,
                ENHANCED_SNIPER_RIFLE_TARGETING,
                ENHANCED_LINEAR_FUSION_TARGETING,
                ENHANCED_HAND_CANNON_TARGETING,
                KINETIC_WEAPON_TARGETING,
                ENERGY_WEAPON_TARGETING,
                POWER_WEAPON_TARGETING,
                LIGHT_REACTOR,
                HANDS_ON,
                PUMP_ACTION,
                REMOTE_CONNECTION,
                ASHES_TO_ASSETS,
                ENHANCED_ASHES_TO_ASSETS,
                HEAVY_LIFTING,
                ENHANCED_HEAVY_LIFTING
        );
        HELMET_FIRST_PERKS = Collections.unmodifiableList(helmetFirstPerks);

        List<Long> helmetSecondPerks = Arrays.asList(
                MACHINE_GUN_RESERVES,
                SWORD_RESERVES,
                GRENADE_LAUNCHER_RESERVES,
                LINEAR_FUSION_RIFLE_RESERVES,
                SNIPER_RIFLE_RESERVES,
                FUSION_RIFLE_RESERVES,
                ROCKET_LAUNCHER_RESERVES,
                SHOTGUN_RESERVES,
                SPECIAL_AMMO_FINDER,
                HEAVY_AMMO_FINDER
        );
        HELMET_SECOND_PERKS = Collections.unmodifiableList(helmetSecondPerks);

        List<Long> gauntletsFirstPerks = Arrays.asList(
                LIGHT_ARMS_LOADER,
                HAND_CANNON_LOADER,
                SIDEARM_LOADER,
                SUBMACHINE_GUN_LOADER,
                BOW_RELOADER,
                LARGE_WEAPON_LOADER,
                ROCKET_LAUNCHER_LOADER,
                GRENADE_LAUNCHER_LOADER,
                SHOTGUN_LOADER,
                RIFLE_LOADER,
                AUTO_RIFLE_LOADER,
                PULSE_RIFLE_LOADER,
                SNIPER_RIFLE_LOADER,
                SCOUT_RIFLE_LOADER,
                LINEAR_FUSION_RIFLE_LOADER,
                FUSION_RIFLE_LOADER,
                MACHINE_GUN_LOADER,
                ENHANCED_HAND_CANNON_LOADER,
                ENHANCED_SHOTGUN_LOADER,
                ENHANCED_GRENADE_LAUNCHER_LOADER,
                ENHANCED_ROCKET_LAUNCHER_LOADER,
                KINETIC_WEAPON_LOADER,
                ENERGY_WEAPON_LOADER,
                POWER_WEAPON_LOADER,
                FASTBALL,
                MOMENTUM_TRANSFER,
                ENHANCED_MOMENTUM_TRANSFER,
                IMPACT_INDUCTION,
                ENHANCED_IMPACT_INDUCTION
        );
        GAUNTLETS_FIRST_PERKS = Collections.unmodifiableList(gauntletsFirstPerks);

        List<Long> gauntletsSecondPerks = Arrays.asList(
                ROCKET_LAUNCHER_SCAVENGER,
                FUSION_RIFLE_SCAVENGER,
                LINEAR_FUSION_RIFLE_SCAVENGER,
                SWORD_SCAVENGER,
                SHOTGUN_SCAVENGER,
                GRENADE_LAUNCHER_SCAVENGER,
                MACHINE_GUN_SCAVENGER,
                SNIPER_RIFLE_SCAVENGER,
                SPECIAL_AMMO_FINDER,
                HEAVY_AMMO_FINDER
        );
        GAUNTLETS_SECOND_PERKS = Collections.unmodifiableList(gauntletsSecondPerks);

        List<Long> chestArmorFirstPerks = Arrays.asList(
                UNFLINCHING_LIGHT_ARMS_AIM,
                UNFLINCHING_SIDEARM_AIM,
                UNFLINCHING_HAND_CANNON_AIM,
                UNFLINCHING_BOW_AIM,
                UNFLINCHING_SUBMACHINE_GUN_AIM,
                UNFLINCHING_RIFLE_AIM,
                UNFLINCHING_AUTO_RIFLE_AIM,
                UNFLINCHING_PULSE_RIFLE_AIM,
                UNFLINCHING_SNIPER_AIM,
                UNFLINCHING_SCOUT_RIFLE_AIM,
                UNFLINCHING_LINEAR_FUSION_AIM,
                UNFLINCHING_FUSION_RIFLE_AIM,
                UNFLINCHING_SHOTGUN_AIM,
                UNFLINCHING_GRENADE_LAUNCHER_AIM,
                UNFLINCHING_MACHINE_GUN,
                ENHANCED_UNFLINCHING_BOW_AIM,
                ENHANCED_UNFLINCHING_SNIPER_AIM,
                ENHANCED_UNFLINCHING_SCOUT_RIFLE_AIM,
                ENHANCED_UNFLINCHING_LINEAR_FUSION_AIM,
                UNFLINCHING_KINETIC_AIM,
                UNFLINCHING_ENERGY_AIM,
                UNFLINCHING_POWER_AIM,
                UNFLINCHING_LARGE_ARMS
        );
        CHEST_ARMOR_FIRST_PERKS = Collections.unmodifiableList(chestArmorFirstPerks);

        List<Long> chestArmorSecondPerks = Arrays.asList(
                SCOUT_RIFLE_RESERVES,
                HAND_CANNON_RESERVES,
                SUBMACHINE_GUN_RESERVES,
                SIDEARM_RESERVES,
                BOW_RESERVES,
                PULSE_RIFLE_RESERVES,
                AUTO_RIFLE_RESERVES,
                PRIMARY_AMMO_FINDER,
                SPECIAL_AMMO_FINDER
        );
        CHEST_ARMOR_SECOND_PERKS = Collections.unmodifiableList(chestArmorSecondPerks);

        List<Long> legarmorFirstPerks = Arrays.asList(
                OVERSIZE_WEAPON_DEXTERITY,
                ROCKET_LAUNCHER_DEXTERITY,
                GRENADE_LAUNCHER_DEXTERITY,
                SHOTGUN_DEXTERITY,
                BOW_DEXTERITY,
                RIFLE_DEXTERITY,
                AUTO_RIFLE_DEXTERITY,
                PULSE_RIFLE_DEXTERITY,
                SNIPER_RIFLE_DEXTERITY,
                SCOUT_RIFLE_DEXTERITY,
                LINEAR_FUSION_DEXTERITY,
                FUSION_RIFLE_DEXTERITY,
                LIGHT_ARMS_DEXTERITY,
                SIDEARM_DEXTERITY,
                HAND_CANNON_DEXTERITY,
                SUBMACHINE_GUN_DEXTERITY,
                MACHINE_GUN_DEXTERITY,
                ENHANCED_SHOTGUN_DEXTERITY,
                ENHANCED_HAND_CANNON_DEXTERITY,
                ENHANCED_ROCKET_LAUNCHER_DEXTERITY,
                ENHANCED_SNIPER_RIFLE_DEXTERITY,
                KINETIC_DEXTERITY,
                ENERGY_DEXTERITY,
                POWER_DEXTERITY,
                BOMBER,
                DYNAMO,
                ENHANCED_BOMBER,
                OUTREACH,
                PERPETUATION,
                DISTRIBUTION,
                TRACTION
        );
        LEG_ARMOR_FIRST_PERKS = Collections.unmodifiableList(legarmorFirstPerks);

        List<Long> legArmorSecondPerks = Arrays.asList(
                SIDEARM_SCAVENGER,
                HAND_CANNON_SCAVENGER,
                SUBMACHINE_GUN_SCAVENGER,
                AUTO_RIFLE_SCAVENGER,
                PULSE_RIFLE_SCAVENGER,
                SCOUT_RIFLE_SCAVENGER,
                ARROW_SCAVENGER,
                PRIMARY_AMMO_FINDER,
                SPECIAL_AMMO_FINDER
        );
        LEG_ARMOR_SECOND_PERKS = Collections.unmodifiableList(legArmorSecondPerks);

        List<Long> classarmorFirstPerks = Arrays.asList(
                ABSOLUTION,
                INNERVATION,
                INVIGORATION,
                INSULATION,
                RECUPERATION,
                BETTER_ALREADY
        );
        CLASS_ARMOR_FIRST_PERKS = Collections.unmodifiableList(classarmorFirstPerks);

        List<Long> classarmorSecondPerks = Arrays.asList(
                MACHINE_GUN_RESERVES,
                SWORD_RESERVES,
                GRENADE_LAUNCHER_RESERVES,
                LINEAR_FUSION_RIFLE_RESERVES,
                SNIPER_RIFLE_RESERVES,
                FUSION_RIFLE_RESERVES,
                ROCKET_LAUNCHER_RESERVES,
                SHOTGUN_RESERVES,
                SCOUT_RIFLE_RESERVES,
                HAND_CANNON_RESERVES,
                SUBMACHINE_GUN_RESERVES,
                SIDEARM_RESERVES,
                BOW_RESERVES,
                PULSE_RIFLE_RESERVES,
                AUTO_RIFLE_RESERVES,
                ROCKET_LAUNCHER_SCAVENGER,
                FUSION_RIFLE_SCAVENGER,
                LINEAR_FUSION_RIFLE_SCAVENGER,
                SWORD_SCAVENGER,
                SHOTGUN_SCAVENGER,
                GRENADE_LAUNCHER_SCAVENGER,
                MACHINE_GUN_SCAVENGER,
                SNIPER_RIFLE_SCAVENGER,
                SIDEARM_SCAVENGER,
                HAND_CANNON_SCAVENGER,
                SUBMACHINE_GUN_SCAVENGER,
                AUTO_RIFLE_SCAVENGER,
                PULSE_RIFLE_SCAVENGER,
                SCOUT_RIFLE_SCAVENGER,
                ARROW_SCAVENGER,
                PRIMARY_AMMO_FINDER,
                SPECIAL_AMMO_FINDER,
                HEAVY_AMMO_FINDER
        );
        CLASS_ARMOR_SECOND_PERKS = Collections.unmodifiableList(classarmorSecondPerks);

        Map<Long, List<Long>> genericPerks = new HashMap<>();
        genericPerks.put(
                PRECISION_WEAPON_TARGETING,
                Arrays.asList(
                        HAND_CANNON_TARGETING,
                        SCOUT_RIFLE_TARGETING,
                        BOW_TARGETING,
                        LINEAR_FUSION_TARGETING,
                        SNIPER_RIFLE_TARGETING
                )
        );
        genericPerks.put(
                SCATTER_PROJECTILE_TARGETING,
                Arrays.asList(
                        AUTO_RIFLE_TARGETING,
                        SUBMACHINE_GUN_TARGETING,
                        PULSE_RIFLE_TARGETING,
                        SIDEARM_TARGETING,
                        FUSION_RIFLE_TARGETING
                )
        );
        genericPerks.put(
                LIGHT_ARMS_LOADER,
                Arrays.asList(
                        HAND_CANNON_LOADER,
                        SIDEARM_LOADER,
                        SUBMACHINE_GUN_LOADER,
                        BOW_RELOADER
                )
        );
        genericPerks.put(
                LARGE_WEAPON_LOADER,
                Arrays.asList(
                        ROCKET_LAUNCHER_LOADER,
                        GRENADE_LAUNCHER_LOADER,
                        SHOTGUN_LOADER
                )
        );
        genericPerks.put(
                RIFLE_LOADER,
                Arrays.asList(
                        AUTO_RIFLE_LOADER,
                        PULSE_RIFLE_LOADER,
                        SNIPER_RIFLE_LOADER,
                        SCOUT_RIFLE_LOADER,
                        LINEAR_FUSION_RIFLE_LOADER,
                        FUSION_RIFLE_LOADER
                )
        );
        genericPerks.put(
                UNFLINCHING_LIGHT_ARMS_AIM,
                Arrays.asList(
                        UNFLINCHING_SIDEARM_AIM,
                        UNFLINCHING_HAND_CANNON_AIM,
                        UNFLINCHING_BOW_AIM,
                        UNFLINCHING_SUBMACHINE_GUN_AIM
                )
        );
        genericPerks.put(
                UNFLINCHING_RIFLE_AIM,
                Arrays.asList(
                        UNFLINCHING_AUTO_RIFLE_AIM,
                        UNFLINCHING_PULSE_RIFLE_AIM,
                        UNFLINCHING_SNIPER_AIM,
                        UNFLINCHING_SCOUT_RIFLE_AIM,
                        UNFLINCHING_LINEAR_FUSION_AIM,
                        UNFLINCHING_FUSION_RIFLE_AIM
                )
        );
        genericPerks.put(
                OVERSIZE_WEAPON_DEXTERITY,
                Arrays.asList(
                        ROCKET_LAUNCHER_DEXTERITY,
                        GRENADE_LAUNCHER_DEXTERITY,
                        SHOTGUN_DEXTERITY,
                        BOW_DEXTERITY
                )
        );
        genericPerks.put(
                RIFLE_DEXTERITY,
                Arrays.asList(
                        AUTO_RIFLE_DEXTERITY,
                        PULSE_RIFLE_DEXTERITY,
                        SNIPER_RIFLE_DEXTERITY,
                        SCOUT_RIFLE_DEXTERITY,
                        LINEAR_FUSION_DEXTERITY,
                        FUSION_RIFLE_DEXTERITY
                )
        );
        genericPerks.put(
                LIGHT_ARMS_DEXTERITY,
                Arrays.asList(
                        SIDEARM_DEXTERITY,
                        HAND_CANNON_DEXTERITY,
                        SUBMACHINE_GUN_DEXTERITY
                )
        );
        genericPerks.put(
                ABSOLUTION,
                Arrays.asList(
                        INNERVATION,
                        INVIGORATION,
                        INSULATION
                )
        );
        GENERIC_PERKS = Collections.unmodifiableMap(genericPerks);
    }
}
