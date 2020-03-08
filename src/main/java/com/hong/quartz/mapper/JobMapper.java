package com.hong.quartz.mapper;

import com.hong.quartz.model.JobInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanghong
 * @date 2020/03/07 21:59
 **/
@Repository
public interface JobMapper {
    List<JobInfo> getJobInfo();

    Integer getJobByName(String jobName);
}
