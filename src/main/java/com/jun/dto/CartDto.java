package com.jun.dto;

import lombok.Data;


/**
 * 用于存放购物车中的属性
 */
@Data
public class CartDto {

    private String productId;

    private Integer productNum;

    public CartDto(String productId,Integer productNum) {
        this.productId = productId;
        this.productNum = productNum;
    }
}
