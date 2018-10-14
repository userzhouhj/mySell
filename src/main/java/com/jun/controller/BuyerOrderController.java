package com.jun.controller;


import com.jun.dto.OrderDto;
import com.jun.form.OrderForm;
import com.jun.service.OrderSerivce;
import com.jun.utils.OrderFormToOrderUtils;
import com.jun.utils.ResultDataUtil;
import com.jun.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderSerivce orderSerivce;

    /**
     * 创建订单
     * @param orderForm 表单的实体类
     * @param bindingResult 表单验证返回的结果
     * @return
     */
    @PostMapping("/create")
    public ResultData create(@Valid OrderForm orderForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){//验证有误
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        try{
            OrderDto orderDto = OrderFormToOrderUtils.getConvertor(orderForm);//对象转换

            OrderDto result = orderSerivce.createOrder(orderDto);
            Map<String,String> map = new HashMap<>();
            map.put("orderId",result.getOrderId());
            return ResultDataUtil.success(map);
        }catch(Exception e){
            return ResultDataUtil.fail();
        }
    }


    /**
     * 获取订单列表
     * @return
     */
    @GetMapping("/list")
    public ResultData list(@RequestParam("openid") String openId){
        if(openId.equals("") || openId == null){
            throw new RuntimeException("openid为空");
        }
        try{
            List<OrderDto> orderDtoList = orderSerivce.selectOrderList(openId);
            return ResultDataUtil.success(orderDtoList);
        }catch(Exception e){
            return ResultDataUtil.fail();
        }
    }

    /**
     * 查询用户的某个订单详情
     * @param openId
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultData detail(@RequestParam("openid") String openId,
                             @RequestParam("orderId") String orderId){
        if(openId.equals("") || openId == null){
            throw new RuntimeException("openid为空");
        }

        try{
            OrderDto orderDto = orderSerivce.selectOneOrder(orderId);
            if(orderDto == null){
                throw new RuntimeException("订单不存在");
            }
            if(!orderDto.getBuyerOperid().equalsIgnoreCase(openId)){
                throw new RuntimeException("买家id不一致");
            }
            return ResultDataUtil.success(orderDto);
        }catch(Exception e){
            return ResultDataUtil.fail();
        }
    }

    @PostMapping("/cancel")
    public ResultData cancel(@RequestParam("openid") String openId,
                             @RequestParam("orderId") String orderId){
        if(openId.equals("") || openId == null){
            throw new RuntimeException("openid为空");
        }
        try{
            OrderDto orderDto = orderSerivce.selectOneOrder(orderId);
            if(orderDto == null){
                throw new RuntimeException("订单不存在");
            }
            if(!orderDto.getBuyerOperid().equalsIgnoreCase(openId)){
                throw new RuntimeException("买家id不一致");
            }
            orderSerivce.cancel(orderDto);
            return ResultDataUtil.success(null);
        }catch(Exception e){
            return ResultDataUtil.fail();
        }
    }

}
