package com.emall.controller;

import com.emall.common.Const;
import com.emall.common.ServerResponse;
import com.emall.pojo.User;
import com.emall.service.OrderService;
import com.emall.vo.OrderCartVo;
import com.emall.vo.OrderPageVo;
import com.emall.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Controller
@RequestMapping("order")
public class OrderAction {
    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param shippingId 收货地址id
     * @param session
     * @return
     */
    @RequestMapping(value = "create.do/shippingId/{shippingId}",method = RequestMethod.GET)
    public @ResponseBody ServerResponse<OrderVo> create(@PathVariable("shippingId") Integer shippingId, HttpSession session){
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        Integer userId=user.getId();
        ServerResponse<OrderVo> sr=orderService.createOrder(shippingId,userId);
        return sr;
    }

    /**
     * 获取订单商品信息
     * @param session
     * @return
     */
    @RequestMapping(value="get_order_cart_product.do",method = RequestMethod.GET)
    public @ResponseBody ServerResponse<OrderCartVo> getOrderCart(HttpSession session){
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        int userId=user.getId();
        ServerResponse<OrderCartVo> sr=orderService.getOrderCart(userId);
        return sr;
    }

    /**
     * 获取订单list
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param session
     * @return
     */
    @RequestMapping(value="list.do",method = RequestMethod.POST)
    public @ResponseBody ServerResponse<PageInfo<OrderPageVo>> getOrderList(Integer pageNum,Integer pageSize,HttpSession session){
    User user= (User) session.getAttribute(Const.CURRENT_USER);
        int userId=user.getId();
        ServerResponse<PageInfo<OrderPageVo>> sr=orderService.getOrderPage(userId,pageSize,pageNum);
       if(user==null){
          return ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
       }
        return sr;
    }

    /**
     * 查询订单详情
     * @param orderNo 订单号
     * @return
     */
    @RequestMapping(value="detail.do",method = RequestMethod.POST)
    public @ResponseBody ServerResponse<OrderPageVo> getOneOrder(Long orderNo){
        ServerResponse<OrderPageVo> sr=orderService.getOneOrder(orderNo);
        return sr;
    }

    /**
     *  取消订单
     * @param orderNo 订单号
     * @return
     */
    @RequestMapping(value="cancel.do",method = RequestMethod.POST)
    public @ResponseBody ServerResponse cancel( Long orderNo){
        ServerResponse sr=orderService.cancel(orderNo);
        return sr;
    }

    /**
     * 订单发货
     * @param orderNo 订单号
     * @return
     */
    @RequestMapping(value="send_goods.do",method = RequestMethod.GET)
    public @ResponseBody ServerResponse sendOrder(Long orderNo){
        return orderService.sendOrder(orderNo);
    }
}
