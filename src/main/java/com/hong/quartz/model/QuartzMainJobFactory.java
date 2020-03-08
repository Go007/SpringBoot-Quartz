package com.hong.quartz.model;

import com.hong.quartz.service.BaseJob;
import com.hong.quartz.util.SpringUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * @author wanghong
 * @date 2020/03/07 19:02
 *  Quartz定时任务的主要执行逻辑，实现Job接口
 **/
@DisallowConcurrentExecution
public class QuartzMainJobFactory implements Job {

    /**
     * 定时任务逻辑实现方法
     * 每当触发器触发时会执行该方法逻辑
     * @param jobExecutionContext
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String jobName = jobDataMap.getString("jobName");
        BaseJob baseJob = (BaseJob) SpringUtil.getBean(jobName);
        baseJob.run(jobName);
    }
}
