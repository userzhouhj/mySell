package com.jun.utils;

import com.jun.enums.CodeEnum;


/**
 * 枚举工具类：通过对自定义枚举类的扩展，获得指定的枚举类
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for(T each : enumClass.getEnumConstants()){
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }

}
