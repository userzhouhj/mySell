package com.jun.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),OK(1,"已结算"),CANCEL(2,"已取消");

    private Integer code;

    private String msg;
    private OrderStatusEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
