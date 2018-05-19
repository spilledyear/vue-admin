package com.hand.sxy.job.service.impl;

import com.hand.sxy.job.dto.JobRecord;
import com.hand.sxy.job.mapper.JobRecordMapper;
import com.hand.sxy.job.service.IJobRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class JobRecordService implements IJobRecordService {

    @Autowired
    private JobRecordMapper jobRecordMapper;

    @Override
    public List<JobRecord> query(JobRecord dto, int page, int pagesize) {
        return jobRecordMapper.query(dto);
    }

    @Override
    public void insert(JobRecord dto) {
        jobRecordMapper.insert(dto);
    }

    @Override
    public void delete(JobRecord dto) {
        jobRecordMapper.delete(dto);
    }

}
