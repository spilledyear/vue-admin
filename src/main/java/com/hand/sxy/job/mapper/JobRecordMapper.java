package com.hand.sxy.job.mapper;

import com.hand.sxy.job.dto.JobRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JobRecordMapper {

    /**
     * 插入执行记录
     *
     * @param dto
     */
    void insert(JobRecord dto);

    /**
     * 删除记录
     *
     * @param dto
     */
    void delete(JobRecord dto);

    List<JobRecord> query(JobRecord dto);

}