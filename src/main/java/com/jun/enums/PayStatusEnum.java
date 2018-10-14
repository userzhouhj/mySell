package com.jun.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements CodeEnum {

    WAIT(0,"未支付"),SUCCESS(1,"支付完毕");

    private Integer code;
    private String msg;

    private PayStatusEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
