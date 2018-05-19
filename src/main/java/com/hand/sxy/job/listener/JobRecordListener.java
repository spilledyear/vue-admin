package com.hand.sxy.job.listener;

import com.hand.sxy.job.dto.JobRecord;
import com.hand.sxy.job.service.IJobRecordService;
import org.quartz.*;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.context.ApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author shiliyan
 */
public class JobRecordListener extends JobListenerSupport {

    private static final String VETOED = "Vetoed";

    private static final String FINISH = "Finish";

    private static final String FAILED = "Failed";


    private ApplicationContext applicationContext;

    public JobRecordListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public String getName() {
        return "JobRecordListener";
    }


    /**
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        JobRecord dto = getRecord(context);
        if (jobException != null) {
            String errMsg = jobException.getMessage();
            dto.setJobStatusMessage(errMsg);
            dto.setJobStatus(FAILED);
        } else {
            dto.setJobStatus(FINISH);
        }
        this.insert(dto);
        Job job = context.getJobInstance();
        if (job instanceof JobListener) {
            context.put("JOB_RUNNING_INFO_ID", dto.getJobRecordId());
            ((JobListener) job).jobWasExecuted(context, jobException);
        }
    }


    private JobRecord getRecord(JobExecutionContext context) {
        JobRecord record = new JobRecord();
        JobDetail jobDetail = context.getJobDetail();
        String jobName = jobDetail.getKey().getName();
        String jobGroup = jobDetail.getKey().getGroup();
        Trigger trigger = context.getTrigger();
        String triggerName = trigger.getKey().getName();
        String triggerGroup = trigger.getKey().getGroup();
        Date nextFireTime = trigger.getNextFireTime();
        int refireCount = context.getRefireCount();
        String fireInstanceId = context.getFireInstanceId();
        Date fireTime = context.getFireTime();

        Date previousFireTime = context.getPreviousFireTime();
        Object result = context.getResult();
        Date scheduledFireTime = context.getScheduledFireTime();


        String schedulerInstanceId = "";
        try {
            schedulerInstanceId = context.getScheduler().getSchedulerInstanceId();
        } catch (SchedulerException e) {
            if (getLog().isErrorEnabled()) {
                getLog().error(e.getMessage(), e);
            }
        }
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        record.setIpAddress(inetAddress.getHostAddress());
        record.setFireInstanceId(fireInstanceId);
        record.setFireTime(fireTime);
        record.setJobGroup(jobGroup);
        record.setJobName(jobName);
        record.setJobResult(result == null ? "" : String.valueOf(context.getResult()));
        record.setNextFireTime(nextFireTime);
        record.setPreviousFireTime(previousFireTime);
        record.setRefireCount((long) refireCount);
        record.setScheduledFireTime(scheduledFireTime);
        record.setSchedulerInstanceId(schedulerInstanceId);
        record.setTriggerGroup(triggerGroup);
        record.setTriggerName(triggerName);
        return record;
    }


    private void insert(JobRecord dto) {
        IJobRecordService jobRecordService = applicationContext.getBean(IJobRecordService.class);
        jobRecordService.insert(dto);
    }
}
