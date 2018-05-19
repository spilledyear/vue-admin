package com.hand.sxy.job.mapper;

import com.hand.sxy.job.dto.SimpleTriggerDto;
import org.springframework.stereotype.Component;

@Component
public interface SimpleTriggerMapper {

    SimpleTriggerDto selectByPrimaryKey(SimpleTriggerDto key);
}