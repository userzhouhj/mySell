package com.jun.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.domain.OrderDetail;
import com.jun.dto.CartDto;
import com.jun.dto.OrderDto;
import com.jun.form.OrderForm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderFormToOrderUtils {
    public static OrderDto getConvertor(OrderForm orderForm) throws Exception{
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOperid(orderForm.getOpenid());
        orderDto.setBuyerPhone(orderForm.getPhone());

        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, CartDto.class);

        List<CartDto> cartDtoList = objectMapper.readValue(orderForm.getItems(),javaType);
        List<OrderDetail> orderDetailList = cartDtoList.stream()
                .map(e -> {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setProductId(e.getProductId());
                    orderDetail.setProductNum(e.getProductNum());
                    return orderDetail;
                })
                .collect(Collectors.toList());

        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }
}
