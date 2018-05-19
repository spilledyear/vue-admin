package com.hand.sxy.job.plugin;

import com.hand.sxy.job.listener.JobRecordListener;
import com.hand.sxy.job.listener.SchedulerRecordListener;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.springframework.context.ApplicationContext;

/**
 * @author spilledyear
 */
public class RcordSchedulerPlugin implements SchedulerPlugin {

    private ApplicationContext applicationContext;

    private Scheduler scheduler;


    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {
        this.scheduler = scheduler;
    }


    @Override
    public void start() {
        try {
            applicationContext = (ApplicationContext) scheduler.getContext().get("applicationContext");
            ListenerManager listenerManager = scheduler.getListenerManager();
            listenerManager.addJobListener(new JobRecordListener(applicationContext), EverythingMatcher.allJobs());
            listenerManager.addSchedulerListener(new SchedulerRecordListener(applicationContext));

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void shutdown() {

    }

}
