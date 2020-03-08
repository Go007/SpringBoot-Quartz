package com.hong.quartz.model;

import com.hong.quartz.service.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wanghong
 * @date 2020/03/08 12:50
 **/
@Slf4j
@Component("helloJob")
public class HelloJob extends BaseJob {
    @Override
    public void doWork() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Hello Job");
    }
}
