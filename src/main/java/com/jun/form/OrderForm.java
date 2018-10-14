package com.jun.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class OrderForm {

    @JsonProperty("name")
    @NotEmpty(message="姓名必填")
    private String name;

    @JsonProperty("phone")
    @NotEmpty(message="电话必填")
    private String phone;

    @JsonProperty("address")
    @NotEmpty(message="地址必填")
    private String address;

    @JsonProperty("openid")
    @NotEmpty(message="openid必填")
    private String openid;

    @JsonProperty("items")
    @NotEmpty(message="购物车不能为空")
    private String items;

}
