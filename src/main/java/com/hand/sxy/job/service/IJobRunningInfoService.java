package com.hand.sxy.job.service;

import com.hand.sxy.job.dto.JobRunningInfoDto;

import java.util.List;


public interface IJobRunningInfoService {

    /**
     * 查询Job运行记录
     *
     * @param dto
     * @param page
     * @param pagesize
     * @return
     */
    List<JobRunningInfoDto> query(JobRunningInfoDto dto, int page, int pagesize);

    /**
     * 新建运行记录.
     *
     * @param dto 运行记录
     */
    void createJobRunningInfo(JobRunningInfoDto dto);


    /**
     * 删除运行记录
     *
     * @param dto 记录
     */
    void delete(JobRunningInfoDto dto);
}
