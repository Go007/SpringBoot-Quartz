package com.hong.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

/**
 * @author wanghong
 * @date 2020/02/05 18:54
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartzApplicationTests {

    @Test
    public void test() {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("/quartz.yml"));
        Properties p = yaml.getObject();
        System.out.println(p);
    }
}
