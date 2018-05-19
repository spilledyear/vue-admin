package com.hand.sxy.job.mapper;

import com.hand.sxy.job.dto.SchedulerDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SchedulerMapper {

    SchedulerDto selectByPrimaryKey(SchedulerDto key);

    List<SchedulerDto> selectSchedulers(SchedulerDto example);

}