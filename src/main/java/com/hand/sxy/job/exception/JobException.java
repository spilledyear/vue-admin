package com.hand.sxy.job.exception;


public class JobException extends Throwable{

    public static final String JOB_EXCEPTION = "JOB_EXCEPTION";

    public static final String NOT_SUB_CLASS = "job.error.invalid_job_class";

    public static final String NOT_TRIGGER = "job.error.has_no_trigger";

    public JobException(String parameters) {
    }

    private static final long serialVersionUID = 2809497996266500743L;

}
