/*
 * #{copyright}#
 */
package com.hand.sxy.job.mapper;

import com.hand.sxy.job.dto.JobRunningInfoDto;
import org.springframework.stereotype.Component;

@Component
public interface JobRunningInfoDtoMapper {

    void deleteByNameGroup(JobRunningInfoDto example);

}