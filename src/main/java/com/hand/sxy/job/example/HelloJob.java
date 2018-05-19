package com.hand.sxy.job.example;

import com.hand.sxy.job.BasalJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author spilledyear
 */
public class HelloJob implements BasalJob {

    private static Logger logger = LoggerFactory.getLogger(HelloJob.class);

    public HelloJob() {

    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Hello Job执行时间: " + new Date());

    }
}  
