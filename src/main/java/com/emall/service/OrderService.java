package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.pojo.Order;
import com.emall.vo.OrderCartVo;
import com.emall.vo.OrderPageVo;
import com.emall.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.spi.activation.Server;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
public interface OrderService {
    /**
     *创建订单
     * @param shippingId 收货地址id
     * @param userId 用户id
     * @return
     */
    public ServerResponse<OrderVo> createOrder(int shippingId,int userId);

    /**
     * 获取订单商品信息
     * @param userId 用户id
     * @return
     */
    public ServerResponse<OrderCartVo> getOrderCart(int userId);

    /**
     * 返回订单的页面对象
     * @param userId 用户
     * @param pageSize 页面大小
     * @param pageNum 页码
     * @return
     */
    public ServerResponse<PageInfo<OrderPageVo>> getOrderPage(int userId, Integer pageSize, Integer pageNum);

    /**
     * 获取一个订单的详情
     * @param orderNo 订单号
     * @return
     */
    public ServerResponse<OrderPageVo> getOneOrder(Long orderNo);

    /**
     * 取消订单
     * @param orderNo 订单号
     * @return
     */
    public ServerResponse cancel(Long orderNo);

    /**
     * 订单发货
     * @param orderNo 订单号
     * @return
     */
    public ServerResponse sendOrder(Long orderNo);
}
