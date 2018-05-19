package com.hand.sxy.job.service.impl;

import com.hand.sxy.job.dto.JobRunningInfoDto;
import com.hand.sxy.job.mapper.JobRunningInfoDtoMapper;
import com.hand.sxy.job.service.IJobRunningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class JobRunningInfoService implements IJobRunningInfoService {

    @Autowired
    private JobRunningInfoDtoMapper jobRunningInfoDtoMapper;

    @Override
    public List<JobRunningInfoDto> query(JobRunningInfoDto dto, int page, int pagesize) {
        return jobRunningInfoDtoMapper.query(dto);
    }

    @Override
    public void createJobRunningInfo(JobRunningInfoDto dto) {
    }

    @Override
    public void delete(JobRunningInfoDto dto) {
        jobRunningInfoDtoMapper.deleteByNameGroup(dto);
    }

}
