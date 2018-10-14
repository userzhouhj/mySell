package com.jun.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class KeyUtil {

    /**
     * 获得自定义 订单和订单详情主键
     */

    public static synchronized String getKey(){
        Date date = new Date();
        SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddhhmmss");
        String first = sm.format(date);
        String second = Integer.toString(new Random().nextInt(9000000)+1000000);
        return first+second;
    }
}
