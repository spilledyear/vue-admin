/*
 * #{copyright}#
 */

package com.hand.sxy.job.controller;


import com.hand.sxy.job.dto.JobRunningInfoDto;
import com.hand.sxy.job.service.IJobRunningInfoService;
import com.hand.sxy.system.dto.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liyan.shi@hand-china.com
 */
@Controller
@RequestMapping(value = {"/job/jobinfo", "/api/job/jobinfo"})
public class RunningInfoController {
    @Autowired
    private IJobRunningInfoService jobRunningInfoService;

    /**
     * 查询Job运行记录
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public ResultResponse query(JobRunningInfoDto dto) {
        int page = 1;
        int pagesize = 20;
        return new ResultResponse(jobRunningInfoService.query(dto, page, pagesize));
    }

}
