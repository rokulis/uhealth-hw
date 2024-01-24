package com.company.alpicoapi.model;

import com.company.alpicoapi.dto.TaskState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MagicItem {
    private int index;
    private int total;
    private long epoch;
    private String seed;
    private String magic;
    private List<Barrier> barriers = new ArrayList<>();
    private long iat;
    private String token;
    private String result;

    private TaskState taskState;
    private Barrier timeBarrier;
    private Barrier constBarrier;
    private List<MagicItem> dependencies = new ArrayList<MagicItem>();

    public int getIndex() {
        return index;
    }

    public MagicItem setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public MagicItem setTotal(int total) {
        this.total = total;
        return this;
    }

    public long getEpoch() {
        return epoch;
    }

    public MagicItem setEpoch(long epoch) {
        this.epoch = epoch;
        return this;
    }

    public String getSeed() {
        return seed;
    }

    public MagicItem setSeed(String seed) {
        this.seed = seed;
        return this;
    }

    public String getMagic() {
        return magic;
    }

    public MagicItem setMagic(String magic) {
        this.magic = magic;
        return this;
    }

    public List<Barrier> getBarriers() {
        return barriers;
    }

    public MagicItem setBarriers(List<Barrier> barriers) {
        this.barriers = barriers;
        return this;
    }

    public long getIat() {
        return iat;
    }

    public MagicItem setIat(long iat) {
        this.iat = iat;
        return this;
    }

    public String getToken() {
        return token;
    }

    public MagicItem setToken(String token) {
        this.token = token;
        return this;
    }

    public String getResult() {
        return result;
    }

    public MagicItem setResult(String result) {
        this.result = result;
        return this;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public MagicItem setTaskState(TaskState taskState) {
        this.taskState = taskState;
        return this;
    }

    public Barrier getTimeBarrier() {
        return timeBarrier;
    }

    public MagicItem setTimeBarrier(Barrier timeBarrier) {
        this.timeBarrier = timeBarrier;
        return this;
    }

    public List<MagicItem> getDependencies() {
        return dependencies;
    }

    public MagicItem setDependencies(List<MagicItem> dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    public Barrier getConstBarrier() {
        return constBarrier;
    }

    public MagicItem setConstBarrier(Barrier constBarrier) {
        this.constBarrier = constBarrier;
        return this;
    }

    public static Comparator<MagicItem> timeBarrierComparator() {
        return (jp1, jp2) -> {
            boolean jp1HasTimeBarrier = jp1.getTimeBarrier() != null;
            boolean jp2HasTimeBarrier = jp2.getTimeBarrier() != null;

            if (jp1HasTimeBarrier && !jp2HasTimeBarrier) {
                return -1;
            } else if (!jp1HasTimeBarrier && jp2HasTimeBarrier) {
                return 1;
            } else if (!jp1HasTimeBarrier) {
                return 0;
            }

            Integer from1 = jp1.getTimeBarrier().getFrom();
            Integer from2 = jp2.getTimeBarrier().getFrom();
            return from1.compareTo(from2);
        };
    }

    @Override
    public String toString() {
//        return "MagicItem{" +
//                "index=" + index +
//                ", taskState=" + taskState +
//                ", timeBarrier=" + timeBarrier +
//                ", dependencies=" + dependencies +
//                '}';
        return "hi";
    }
}
