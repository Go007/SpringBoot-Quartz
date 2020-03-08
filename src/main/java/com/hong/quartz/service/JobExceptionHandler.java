package com.hong.quartz.service;

import com.hong.quartz.util.IPV4Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author wanghong
 * @date 2020/03/07 19:10
 *  定时任务执行异常处理器
 **/
@Slf4j
@Component
public class JobExceptionHandler {

    /**
     * 异步执行定时任务执行异常的通用处理方法
     * @param jobName 任务名称
     * @param exception
     */
    @Async
    public void handle(String jobName, Exception exception) {
        log.error("error jobName=[{}],executorIp=[{}],exception=[{}]", jobName, IPV4Util.getLocalIpv4Address(), exception);
    }
}
