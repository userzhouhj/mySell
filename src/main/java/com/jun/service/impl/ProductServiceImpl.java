package com.jun.service.impl;

import com.jun.dao.ProductMapper;
import com.jun.domain.Product;
import com.jun.domain.ProductExample;
import com.jun.dto.CartDto;
import com.jun.enums.ProductStatusEnum;
import com.jun.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public List<Product> selectAllProduct() {
        return productMapper.selectByExample(null);
    }

    @Override
    public Product selectProductById(String productId) {
        return productMapper.selectByPrimaryKey(productId);
    }

    @Override
    public List<Product> selectAllUpProduct() {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductStatusEqualTo(ProductStatusEnum.ON.getCode());
        return productMapper.selectByExample(productExample);
    }

    @Override
    public List<Product> selectProductByType(Integer type) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andCategoryTypeEqualTo(type);
        return productMapper.selectByExample(productExample);
    }

    @Override
    public Product save(Product product) {

        if(product != null){
            product.setCreateTime(new Date());
            product.setUpdateTime(new Date());
            productMapper.insert(product);
        }else{
            return null;
        }
        return product;
    }

    @Override
    public Product update(Product product) {

        if(product.getProductId() == null){
            return  null;
        }
        product.setUpdateTime(new Date());
        productMapper.updateByPrimaryKey(product);

        return product;
    }

    @Override
    public void deleteById(String id) {
        if(productMapper.selectByPrimaryKey(id) == null){
            throw new RuntimeException("商品不存在");
        }else{
            productMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    @Transactional
    public void decrecseStock(List<CartDto> cartDtoList) {
        for(CartDto cartDto : cartDtoList){
            Product product = productMapper.selectByPrimaryKey(cartDto.getProductId());
            if(product == null){
                throw new RuntimeException("商品不存在");
            }
            if(product.getProductStock() >= cartDto.getProductNum()){
                product.setProductStock(product.getProductStock() - cartDto.getProductNum());
            }else{
                throw new RuntimeException("库存不足！！！");
            }
            product.setUpdateTime(new Date());
            productMapper.updateByPrimaryKey(product);
        }
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for(CartDto cartDto : cartDtoList){
            Product product = productMapper.selectByPrimaryKey(cartDto.getProductId());
            if(product == null){
                throw new RuntimeException("商品不存在");
            }
            int stock = cartDto.getProductNum() + product.getProductStock();
            product.setProductStock(stock);
            product.setUpdateTime(new Date());
            productMapper.updateByPrimaryKey(product);
        }
    }

    @Override
    public Product onSell(String productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            throw new RuntimeException("商品不存在");
        }
        if(product.getProductStatus() == ProductStatusEnum.ON.getCode()){
            throw new RuntimeException("商品已经上架");
        }else{
            product.setProductStatus(ProductStatusEnum.ON.getCode());
            product.setUpdateTime(new Date());
            productMapper.updateByPrimaryKey(product);
        }
        return product;
    }

    @Override
    public Product offSell(String productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            throw new RuntimeException("商品不存在");
        }
        if(product.getProductStatus() == ProductStatusEnum.OFF.getCode()){
            throw new RuntimeException("商品已经下架");
        }else{
            product.setProductStatus(ProductStatusEnum.OFF.getCode());
            product.setUpdateTime(new Date());
            productMapper.updateByPrimaryKey(product);
        }
        return product;
    }
}
