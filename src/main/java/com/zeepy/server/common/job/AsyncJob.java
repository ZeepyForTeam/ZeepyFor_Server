package com.zeepy.server.common.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Minky on 2021-09-01
 */

@Service
public class AsyncJob {
    private static final Logger logger = LoggerFactory.getLogger(AsyncJob.class);

    @Async("asyncExecutor")
    public void onStart(Callable callable) {
        try {
            Thread.sleep(1000);
            logger.info("Job is Start");
            callable.call();
            logger.info("Job is End");
        } catch (InterruptedException e) {
            logger.info("Job is Failed");
        }
    }
}
