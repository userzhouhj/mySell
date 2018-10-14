package com.jun.service.impl;

import com.jun.dao.OrderDetailMapper;
import com.jun.dao.OrderMapper;
import com.jun.dao.ProductMapper;
import com.jun.domain.*;
import com.jun.dto.CartDto;
import com.jun.dto.OrderDto;
import com.jun.enums.OrderStatusEnum;
import com.jun.enums.PayStatusEnum;
import com.jun.service.OrderSerivce;
import com.jun.service.ProductService;
import com.jun.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderSerivce {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;



    @Override
    @Transactional
    @CachePut(cacheNames = "order",key = "'order_'+#result.orderId")
    public OrderDto createOrder(OrderDto orderDto) {

        BigDecimal orderAmount = new BigDecimal(0);
        String orderId = KeyUtil.getKey();
        //1.查询商品
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            Product product = productMapper.selectByPrimaryKey(orderDetail.getProductId());
            if(product == null){
                throw new RuntimeException("商品不存在");
            }
            //2.计算金额
            orderAmount = product.getProductPrice().multiply(new BigDecimal(orderDetail.getProductNum())).add(orderAmount);

            //3.1订单详情入库
            orderDetail.setDetailId(KeyUtil.getKey());
            orderDetail.setOrderId(orderId);
            orderDetail.setCreateTime(new Date());
            orderDetail.setUpdateTime(new Date());
            BeanUtils.copyProperties(product,orderDetail);
        }

        //3.2 订单写入数据库
        Order order = new Order();
        BeanUtils.copyProperties(orderDto,order);

        order.setOrderId(orderId);
        order.setOrderAmount(orderAmount);

        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        orderMapper.insert(order);

        //4.减少库存
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(),e.getProductNum())).collect(Collectors.toList());

        productService.decrecseStock(cartDtoList);

        return orderDto;
    }

    /* 查询单个订单*/
    @Override
    @Cacheable(cacheNames = "order",key = "'order_'+#orderId")
    public OrderDto selectOneOrder(String orderId) {
        OrderDto orderDto = new OrderDto();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order != null){
            BeanUtils.copyProperties(order,orderDto);
        }

        OrderDetailExample orderDetailExample = new OrderDetailExample();
        OrderDetailExample.Criteria criteria = orderDetailExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectByExample(orderDetailExample);
        orderDto.setOrderDetailList(orderDetails);
        return orderDto;
    }

    /*查询指定用户的所有订单*/
    @Override

    public List<OrderDto> selectOrderList(String buyerOpenId) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andBuyerOperidEqualTo(buyerOpenId);
        List<Order> orderList = orderMapper.selectByExample(orderExample);//查询指定用户的所有订单列表

        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Order order : orderList){
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order,orderDto);//创建orderdto并及进行拷贝值
            OrderDetailExample orderDetailExample = new OrderDetailExample();
            OrderDetailExample.Criteria criteria1 = orderDetailExample.createCriteria();
            criteria1.andOrderIdEqualTo(order.getOrderId());
            List<OrderDetail> orderDetailList = orderDetailMapper.selectByExample(orderDetailExample);//获得指定点单编号的订单详情列表
            orderDto.setOrderDetailList(orderDetailList);//属性赋值
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    /*取消订单*/
    @Override
    @CachePut(cacheNames = "order",key = "'order_'+#result.orderId")
    public OrderDto cancel(OrderDto orderDto) {

        Order order = new Order();
        //1.判断订单状态
        Integer status = orderDto.getOrderStatus();
        if(status != 0){
            throw new RuntimeException("订单已完结或取消！");
        }
        //2.修改订单，进行取消操作
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto,order);
        int effectNum = orderMapper.updateByPrimaryKey(order);
        if(effectNum <= 0){
            throw new RuntimeException("订单更新失败！");
        }
        //3.返还库存
        List<CartDto> cartDtoList = orderDto.getOrderDetailList()
                .stream()
                .map(e -> new CartDto(e.getProductId(),e.getProductNum()))
                .collect(Collectors.toList());

        productService.increaseStock(cartDtoList);
        //4.退款
        //TODO
        return orderDto;
    }

    /*完结订单*/
    @Override
    @CachePut(cacheNames = "order",key = "'order_'+#result.orderId")
    public OrderDto finish(OrderDto orderDto) {
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){//判断状态
            throw new RuntimeException("订单状态异常");
        }
        //修改状态
        Order order = new Order();
        orderDto.setOrderStatus(OrderStatusEnum.OK.getCode());
        BeanUtils.copyProperties(orderDto,order);
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKey(order);
        return orderDto;
    }

    /*支付订单*/
    @Override
    @CachePut(cacheNames = "order",key = "'order_'+#result.orderId")
    public OrderDto paid(OrderDto orderDto) {
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){//判断状态
            throw new RuntimeException("订单状态异常,订单完结或取消");
        }
        if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            throw new RuntimeException("订单支付成功或支付异常");
        }
        Order order = new Order();
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDto,order);
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKey(order);
        return orderDto;
    }

    /*店家查询所有订单*/
    @Override
    @Cacheable(cacheNames = "order",key = "'allOrder'")
    public List<OrderDto> getAllOrderList() {
        List<OrderDto> orderDtoList = new ArrayList<>();
        OrderExample orderExample = new OrderExample();
        List<Order> orderList = orderMapper.selectByExample(orderExample);
        for(Order order : orderList){
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order,orderDto);
            /*不进行详情赋值*/
            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }
}
