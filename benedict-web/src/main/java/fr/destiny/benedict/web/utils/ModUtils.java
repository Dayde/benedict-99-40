package fr.destiny.benedict.web.utils;

import java.util.Set;

public class ModUtils {

    public static Set<Long> STAT_MODS;

    public static Long MOBILITY_MOD = 3961599962L;
    public static Long RESILIENCE_MOD = 2850583378L;
    public static Long RECOVERY_MOD = 2645858828L;
    public static Long DISCILPLINE_MOD = 4048838440L;
    public static Long INTELLECT_MOD = 3355995799L;
    public static Long STRENGTH_MOD = 3253038666L;

    static {
        STAT_MODS = Set.of(
                MOBILITY_MOD,
                RESILIENCE_MOD,
                RECOVERY_MOD,
                DISCILPLINE_MOD,
                INTELLECT_MOD,
                STRENGTH_MOD
        );
    }
}
