package fr.destiny.benedict.web.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

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

    private static final long ABSOLUTION = 3317098328L;
    private static final long INNERVATION = 2760701299L;
    private static final long INVIGORATION = 673846245L;
    private static final long INSULATION = 1316177012L;

    public static final Map<Long, List<Long>> GENERIC_PERKS;

    static {
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

    public static <K, V> Map<K, Set<V>> mergeMaps(Map<K, Set<V>> map1, Map<K, Set<V>> map2) {
        return Stream.concat(
                map1.entrySet().stream(),
                map2.entrySet().stream()
        ).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (set1, set2) -> {
                    Set<V> mergedSet = new HashSet<>(set1);
                    mergedSet.addAll(set2);
                    return mergedSet;
                }
        ));
    }
}
