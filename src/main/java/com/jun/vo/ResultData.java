package com.jun.vo;


import lombok.Getter;
import lombok.Setter;


/**
 * 用与前端与控制层进行数据交换
 * @param <T>
 */

@Getter@Setter
public class ResultData<T> {

    /*状态码*/
    private Integer code;

    /*消息*/
    private String msg;

    /* 封装的数据类型 */
    private T data;

    public ResultData(){}
    public ResultData(Integer code,String msg,T data){
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

}
