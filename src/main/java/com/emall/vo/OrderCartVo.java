package com.emall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/11/29 0029.
 */
public class OrderCartVo {
    private List<OrderItemVo> orderItemVoList;
    private String imageHost;
    private BigDecimal productTotalPrice;

    public OrderCartVo() {
    }

    public OrderCartVo(List<OrderItemVo> orderItemVoList, String imageHost, BigDecimal productTotalPrice) {
        this.orderItemVoList = orderItemVoList;
        this.imageHost = imageHost;
        this.productTotalPrice = productTotalPrice;
    }

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    @Override
    public String toString() {
        return "OrderCartVo{" +
                "orderItemVoList=" + orderItemVoList +
                ", imageHost='" + imageHost + '\'' +
                ", productTotalPrice=" + productTotalPrice +
                '}';
    }
}
