package com.emall.service.impl;

import com.emall.common.ResponseCode;
import com.emall.common.ServerResponse;
import com.emall.dao.*;
import com.emall.pojo.Order;
import com.emall.pojo.OrderItem;
import com.emall.service.OrderService;
import com.emall.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ShippingDao shippingDao;
    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse<OrderVo> createOrder(int shippingId, int userId) {
        List<OrderItemVo> list=null;

        try {
            list =cartDao.findCart(userId);
            Long orderNo=null;
            BigDecimal payment=null;
            Integer paymentType = 1;
            Integer postage = 0;
            Integer status = 10;
            Date paymentTime=null;
            Date sendTime= null;
            Date endTime= null;
            Date closeTime=null;
            for(OrderItemVo orderItemVo:list) {
                orderNo = orderItemVo.getOrderNo();
                payment = payment.add(orderItemVo.getTotalPrice());
                Map map=new HashMap();
                map.put("productId",orderItemVo.getProductId());
                map.put("count",orderItemVo.getQuantity());
                productDao.reduceStock(map);
                OrderItem orderItem=new OrderItem(userId,orderNo,orderItemVo.getProductId(),orderItemVo.getProductName(),orderItemVo.getProductImage(),orderItemVo.getCurrentUnitPrice(),orderItemVo.getQuantity(),orderItemVo.getTotalPrice());
                orderItemDao.addOrderItem(orderItem);
            }
            Order order=new Order(orderNo,userId,shippingId,payment,paymentType,postage,status,paymentTime,sendTime,endTime,closeTime);
            orderDao.addOrder(order);
            ShippingVo shippingVo=shippingDao.createShippingVo(shippingId);
            cartDao.deleteCart(userId);
            OrderVo orderVo=new OrderVo(orderNo,payment,paymentType,postage,status,paymentTime,sendTime,endTime,closeTime,new Date(),list,shippingId,shippingVo);
            return ServerResponse.getSuccessServerResponse(orderVo,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"创建订单失败");

    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public ServerResponse<OrderCartVo> getOrderCart(int userId) {
        List<OrderItemVo> orderItemVoList=null;
        BigDecimal productTotalPrice=null;
        try {
            orderItemVoList=orderItemDao.findAllOrderItemVo(userId);
            for(OrderItemVo orderItemVo:orderItemVoList){
            productTotalPrice=productTotalPrice.add(orderItemVo.getTotalPrice());
            }
            OrderCartVo orderCartVo=new OrderCartVo();
            orderCartVo.setImageHost("http://img.emall.com/");
            orderCartVo.setProductTotalPrice(productTotalPrice);
            orderCartVo.setOrderItemVoList(orderItemVoList);
            return ServerResponse.getSuccessServerResponse(orderCartVo,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"用户未登录");
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public ServerResponse<PageInfo<OrderPageVo>> getOrderPage(int userId, Integer pageSize,Integer pageNum) {
        List<OrderPageVo> orderPageVoList=null;
        try {
            if(pageNum==null&&pageSize!=null){
                PageHelper.offsetPage(0,pageSize);
                orderPageVoList=orderDao.findOrder(userId);
                for(OrderPageVo orderPageVo:orderPageVoList){
                    int status=orderPageVo.getStatus();
                    switch (status){
                        case 0:
                            orderPageVo.setStatusDesc("已取消");
                            break;
                        case 10:
                            orderPageVo.setStatusDesc("未付款");
                            break;
                        case 20:
                            orderPageVo.setStatusDesc("已付款");
                            break;
                        case 40:
                            orderPageVo.setStatusDesc("已发货");
                            break;
                        case 50:
                            orderPageVo.setStatusDesc("交易成功");
                            break;
                        case 60:
                            orderPageVo.setStatusDesc("交易失败");
                            break;
                    }
                    orderPageVo.setOrderItemVoList(orderItemDao.findOrderItemVo(orderPageVo.getOrderNo()));
                    orderPageVo.setReceiveName(shippingDao.createShippingVo(orderPageVo.getShippingId()).getReceiverName());
                    orderPageVo.setShippingVo(shippingDao.createShippingVo(orderPageVo.getShippingId()));

                }

                PageInfo<OrderPageVo> page=new PageInfo<OrderPageVo>(orderPageVoList);
                return ServerResponse.getSuccessServerResponse(page,null);
            }else if(pageSize==null&&pageNum!=null){
                PageHelper.offsetPage(pageNum-1,10);
                orderPageVoList=orderDao.findOrder(userId);
                for(OrderPageVo orderPageVo:orderPageVoList){
                    int status=orderPageVo.getStatus();
                    switch (status){
                        case 0:
                            orderPageVo.setStatusDesc("已取消");
                            break;
                        case 10:
                            orderPageVo.setStatusDesc("未付款");
                            break;
                        case 20:
                            orderPageVo.setStatusDesc("已付款");
                            break;
                        case 40:
                            orderPageVo.setStatusDesc("已发货");
                            break;
                        case 50:
                            orderPageVo.setStatusDesc("交易成功");
                            break;
                        case 60:
                            orderPageVo.setStatusDesc("交易失败");
                            break;
                    }
                    orderPageVo.setOrderItemVoList(orderItemDao.findOrderItemVo(orderPageVo.getOrderNo()));
                    orderPageVo.setReceiveName(shippingDao.createShippingVo(orderPageVo.getShippingId()).getReceiverName());
                    orderPageVo.setShippingVo(shippingDao.createShippingVo(orderPageVo.getShippingId()));

                }

                PageInfo<OrderPageVo> page=new PageInfo<OrderPageVo>(orderPageVoList);
                return ServerResponse.getSuccessServerResponse(page,null);
            }
            PageHelper.offsetPage(pageNum-1,pageSize);
            orderPageVoList=orderDao.findOrder(userId);
            for(OrderPageVo orderPageVo:orderPageVoList){
                int status=orderPageVo.getStatus();
                switch (status){
                    case 0:
                        orderPageVo.setStatusDesc("已取消");
                        break;
                    case 10:
                        orderPageVo.setStatusDesc("未付款");
                        break;
                    case 20:
                        orderPageVo.setStatusDesc("已付款");
                        break;
                    case 40:
                        orderPageVo.setStatusDesc("已发货");
                        break;
                    case 50:
                        orderPageVo.setStatusDesc("交易成功");
                        break;
                    case 60:
                        orderPageVo.setStatusDesc("交易失败");
                        break;
                }
                orderPageVo.setOrderItemVoList(orderItemDao.findOrderItemVo(orderPageVo.getOrderNo()));
                orderPageVo.setReceiveName(shippingDao.createShippingVo(orderPageVo.getShippingId()).getReceiverName());
                orderPageVo.setShippingVo(shippingDao.createShippingVo(orderPageVo.getShippingId()));

            }
            PageInfo<OrderPageVo> page=new PageInfo<OrderPageVo>(orderPageVoList);
            return ServerResponse.getSuccessServerResponse(page,null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"没有权限");
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public ServerResponse<OrderPageVo> getOneOrder(Long orderNo) {
        OrderPageVo orderPageVo=null;
        try {
            orderPageVo=orderDao.findOneOrder(orderNo);
            int status=orderPageVo.getStatus();
            switch (status){
                case 0:
                    orderPageVo.setStatusDesc("已取消");
                    break;
                case 10:
                    orderPageVo.setStatusDesc("未付款");
                    break;
                case 20:
                    orderPageVo.setStatusDesc("已付款");
                    break;
                case 40:
                    orderPageVo.setStatusDesc("已发货");
                    break;
                case 50:
                    orderPageVo.setStatusDesc("交易成功");
                    break;
                case 60:
                    orderPageVo.setStatusDesc("交易失败");
                    break;
            }
            orderPageVo.setOrderItemVoList(orderItemDao.findOrderItemVo(orderPageVo.getOrderNo()));
            orderPageVo.setReceiveName(shippingDao.createShippingVo(orderPageVo.getShippingId()).getReceiverName());
            orderPageVo.setShippingVo(shippingDao.createShippingVo(orderPageVo.getShippingId()));
        return ServerResponse.getSuccessServerResponse(orderPageVo,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"没有找到订单");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse cancel(Long orderNo) {
        try {
            int status=orderDao.findStatus(orderNo);
            if(status>=20){
                return ServerResponse.getFailServerResponse(null,"此订单已付款，无法被取消");
            }
            orderDao.updateOrder(orderNo);
            List<OrderItemVo> orderItemVoList=orderItemDao.findOrderItemVo(orderNo);
            for(OrderItemVo orderItemVo:orderItemVoList){
              int productId=orderItemVo.getProductId();
              int quantity=orderItemVo.getQuantity();
                Map map=new HashMap();
                map.put("productId",productId);
                map.put("count",quantity);
                productDao.addStock(map);
            }
            return ServerResponse.getSuccessServerResponse(null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"该用户没有此订单");
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse sendOrder(Long orderNo){
        try {
            if(orderNo==20) {


                orderDao.sendOrder(orderNo);
                return ServerResponse.getSuccessServerResponse(null,"发货成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"发货失败");
    }
}
