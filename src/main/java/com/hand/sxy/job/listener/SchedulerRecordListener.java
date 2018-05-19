package com.hand.sxy.job.listener;

import com.hand.sxy.job.dto.JobRecord;
import com.hand.sxy.job.service.IJobRecordService;
import org.quartz.JobKey;
import org.quartz.listeners.SchedulerListenerSupport;
import org.springframework.context.ApplicationContext;

/**
 * @author spilledyear
 */
public class SchedulerRecordListener extends SchedulerListenerSupport {

    private static final String JOB_INFO_HAS_DELETED = "Job Info [{}.{}] has deleted.";
    private static final String JOB_WAS_DELETED_FROM_SCHEDULER = "Job [{}.{}] was deleted from Scheduler.";
    private final ApplicationContext applicationContext;

    public SchedulerRecordListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public void jobDeleted(JobKey jobKey) {
        JobRecord dto = new JobRecord();
        String group = jobKey.getGroup();
        String name = jobKey.getName();
        logInfo(JOB_WAS_DELETED_FROM_SCHEDULER, group, name);
        dto.setJobName(name);
        dto.setJobGroup(group);
        deleteJobInfo(dto);
        logInfo(JOB_INFO_HAS_DELETED, group, name);
    }

    private void deleteJobInfo(JobRecord jobCreateDto) {
        IJobRecordService jobRecordService = applicationContext.getBean(IJobRecordService.class);
        jobRecordService.delete(jobCreateDto);
    }

    protected void logInfo(String info, Object... para) {
        if (getLog().isInfoEnabled()) {
            getLog().info(info, para);
        }
    }
}
