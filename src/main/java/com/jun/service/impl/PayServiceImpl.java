package com.jun.service.impl;

import com.jun.dto.OrderDto;
import com.jun.service.PayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信支付";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Override
    public void create(OrderDto orderDto) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOperid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        bestPayService.pay(payRequest);
    }
}
