package com.hong.quartz.model;

import lombok.Data;

/**
 * @author wanghong
 * @date 2020/03/07 18:42
 *  cron任务信息
 **/
@Data
public class JobInfo {
    private String jobName;
    private String jobGroup;
    private String jobClassName;
    private String description;
    private String cronExpression;
    private String state;
}
