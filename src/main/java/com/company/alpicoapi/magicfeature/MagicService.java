package com.company.alpicoapi.magicfeature;

import com.company.alpicoapi.api.AlpicoApi;
import com.company.alpicoapi.model.MagicItem;
import com.company.alpicoapi.task.Consumer;
import com.company.alpicoapi.utils.MagicItemUtils;
import com.company.alpicoapi.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MagicService {

    private static final int NUMBER_OF_CONSUMERS = 1;

    private final AlpicoApi alpicoApi;

    public MagicService(AlpicoApi alpicoApi) {
        this.alpicoApi = alpicoApi;
    }

    public byte[] getMagicImage(String magic) {
        String[] magicTokens = alpicoApi.initialize(magic);
        List<MagicItem> readyToSolveList = MagicItemUtils.createMagicItems(magicTokens);

        MagicItemUtils.checkTimeBarriers(readyToSolveList);
        LinkedBlockingQueue<MagicItem> taskQueue = new LinkedBlockingQueue<>(readyToSolveList);

        ThreadUtils.addDeadpills(taskQueue, NUMBER_OF_CONSUMERS);

//        new Consumer(magic, alpicoApi, taskQueue, readyToSolveList).run();

        List<Thread> threads = ThreadUtils.run(
                new Consumer(magic, alpicoApi, taskQueue, readyToSolveList),
                NUMBER_OF_CONSUMERS);
        ThreadUtils.join(threads);

        return MagicItemUtils.tokensToSvg(readyToSolveList);
    }
}
