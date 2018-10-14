package com.jun.enums;


import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum {
    ON(0,"在架"),OFF(1,"下架");

    private Integer code;

    private String msg;

    private ProductStatusEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }


}
