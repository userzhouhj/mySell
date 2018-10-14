package com.jun.form;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductForm {

    private String productId;

    @NotEmpty(message = "商品名称不能为空")
    private String productName;

    @NotNull(message = "商品价格不能为空")
    private BigDecimal productPrice;

    @NotNull(message = "商品库存不能为空")
    private Integer productStock;

    @NotEmpty(message = "商品描述不能为空")
    private String productDescription;

    @NotEmpty(message = "商品图片地址不能为空")
    private String productIcon;

    @NotNull(message = "商品类别代号不能为空")
    private Integer categoryType;

}
