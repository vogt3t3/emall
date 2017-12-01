package com.emall.controller;

import com.emall.common.Const;
import com.emall.common.ResponseCode;
import com.emall.common.ServerResponse;
import com.emall.pojo.User;
import com.emall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
@Controller()
@Scope("prototype")
@RequestMapping("cart")
public class CartAction {
    @Autowired
    @Qualifier("cartSer")
    private CartService cartService;
    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectCartAll(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){//判断是否处于登录状态
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
            return serverResponse;
        }
        Integer userId=user.getId();
        //处于登录状态后执行查询操作
        ServerResponse sr=cartService.selectCartAll(userId);
        return sr;
    }

    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    /**
     * productId 商品id
      * count  商品数量
     */
    public ServerResponse addCart(HttpSession session,Integer productId,Integer count){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){//判断是否处于登录状态
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
            return serverResponse;
        }
        Integer userId=user.getId();
        //处于登录状态执行添加操作
        return cartService.addCart(userId,productId,count);
    }

    @RequestMapping(value = "update.do" ,method = RequestMethod.POST)
    @ResponseBody
    /**
     * productId 商品id
     * count  商品数量
     */
    public ServerResponse updateCount(HttpSession session,Integer productId,Integer count){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){//判断是否处于登录状态
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
            return serverResponse;
        }
        Integer userId=user.getId();
        return cartService.updateCount(userId,productId,count);
    }

    @RequestMapping(value = "delete_product.do" ,method = RequestMethod.POST)
    @ResponseBody
    /**
     * productIds 商品id 集合
     */
    public ServerResponse delCart(HttpSession session,Integer productIds[]){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){//判断是否处于登录状态
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
            return serverResponse;
        }
        Integer userId=user.getId();
        return cartService.delCart(userId,productIds);
    }

    @RequestMapping(value = "select.do" ,method = RequestMethod.POST)
    @ResponseBody
    /**
     * productId 商品id
     */
    public ServerResponse checkCart(HttpSession session,Integer productId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){//判断是否处于登录状态
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
            return serverResponse;
        }
        Integer userId=user.getId();
        return cartService.checkCart(userId,productId);
    }

    @RequestMapping(value = "un_select.do" ,method = RequestMethod.POST)
    @ResponseBody
    /**
     * productId 商品id
     */
    public ServerResponse uncheckCart(HttpSession session,Integer productId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){//判断是否处于登录状态
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
            return serverResponse;
        }
        Integer userId=user.getId();
        return cartService.uncheckCart(userId,productId);
    }

    @RequestMapping(value = "get_cart_product_count.do" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCount(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){//判断是否处于登录状态
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
            return serverResponse;
        }
        Integer userId=user.getId();
        return cartService.getCount(userId);
    }

    @RequestMapping(value = "select_all.do" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse checkAll(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){//判断是否处于登录状态
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
            return serverResponse;
        }
        Integer userId=user.getId();
        return cartService.checkAll(userId);
    }

    @RequestMapping(value = "un_select_all.do" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uncheckAll(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if (user==null){//判断是否处于登录状态
            ServerResponse serverResponse=ServerResponse.getNoLoginServerResponse(null,"用户未登录，请登录");
            return serverResponse;
        }
        Integer userId=user.getId();
        return cartService.uncheckAll(userId);
    }
}
