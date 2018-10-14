package com.jun.service;

import com.jun.dto.OrderDto;

import java.util.List;

public interface OrderSerivce {

    public OrderDto createOrder(OrderDto orderDto);//新建订单

    public OrderDto selectOneOrder(String orderId);//查询单个订单

    public List<OrderDto> selectOrderList(String buyerOpenId);//查询订单列表

    public OrderDto cancel(OrderDto orderDto);//取消订单

    public OrderDto finish(OrderDto orderDto);//完结订单

    public OrderDto paid(OrderDto orderDto);//支付订单

    public List<OrderDto> getAllOrderList();



}
