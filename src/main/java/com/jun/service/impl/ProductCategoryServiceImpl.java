package com.jun.service.impl;

import com.jun.dao.ProductCategoryMapper;
import com.jun.domain.ProductCategory;
import com.jun.domain.ProductCategoryExample;
import com.jun.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Cacheable(cacheNames = "category",key="'findOneBy_'+#id",cacheManager = "categoryRedisCacheManager")
    public ProductCategory findOneById(Integer id) throws Exception{
        return productCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    @Cacheable(cacheNames = "category",key = "#root.methodName",cacheManager = "categoryRedisCacheManager")
    public List<ProductCategory> getAllProductCategory() throws Exception{
        return productCategoryMapper.selectByExample(null);
    }

    @Override
    @CacheEvict(cacheNames = "category",key = "'getAllProductCategory'")
    public ProductCategory save(ProductCategory productCategory) throws Exception{
        if(productCategory != null){
            productCategory.setCreateTime(new Date());
            productCategory.setUpdateTime(new Date());
            productCategoryMapper.insert(productCategory);
            return productCategory;
        }
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "category",key = "'getAllProductCategory'")
    public ProductCategory update(ProductCategory productCategory) throws Exception{
        if(productCategory != null){
            ProductCategory target = productCategoryMapper.selectByPrimaryKey(productCategory.getCategoryId());
            if(productCategory.getCategoryName() != null && !productCategory.getCategoryName().equals("")){
                target.setCategoryName(productCategory.getCategoryName());
            }
            if(productCategory.getCategoryType() != null){
                target.setCategoryType(productCategory.getCategoryType());
            }

            target.setUpdateTime(new Date());

            productCategoryMapper.updateByPrimaryKey(target);
            return target;
        }
        return null;
    }

    @Override
    public List<ProductCategory> getProductCategoryByType(List<Integer> categoryTypeList) throws Exception{


        ProductCategoryExample productCategoryExample = new ProductCategoryExample();
        ProductCategoryExample.Criteria criteria = productCategoryExample.createCriteria();

        criteria.andCategoryTypeIn(categoryTypeList);

        return productCategoryMapper.selectByExample(productCategoryExample);
    }
}
