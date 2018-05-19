package com.hand.sxy.job.mapper;

import com.hand.sxy.job.dto.TriggerDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TriggerMapper {
    TriggerDto selectByPrimaryKey(TriggerDto key);

    List<TriggerDto> selectTriggers(TriggerDto example);

}