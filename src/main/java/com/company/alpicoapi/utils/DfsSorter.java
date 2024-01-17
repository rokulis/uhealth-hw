package com.company.alpicoapi.utils;

import com.company.alpicoapi.model.MagicItem;

import java.util.*;

public class DfsSorter {

    public static List<MagicItem> sortMagicItemDependencies(List<MagicItem> payloads) {
        List<MagicItem> sortedList = new ArrayList<>();
        Set<MagicItem> visited = new HashSet<>();

        for (MagicItem payload : payloads) {
            dfsSort(payload, visited, sortedList);
        }

        return sortedList;
    }

    private static void dfsSort(MagicItem payload, Set<MagicItem> visited, List<MagicItem> sortedList) {
        if (visited.contains(payload)) {
            return;
        }

        visited.add(payload);
        for (MagicItem dependency : payload.getDependencies()) {
            dfsSort(dependency, visited, sortedList);
        }

        sortedList.add(payload);
    }
}
