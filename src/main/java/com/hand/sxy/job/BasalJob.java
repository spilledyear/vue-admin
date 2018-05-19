package com.hand.sxy.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author spilledyear
 */
public interface BasalJob extends Job {

    /**
     * Job执行入口
     *
     * @param context
     * @throws JobExecutionException
     */
    @Override
    void execute(JobExecutionContext context) throws JobExecutionException;
}

