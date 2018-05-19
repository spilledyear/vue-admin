package com.hand.sxy.job.mapper;

import com.hand.sxy.job.dto.CronTriggerDto;
import org.springframework.stereotype.Component;

@Component
public interface CronTriggerMapper {
    CronTriggerDto selectByPrimaryKey(CronTriggerDto key);
}