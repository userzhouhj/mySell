package com.jun.controller;


import com.jun.domain.ProductCategory;
import com.jun.form.CategoryForm;
import com.jun.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/seller")
public class SellerProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 商家的商品类别展示列表
     * @return
     */
    @RequestMapping(value = "/category/list",method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView model = new ModelAndView();
        List<ProductCategory> productCategoryList = new ArrayList<>();
        try{
            productCategoryList = productCategoryService.getAllProductCategory();
            model.addObject("categoryList",productCategoryList);
            model.setViewName("category/list");
        }catch(Exception e){
            model.setViewName("common/error");
        }
        return model;
    }

    @GetMapping("/category/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) String categoryId){
        ModelAndView model = new ModelAndView();
        try{
            if(categoryId != null && !categoryId.equals("")){
                ProductCategory productCategory = productCategoryService.findOneById(Integer.parseInt(categoryId));
                model.addObject("category",productCategory);
            }

        }catch(Exception e){
            model.setViewName("common/error");
            return model;
        }
        model.setViewName("category/index");
        return model;
    }

    /**
     * 保存/更新商品类别
     * @param categoryForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/category/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult){
        ModelAndView model = new ModelAndView();

        if(bindingResult.hasErrors()){
            model.addObject("error","表单填写有误");
            model.addObject("url","/sell/seller/category/index");
            model.setViewName("common/error");
        }
        ProductCategory productCategory = new ProductCategory();
        try{
            if(categoryForm.getCategoryId() != null && !categoryForm.equals("")){//更新
                productCategory = productCategoryService.findOneById(categoryForm.getCategoryId());
                BeanUtils.copyProperties(categoryForm,productCategory);
                productCategoryService.update(productCategory);
            }else{
                //新增
                BeanUtils.copyProperties(categoryForm,productCategory);
                productCategoryService.save(productCategory);
            }

            model.addObject("url","/sell/seller/category/list");
            model.setViewName("common/success");
        }catch(Exception e){
            model.addObject("url","/sell/seller/category/index");
            model.addObject("error",e.getMessage());
            model.setViewName("common/error");
        }
        return model;
    }


}
