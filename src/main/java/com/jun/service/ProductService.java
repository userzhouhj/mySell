package com.jun.service;

import com.jun.domain.Product;
import com.jun.dto.CartDto;

import java.util.List;

public interface ProductService {

    public List<Product> selectAllProduct();

    public List<Product> selectAllUpProduct();//查询所有上架 的商品

    public Product selectProductById(String productId);

    public List<Product> selectProductByType(Integer type);

    public Product save(Product product);

    public Product update(Product product);

    public void deleteById(String id);

    public void decrecseStock(List<CartDto> cartDtoList);

    public void increaseStock(List<CartDto> cartDtoList);

    public Product onSell(String productId);//商品上架

    public Product offSell(String productId);//商品下架

}
