package com.company.alpicoapi.task;

import com.company.alpicoapi.api.AlpicoApi;
import com.company.alpicoapi.dto.AlpicoResponseDTO;
import com.company.alpicoapi.dto.Issue;
import com.company.alpicoapi.dto.TaskState;
import com.company.alpicoapi.exceptions.ApiException;
import com.company.alpicoapi.model.MagicItem;
import com.company.alpicoapi.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Consumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final AlpicoApi alpicoApi;
    private final LinkedBlockingQueue<MagicItem> queue;
    private final String magic;
    private final List<MagicItem> payloads;

    public Consumer(String magic, AlpicoApi alpicoApi, LinkedBlockingQueue<MagicItem> queue, List<MagicItem> payloads) {
        this.alpicoApi = alpicoApi;
        this.queue = queue;
        this.magic = magic;
        this.payloads = payloads;
    }

    @Override
    public void run() {
        while (true) {
            MagicItem take = null;
            try {
                take = queue.take();
                logger.info("Taking: {}", take);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            if (take.getIndex() == ThreadUtils.DEAD_PILL) {
                logger.info("returning deadpill");
                return;
            }
            solve(magic, take, payloads);
            if (queue.size() == 0) {
                logger.info("returning");
                return;
            }
        }
    }

    public void solve(String magic, final MagicItem item, List<MagicItem> list) {
        if (item.getTaskState() == TaskState.IN_PROGRESS || item.getTaskState() == TaskState.COMPLETED) {
            return;
        }

        item.setTaskState(TaskState.IN_PROGRESS);

        AlpicoResponseDTO apiResponseDTO = alpicoApi.getPart(magic, AlpicoApi.createRequest(item, list));

        if (apiResponseDTO.isSuccess()) {
            item.setResult(apiResponseDTO.getPayload());
            item.setTaskState(TaskState.COMPLETED);
            queue.remove(item);
            return;
        }

        if (apiResponseDTO.getIssues() == null) {
            retrySolve(item, list);
            return;
        }

        Issue issue = apiResponseDTO.getIssues()[0];

        if (issue.getMessage().startsWith("Request was made too")) {
            if (issue.getParams().getActual() > issue.getParams().getExpected().getBefore()) {
                item.setTaskState(TaskState.FAILED);
                Thread.currentThread().interrupt();
                throw ApiException.internalError("err.solving.toolate")
                        .addLabel("actual", issue.getParams().getActual())
                        .addLabel("expectedBefore", issue.getParams().getExpected().getBefore());
            }

            int waitingTime = issue.getParams().getExpected().getAfter() - issue.getParams().getActual();
            logger.info("waiting for: {}", waitingTime);
            try {
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("waited for: {}, going go retry", waitingTime);
            retrySolve(item, list);
            return;
        }

        logger.error("unexcepted retry");
        retrySolve(item, list);
    }

    public void retrySolve(MagicItem item, List<MagicItem> list) {
        item.setTaskState(TaskState.FAILED);
        solve(magic, item, list);
    }
}
