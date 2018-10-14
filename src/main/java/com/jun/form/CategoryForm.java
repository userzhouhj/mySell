package com.jun.form;


import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


/**
 * 前端表单属性
 */
@Data
public class CategoryForm {


    private Integer categoryId;

    @NotEmpty(message = "类别名称必填")
    private String categoryName;

    @NotNull(message = "类别编号必填")
    private Integer categoryType;

}
