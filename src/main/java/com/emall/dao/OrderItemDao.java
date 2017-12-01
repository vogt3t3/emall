package com.emall.dao;

import com.emall.pojo.OrderItem;
import com.emall.vo.OrderItemVo;

import java.util.List;

public interface OrderItemDao {
     public void addOrderItem(OrderItem orderItem) throws Exception;
     public List<OrderItemVo> findAllOrderItemVo(int userId) throws Exception;
     public List<OrderItemVo> findOrderItemVo(Long orderNo) throws Exception;
}