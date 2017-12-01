package com.emall.controller;

import com.emall.common.Const;
import com.emall.common.ServerResponse;
import com.emall.pojo.Shipping;
import com.emall.pojo.User;
import com.emall.service.ShippingService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/11/29.
 */
@Controller
@RequestMapping("shipping")
public class ShippingAction {
    @Autowired
    @Qualifier("shippingSer")
    private ShippingService shippingService;

    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    /**
     * record 收货地址对象
     */
    public ServerResponse<PageInfo<Shipping>> addShipping(HttpSession session,Shipping record){
//        User user=(User) session.getAttribute(Const.CURRENT_USER);
//        if (user==null){
//            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"请登录之后操作");
//            return serverResponse;
//        }
//        Integer userId=user.getId();
        System.out.println(record);
        return shippingService.insert(1,record);
    }

    @RequestMapping(value = "del.do",method = RequestMethod.POST)
    @ResponseBody
    /**
     * id 收货地址id
     */
    public ServerResponse<PageInfo<Shipping>> delShipping(HttpSession session,Integer id){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"请登录之后操作");
            return serverResponse;
        }
        Integer userId=user.getId();
        return shippingService.deleteByPrimaryKey(userId,id);
    }

    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    @ResponseBody
    /**
     * id 收货地址id
     */
    public ServerResponse<PageInfo<Shipping>> updateShipping(HttpSession session,Shipping record){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"请登录之后操作");
            return serverResponse;
        }
        Integer userId=user.getId();
        return shippingService.updateByPrimaryKey(userId,record);
    }

    @RequestMapping(value = "select.do",method = RequestMethod.POST)
    @ResponseBody
    /**
     * shippingId 收货地址id
     */
    public ServerResponse<Shipping> selectShipping(HttpSession session,Integer shippingId){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"请登录之后查询");
            return serverResponse;
        }
        return shippingService.selectByPrimaryKey(shippingId);
    }

    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    /**
     * @param usreId 用户id
     * @param pageNum 当前页码
     * @param pageSize  每页显示数量
     */
    public ServerResponse<PageInfo<Shipping>> shippinglist(HttpSession session, Integer uId, Integer pageNum, Integer pageSize){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"请登录之后查询");
            return serverResponse;
        }
        return shippingService.selectAll(uId,pageNum,pageSize);
    }
}
