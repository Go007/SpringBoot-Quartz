package com.hong.quartz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hong.quartz.common.CommonResultVO;
import com.hong.quartz.mapper.JobMapper;
import com.hong.quartz.model.JobInfo;
import com.hong.quartz.model.QuartzMainJobFactory;
import com.hong.quartz.service.BaseJob;
import com.hong.quartz.service.IQuartzService;
import com.hong.quartz.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghong
 * @date 2020/03/07 18:44
 **/
@Service
public class QuartzServiceImpl implements IQuartzService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobMapper jobMapper;

    @Override
    public CommonResultVO addJob(JobInfo jobInfo) {
        CommonResultVO vo = new CommonResultVO();

        try {
            //构建job信息
            String jobClassName = jobInfo.getJobClassName();
            // 校验 任务类 是否已经存在
            if (jobMapper.getJobByName(jobClassName) > 0){
                vo.setData("任务已存在");
                return vo;
            }

            String verifyInfo = verifyJobInfo(jobInfo);
            if (StringUtils.isNotEmpty(verifyInfo)) {
                vo.setData(verifyInfo);
                return vo;
            }

            // String jobGroup = jobInfo.getJobGroup();
            String description = jobInfo.getDescription();
            String cron = jobInfo.getCronExpression();

            JobDetail jobDetail = JobBuilder.newJob(QuartzMainJobFactory.class)
                    .withDescription(description)
                    .withIdentity(jobClassName, Scheduler.DEFAULT_GROUP)
                    .build();

            jobDetail.getJobDataMap().put("jobName", jobClassName);

            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withDescription(description)
                    .withIdentity(jobClassName, Scheduler.DEFAULT_GROUP)
                    .withSchedule(scheduleBuilder)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
        }

        return vo;
    }

    @Override
    public PageInfo<JobInfo> queryJob(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobInfo> list = jobMapper.getJobInfo();
        for (JobInfo jobInfo:list){
           jobInfo.setJobClassName(SpringUtil.getBean(jobInfo.getJobName()).getClass().getName());
        }
        PageInfo<JobInfo> page = new PageInfo<>(list);
        return page;
    }

    @Override
    public CommonResultVO pauseJob(String jobClassName, String jobGroup) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return new CommonResultVO();
    }

    @Override
    public CommonResultVO resumeJob(String jobClassName, String jobGroup) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return new CommonResultVO();
    }

    @Override
    public CommonResultVO updateJob(JobInfo jobInfo) {
        CommonResultVO vo = new CommonResultVO();
        if (!CronExpression.isValidExpression(jobInfo.getCronExpression())) {
            vo.setData("cron表达式错误");
            return vo;
        }

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJobClassName(), jobInfo.getJobGroup());
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCronExpression());

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
        }

        return vo;
    }

    /**
     * 删除操作前应该暂停该任务的触发器，并且停止该任务的执行
     * @param jobClassName
     * @param jobGroup
     * @return
     */
    @Override
    public CommonResultVO deleteJob(String jobClassName, String jobGroup) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroup));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroup));
            scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return new CommonResultVO();
    }


    private String verifyJobInfo(JobInfo jobInfo) {
        StringBuilder sb = new StringBuilder();

        // 验证 cron 表达式 是否正确
        if (!CronExpression.isValidExpression(jobInfo.getCronExpression())) {
            sb.append("cron表达式错误;");
        }

        // 验证 执行 bean 名称和类名 是否有效
        Object job = null;
        try {
            job = SpringUtil.getBean(jobInfo.getJobClassName());
            if (!(job instanceof BaseJob)) {
                sb.append("任务非BaseJob类型;");
            }
        } catch (NoSuchBeanDefinitionException e) {
            sb.append("任务名称错误;");
        }

        return sb.toString();
    }

}
