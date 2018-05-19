package com.hand.sxy.job.mapper;

import com.hand.sxy.job.dto.JobDetailDto;
import com.hand.sxy.job.dto.JobInfoDetailDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author spilledyear
 */
@Component
public interface JobDetailMapper {
    JobDetailDto selectByPrimaryKey(JobDetailDto key);

    List<JobDetailDto> selectJobDetails(JobDetailDto example);

    List<JobInfoDetailDto> selectJobInfoDetails(JobDetailDto example);
}