package com.jun.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jun.domain.OrderDetail;
import com.jun.enums.OrderStatusEnum;
import com.jun.enums.PayStatusEnum;
import com.jun.utils.EnumUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto implements Serializable {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOperid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    //不进行json序列化
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(this.orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore
    public  PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(this.payStatus,PayStatusEnum.class);
    }

}
