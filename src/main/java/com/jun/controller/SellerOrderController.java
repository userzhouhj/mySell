package com.jun.controller;


import com.github.pagehelper.PageInfo;
import com.jun.domain.OrderDetail;
import com.jun.dto.OrderDto;
import com.jun.service.OrderSerivce;
import com.jun.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderSerivce orderSerivce;

    /**
     * 店家查询所有订单列表的方法
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "5") Integer size){
        ModelAndView model = new ModelAndView();
        List<OrderDto> orderDtoList = orderSerivce.getAllOrderList();

        int total = PageUtil.getTotal(orderDtoList,size);//总页数

        orderDtoList = PageUtil.getList(orderDtoList,page,size);

        PageInfo pageInfo = PageUtil.getPageInfo(orderDtoList,total,page,size);
        model.addObject("orderList",orderDtoList);
        model.addObject("currentPage",page);
        model.addObject("size",size);
        model.addObject("pageInfo",pageInfo);
        model.setViewName("order/list");
        return model;
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("/orderId") String orderId){
        ModelAndView model = new ModelAndView();

        OrderDto orderDto = orderSerivce.selectOneOrder(orderId);
        if(orderDto == null){
            return new ModelAndView("common/error");
        }
        OrderDto resultOrderDto = orderSerivce.cancel(orderDto);
        model.setViewName("common/success");

        return model;
    }

    /**
     * 查询指定订单的订单明细
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detali(@RequestParam("orderId") String orderId){
        ModelAndView model = new ModelAndView();

        OrderDto orderDto = orderSerivce.selectOneOrder(orderId);

        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderDto.getOrderId());
        map.put("orderAmount",orderDto.getOrderAmount());
        map.put("orderDetailList",orderDetailList);
        model.addObject("orderDTO",map);
        model.setViewName("order/detail");

        return model;

    }

    /**
     * 订单完结
     * @param orderId
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId){
        ModelAndView model = new ModelAndView();
        OrderDto orderDto = orderSerivce.selectOneOrder(orderId);
        if(orderDto == null){
            return new ModelAndView("common/error");
        }
        orderSerivce.finish(orderDto);
        model.setViewName("/sell/seller/order/list");
        return model;
    }
}
