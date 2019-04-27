package fr.destiny.benedict.web.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

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
