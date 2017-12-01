package com.emall.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/29 0029.
 */
public class OrderPageVo {
    private Long orderNo;
    private BigDecimal payment;
    private Integer paymentType;
    private final String paymentTypeDesc="在线支付";
    private Integer postage;
    private Integer status;
    private String statusDesc;
    private Date paymentTime;
    private Date sendTime;
    private Date endTime;
    private Date closeTime;
    private Date createTime;
    private List<OrderItemVo> orderItemVoList;
    private final String imageHost="http://img.emall.com/";
    private Integer shippingId;
    private String receiveName;
    private ShippingVo shippingVo;

    public OrderPageVo() {
    }

    public OrderPageVo(Long orderNo, BigDecimal payment, Integer paymentType, Integer postage, Integer status, String statusDesc, Date paymentTime, Date sendTime, Date endTime, Date closeTime, Date createTime, List<OrderItemVo> orderItemVoList,  Integer shippingId, String receiveName, ShippingVo shippingVo) {
        this.orderNo = orderNo;
        this.payment = payment;
        this.paymentType = paymentType;
        this.postage = postage;
        this.status = status;
        this.statusDesc = statusDesc;
        this.paymentTime = paymentTime;
        this.sendTime = sendTime;
        this.endTime = endTime;
        this.closeTime = closeTime;
        this.createTime = createTime;
        this.orderItemVoList = orderItemVoList;

        this.shippingId = shippingId;
        this.receiveName = receiveName;
        this.shippingVo = shippingVo;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeDesc() {
        return paymentTypeDesc;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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



    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public ShippingVo getShippingVo() {
        return shippingVo;
    }

    public void setShippingVo(ShippingVo shippingVo) {
        this.shippingVo = shippingVo;
    }

    @Override
    public String toString() {
        return "OrderPageVo{" +
                "orderNo=" + orderNo +
                ", payment=" + payment +
                ", paymentType=" + paymentType +
                ", paymentTypeDesc='" + paymentTypeDesc + '\'' +
                ", postage=" + postage +
                ", status=" + status +
                ", statusDesc='" + statusDesc + '\'' +
                ", paymentTime=" + paymentTime +
                ", sendTime=" + sendTime +
                ", endTime=" + endTime +
                ", closeTime=" + closeTime +
                ", createTime=" + createTime +
                ", orderItemVoList=" + orderItemVoList +
                ", imageHost='" + imageHost + '\'' +
                ", shippingId=" + shippingId +
                ", receiveName='" + receiveName + '\'' +
                ", shippingVo=" + shippingVo +
                '}';
    }
}
