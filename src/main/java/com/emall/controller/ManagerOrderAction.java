package com.emall.controller;

import com.emall.common.Const;
import com.emall.common.ServerResponse;
import com.emall.pojo.Order;
import com.emall.pojo.User;
import com.emall.service.OrderService;
import com.emall.vo.OrderPageVo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/11/29 0029.
 */
@Controller
@RequestMapping("/manage/order")
public class ManagerOrderAction {
    @Autowired
    private OrderService orderService;

    /**
     * 后台查订单list
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param session
     * @return
     */
    @RequestMapping(value="list.do",method= RequestMethod.POST)
    public @ResponseBody ServerResponse<PageInfo<OrderPageVo>> findMOrderPage(Integer pageNum,Integer pageSize,HttpSession session){
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        int userId=user.getId();
        ServerResponse<PageInfo<OrderPageVo>> sr=orderService.getOrderPage(userId,pageSize,pageNum);
        if(user==null){
            return ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
        }
        return sr;
    }

    /**
     * 后台订单号查询
     * @param orderNo 订单号
     * @return
     */
    @RequestMapping(value="search.do",method = RequestMethod.POST)
    public @ResponseBody ServerResponse<OrderPageVo> findMOneOrder(Long orderNo){
        ServerResponse sr=orderService.getOneOrder(orderNo);
        return sr;
    }

    /**
     * 后台查询订单详情
     * @param orderNo
     * @return
     */
    @RequestMapping(value="detail.do",method = RequestMethod.POST)
    public @ResponseBody ServerResponse<OrderPageVo> findOrderDetail(Long orderNo){
        ServerResponse sr=orderService.getOneOrder(orderNo);
        return sr;
    }
}
