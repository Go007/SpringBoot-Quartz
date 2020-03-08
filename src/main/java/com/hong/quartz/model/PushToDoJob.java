package com.hong.quartz.model;

import com.hong.quartz.service.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wanghong
 * @date 2020/03/07 22:25
 **/
@Slf4j
@Component("pushToDoJob")
public class PushToDoJob extends BaseJob {

    @Override
    public void doWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Push To Do Job");
    }
}
