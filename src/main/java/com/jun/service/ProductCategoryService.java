package com.jun.service;

import com.jun.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    public ProductCategory findOneById(Integer id)throws Exception;

    public List<ProductCategory> getAllProductCategory()throws Exception;

    public ProductCategory save(ProductCategory productCategory)throws Exception;

    public ProductCategory update(ProductCategory productCategory)throws Exception;

    public List<ProductCategory> getProductCategoryByType(List<Integer> categoryTypeList)throws Exception;

}
