package com.jun.controller;


import com.jun.domain.Product;
import com.jun.domain.ProductCategory;
import com.jun.service.ProductCategoryService;
import com.jun.service.ProductService;
import com.jun.utils.ResultDataUtil;
import com.jun.vo.ProductInfoVo;
import com.jun.vo.ProductVo;
import com.jun.vo.ResultData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResultData list(){

        try{
            List<Product> productUpList = productService.selectAllUpProduct();

            List<Integer> productCategoryTypeList = productUpList
                    .stream()
                    .map(e -> e.getCategoryType())
                    .collect(Collectors.toList());

            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryByType(productCategoryTypeList);

            List<ProductVo> productVoList = new ArrayList<>();
            for(ProductCategory p : productCategoryList){
                ProductVo productVo = new ProductVo();
                productVo.setCategoryType(p.getCategoryType());
                productVo.setCategoryName(p.getCategoryName());

                List<ProductInfoVo> productInfoVoList = new ArrayList<>();
                for(Product product : productUpList){
                    if(product.getCategoryType().equals(p.getCategoryType())){
                        ProductInfoVo productInfoVo = new ProductInfoVo();
                        BeanUtils.copyProperties(product,productInfoVo);
                        productInfoVoList.add(productInfoVo);
                    }
                }
                productVo.setFoods(productInfoVoList);
                productVoList.add(productVo);
            }
            return ResultDataUtil.success(productVoList);

        }catch(Exception e){
            return ResultDataUtil.fail();
        }

    }

}
