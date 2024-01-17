package com.company.alpicoapi.utils;

import com.company.alpicoapi.exceptions.ApiException;
import com.company.alpicoapi.model.MagicItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadUtils {
    public static final Integer DEAD_PILL = -1;

    public static void join(List<Thread> threads) {
        try {
            for (Thread th : threads)
                th.join();
        } catch (InterruptedException e) {
            throw ApiException.internalError("err.internal");
        }
    }

    public static List<Thread> run(Runnable runnable, Integer numberOfThreads) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
            threads.add(thread);
        }
        return threads;
    }

    public static void addDeadpills(LinkedBlockingQueue<MagicItem> testing, Integer numberOfThreads) {
        for (int i = 0; i < numberOfThreads; i++) {
            testing.add(new MagicItem().setIndex(DEAD_PILL));
        }
    }
}
