package com.hand.sxy.job.mapper;

import com.hand.sxy.job.dto.JobRunningInfoDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JobRunningInfoDtoMapper {

    void deleteByNameGroup(JobRunningInfoDto dto);

    List<JobRunningInfoDto> query(JobRunningInfoDto dto);
}