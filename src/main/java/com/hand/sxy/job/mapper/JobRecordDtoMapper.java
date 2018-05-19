package com.hand.sxy.job.mapper;

import com.hand.sxy.job.dto.JobRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JobRecordDtoMapper {

    void deleteByNameGroup(JobRecord dto);

    List<JobRecord> query(JobRecord dto);
}