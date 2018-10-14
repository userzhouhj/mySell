package com.jun.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductVo {

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("foods")
    private List<ProductInfoVo> foods;

}
