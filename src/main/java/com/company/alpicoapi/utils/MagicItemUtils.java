package com.company.alpicoapi.utils;

import com.company.alpicoapi.exceptions.ApiException;
import com.company.alpicoapi.magicfeature.MagicService;
import com.company.alpicoapi.model.Barrier;
import com.company.alpicoapi.model.BarrierType;
import com.company.alpicoapi.model.MagicItem;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MagicItemUtils {

    private static final int MINIMAL_TIME_TO_ENSURE_NEXT_ITEM_SOLVABLE = 300;

    public static byte[] tokensToSvg(List<MagicItem> list) {
        list.sort(Comparator.comparingInt(MagicItem::getIndex));
        StringBuilder stringBuilder = new StringBuilder();

        for (MagicItem magic : list) {
            stringBuilder.append(magic.getResult());
        }

        return Base64.getDecoder().decode(stringBuilder.toString());
    }

    public static List<MagicItem> createMagicItems(String[] magicTokens) {
        List<MagicItem> list = new ArrayList<>();

        for (String magicToken : magicTokens) {
            list.add(createMagicItem(magicToken));
        }

        for (MagicItem magicItem : list) {
            addSortedDependencies(magicItem, list);
        }

        list.sort(MagicItem.timeBarrierComparator());

        return Collections.synchronizedList(DfsSorter.sortMagicItemDependencies(list));
    }

    private static MagicItem createMagicItem(String magicToken) {
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String[] chunks = magicToken.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));

        MagicItem jwtPayload = JsonUtils.fromJson(payload, MagicItem.class);

        Barrier timeBarrier = jwtPayload.getBarriers()
                .stream()
                .filter(barrier -> barrier.getType() == BarrierType.TIME)
                .findFirst()
                .orElse(null);

        Barrier constBarrier = jwtPayload.getBarriers()
                .stream()
                .filter(barrier -> barrier.getType() == BarrierType.CONST)
                .findFirst()
                .orElse(null);

        jwtPayload.setConstBarrier(constBarrier);
        jwtPayload.setTimeBarrier(timeBarrier);
        jwtPayload.setToken(magicToken);
        return jwtPayload;
    }

    private static void addSortedDependencies(MagicItem jwtPayload, List<MagicItem> list) {
        if (jwtPayload.getDependencies().size() > 0) {
            return; // already added
        }

        List<MagicItem> dependencies = jwtPayload.getBarriers()
                .stream()
                .filter(barrier -> barrier.getType() == BarrierType.DEPENDENCY)
                .flatMap(barrier -> barrier.getOn().stream())
                .map(list::get).collect(Collectors.toList());

        if (dependencies.size() == 0) {
            return;
        }

        dependencies.sort(MagicItem.timeBarrierComparator());
        jwtPayload.getDependencies().addAll(dependencies);

        for (MagicItem p : dependencies) {
            addSortedDependencies(p, list);
        }
    }

    public static void checkTimeBarriers(List<MagicItem> magicItems) {
        for (int i = 0; i < magicItems.size(); i++)
            for (int j = i; j < magicItems.size() - 1; j++)
                MagicItemUtils.checkTimeBarriers(magicItems.get(j + 1).getTimeBarrier(), magicItems.get(i).getTimeBarrier());
    }


    public static void checkTimeBarriers(Barrier parentBarrier, Barrier childBarrier) {
        if (parentBarrier == null || childBarrier == null)
            return;

        Integer fromParent = parentBarrier.getFrom();
        Integer untilParent = parentBarrier.getUntil();
        Integer fromChild = childBarrier.getFrom();
        Integer untilChild = childBarrier.getUntil();

        if (fromParent == null || untilParent == null || fromChild == null || untilChild == null) {
            return;
        }

        boolean solvable = fromChild + MINIMAL_TIME_TO_ENSURE_NEXT_ITEM_SOLVABLE < untilParent || untilChild <= fromParent;

        if (!solvable) {
            throw ApiException.bad("err.not.solvable")
                    .addLabel("from1", fromChild)
                    .addLabel("from2", fromParent)
                    .addLabel("until1", untilChild)
                    .addLabel("until2", untilParent);
        }
    }
}
