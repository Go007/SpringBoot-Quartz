package com.hong.quartz.service;

import com.github.pagehelper.PageInfo;
import com.hong.quartz.common.CommonResultVO;
import com.hong.quartz.model.JobInfo;

/**
 * @author wanghong
 * @date 2020/03/07 18:35
 **/
public interface IQuartzService {

    CommonResultVO addJob(JobInfo jobInfo);

    PageInfo<JobInfo> queryJob(int pageNum,int pageSize);

    CommonResultVO pauseJob(String jobGroup, String jobClassName);

    CommonResultVO resumeJob(String jobClassName, String jobGroup);

    CommonResultVO updateJob(JobInfo jobInfo);

    CommonResultVO deleteJob(String jobClassName, String jobGroup);
}
