package com.hong.quartz.service;

import com.hong.quartz.util.IPV4Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wanghong
 * @date 2020/03/08 11:44
 *  Quartz抽象任务骨架定义
 **/
@Slf4j
public class BaseJob {

    @Autowired
    private JobExceptionHandler jobExceptionHandler;

    public void run(String jobName) {
        String ip = IPV4Util.getLocalIpv4Address();
        log.info("begin jobName=[{}],executorIp=[{}]", jobName, ip);
        try {
            doWork();
        } catch (Exception e) {
            jobExceptionHandler.handle(jobName,e);
        }
        log.info("end jobName=[{}],executorIp=[{}]", jobName, ip);
    }

    /**
     * 由子类覆写该方法，实现具体的定时任务逻辑
     */
    public void doWork(){
    }

}
