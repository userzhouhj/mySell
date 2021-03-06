package com.jun.controller;


import com.github.pagehelper.PageInfo;
import com.jun.domain.Product;
import com.jun.domain.ProductCategory;
import com.jun.form.ProductForm;
import com.jun.service.ProductCategoryService;
import com.jun.service.ProductService;
import com.jun.utils.KeyUtil;
import com.jun.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "size",required = false,defaultValue = "5") Integer size,
                             @RequestParam(value = "page",required = false,defaultValue = "1") Integer page){
        ModelAndView model = new ModelAndView();

        List<Product> productList = productService.selectAllProduct();
        int total = PageUtil.getTotal(productList,size);//总页数

        productList = PageUtil.getList(productList,page,size);

        PageInfo pageInfo = PageUtil.getPageInfo(productList,total,page,size);

        model.addObject("size",size);
        model.addObject("currentPage",page);
        model.addObject("productList",productList);
        model.addObject("pageInfo",pageInfo);
        model.setViewName("product/list");
        return model;
    }

    /**
     * 商品下架
     * @param productId
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView off(@RequestParam("productId") String productId){
        ModelAndView model = new ModelAndView();

        if(productId != null && !productId.equals("")){
            Product product = productService.offSell(productId);
            if(product != null){
                model.setViewName("redirect:/seller/product/list");
            }else{
                model.setViewName("common/error");
                model.addObject("url","/sell/seller/product/list");
            }
        }

        return model;
    }


    /**
     * 商品上架
     * @param productId
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView on(@RequestParam("productId") String productId){
        ModelAndView model = new ModelAndView();

        if(productId != null && !productId.equals("")){
            Product product = productService.onSell(productId);
            if(product != null){
                model.setViewName("redirect:/seller/product/list");
            }else{
                model.setViewName("common/error");
                model.addObject("url","/sell/seller/product/list");
            }
        }

        return model;
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId){
        ModelAndView model = new ModelAndView();

        if(productId != null && !productId.equals("")){
            //代表修改商品
            Product product = productService.selectProductById(productId);
            model.addObject("product",product);
        }
        try{
            List<ProductCategory> productCategoryList = productCategoryService.getAllProductCategory();
            model.addObject("categoryList",productCategoryList);
            model.setViewName("product/index");
        }catch(Exception e){
            throw new RuntimeException("程序错误");
        }
        return model;
    }


    /**
     * 商品的增加或修改
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult){
        ModelAndView model = new ModelAndView();

        if(bindingResult.hasErrors()){
            model.addObject("error","数据绑定错误");
            model.addObject("url","/sell/seller/product/list");
            model.setViewName("common/error");
            return model;
        }

        Product product = new Product();
        try{
            if(productForm.getProductId() == null || productForm.getProductId().equals("")){//新增

                String productId = KeyUtil.getKey();
                productForm.setProductId(productId);
                BeanUtils.copyProperties(productForm,product);
                productService.save(product);
            }else{//修改

                BeanUtils.copyProperties(productForm,product);
                productService.update(product);
            }
        }catch(Exception e){
            model.addObject("error",e.getMessage());
            model.addObject("url","/sell/seller/product/list");
            model.setViewName("common/error");
            return model;
        }

        model.setViewName("common/success");
        model.addObject("url","/sell/seller/product/list");

        return model;
    }

}
