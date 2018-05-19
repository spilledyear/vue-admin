package com.hand.sxy.job.service;

import com.hand.sxy.job.dto.JobRecord;

import java.util.List;


public interface IJobRecordService {

    /**
     * 查询Job运行记录
     *
     * @param dto
     * @param page
     * @param pagesize
     * @return
     */
    List<JobRecord> query(JobRecord dto, int page, int pagesize);

    /**
     * 新建运行记录.
     *
     * @param dto 运行记录
     */
    void createJobRunningInfo(JobRecord dto);


    /**
     * 删除运行记录
     *
     * @param dto 记录
     */
    void delete(JobRecord dto);
}
