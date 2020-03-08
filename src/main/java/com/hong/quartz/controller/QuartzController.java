package com.hong.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.hong.quartz.common.CommonResultVO;
import com.hong.quartz.model.JobInfo;
import com.hong.quartz.service.IQuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghong
 * @date 2020/03/06 22:43
 **/
@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    private IQuartzService quartzService;

    /**
     * 添加定时任务
     * @param jobInfo
     * @return
     */
    @PostMapping(value = "/addJob")
    public CommonResultVO addJob(@RequestBody JobInfo jobInfo){
        return quartzService.addJob(jobInfo);
    }

    /**
     * 查询任务
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/queryJob")
    public Map<String, Object> queryJob(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        PageInfo<JobInfo> jobAndTrigger = quartzService.queryJob(pageNum, pageSize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", jobAndTrigger);
        map.put("total", jobAndTrigger.getTotal());
        return map;
    }

    @PostMapping(value = "pauseJob")
    public CommonResultVO pauseJob(@RequestParam(value = "jobClassName") String jobClassName,@RequestParam(value = "jobGroup") String jobGroup){
        return quartzService.pauseJob(jobClassName,jobGroup);
    }

    @PostMapping(value = "resumeJob")
    public CommonResultVO resumeJob(@RequestParam(value = "jobClassName") String jobClassName,@RequestParam(value = "jobGroup") String jobGroup){
        return quartzService.resumeJob(jobClassName,jobGroup);
    }

    @PostMapping(value = "/updateJob")
    public CommonResultVO updateJob(@RequestBody JobInfo jobInfo){
        return quartzService.updateJob(jobInfo);
    }

    @PostMapping(value = "/deleteJob")
    public CommonResultVO deleteJob(@RequestParam(value = "jobClassName") String jobClassName,@RequestParam(value = "jobGroup") String jobGroup){
        return quartzService.deleteJob(jobClassName,jobGroup);
    }

}
