package com.wjh.util;

import java.util.Date;
import java.util.UUID;

public class StringUtil {
    private StringUtil() {
    }
    /**
     * 生成随机字符串
     * UUID(4)+time(4)
     */
    public static String gerRandomString(){
        String uuid = UUID.randomUUID().toString();
        String time = new Date().getTime()+"";
        return time;
    }
}
