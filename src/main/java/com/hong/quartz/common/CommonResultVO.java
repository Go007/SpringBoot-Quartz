package com.hong.quartz.common;

import lombok.Data;

/**
 * @author wanghong
 * @date 2020/03/07 18:39
 **/
@Data
public class CommonResultVO<T> {
    protected String responseCode = "000000";
    protected String responseMsg = "成功";
    protected T data;
}
