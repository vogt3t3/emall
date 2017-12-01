package com.emall.dao;

import com.emall.pojo.Order;
import com.emall.vo.OrderCartVo;
import com.emall.vo.OrderItemVo;
import com.emall.vo.OrderPageVo;

import java.util.List;

public interface OrderDao {
    public void addOrder(Order order) throws Exception;
    public List<OrderPageVo> findOrder(int userId) throws Exception;
    public OrderPageVo findOneOrder(Long orderNo) throws Exception;
    public void updateOrder(Long orderNo) throws Exception;
    public int findStatus(Long orderNo) throws Exception;
    public void sendOrder(Long orderNo) throws Exception;
}