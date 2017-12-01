package com.emall.service.impl;

import com.emall.common.ServerResponse;
import com.emall.dao.CartDao;
import com.emall.service.CartService;
import com.emall.vo.CartProductVoList;
import com.emall.vo.ResponseCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
@Service("cartSer")
public class CartServiceimpl implements CartService{
    @Autowired
    private CartDao cartDao;

    /**
     *查询该用户购物车中所有商品
     * @param user_id
     * @return ServerResponse<ResponseCartVo>
     */
    @Override
    @Transactional
    public ServerResponse<ResponseCartVo> selectCartAll(Integer user_id) {
        BigDecimal cartTotalPrice=new BigDecimal(0);
        List<CartProductVoList> list=cartDao.selectCartAll(user_id);
        boolean allChecked=true;
        for (CartProductVoList a:list){
            if(a.getQuantity()>a.getProductStock()){
                a.setLimitQuantity("LIMIT_NUM_FAIL");
            }else{
                a.setLimitQuantity("LIMIT_NUM_SUCCESS");
            }

            if(a.getProductChecked()==1){
                cartTotalPrice=cartTotalPrice.add(a.getProductTotalPrice());
            }
            if(allChecked){
                if(a.getProductChecked()==0){
                    allChecked=false;
                }
            }
        }
        ResponseCartVo responseCart=new ResponseCartVo();
        responseCart.setCartProductVoList(list);
        responseCart.setCartTotalPrice(cartTotalPrice);
        responseCart.setAllChecked(allChecked);
        ServerResponse<ResponseCartVo> serverResponse=ServerResponse.getSuccessServerResponse(responseCart,null);
        return serverResponse;
    }

    /**
     * 添加商品到购物车中
     * @param userId
     * @param productId
     * @param count
     * @return ServerResponse<ResponseCartVo>
     */
    @Override
    @Transactional
    public ServerResponse<ResponseCartVo> addCart(Integer userId, Integer productId, Integer count) {
        int num=0;
        boolean flg=true;
        List<CartProductVoList> erc= cartDao.selectCartAll(userId);
        for (CartProductVoList s:erc){
            if(s.getProductId()==productId){
                count=count+s.getQuantity();
                num=cartDao.updateCount(userId,productId,count);
                if(num<0){
                    ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"出现异常");
                    return serverResponse;
                }
                flg=false;
                break;
            }
        }
        if (flg){
            num= cartDao.addCart(userId,productId,count);
        }
        if(num<0){//添加失败择给出异常信息
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"出现异常");
            return serverResponse;
        }
        return selectCartAll(userId);
    }

    /**
     *修改商品数量
     * @param userId
     * @param productId
     * @param count
     * @return ServerResponse<ResponseCartVo>
     */
    @Override
    @Transactional
    public ServerResponse<ResponseCartVo> updateCount(Integer userId, Integer productId, Integer count) {
        int num=cartDao.updateCount( userId,  productId,  count);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"出现异常");
            return serverResponse;
        }
        return selectCartAll(userId);
    }

    /**
     *从购物车中删除商品
     * @param userId
     * @param productIds
     * @return ServerResponse<ResponseCartVo>
     */
    @Override
    @Transactional
    public ServerResponse<ResponseCartVo> delCart(Integer userId,Integer productIds[]) {
        int num=cartDao.delCart(userId,productIds);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"出现异常");
            return serverResponse;
        }
        return selectCartAll(userId);
    }

    /**
     * 选中购物车中商品
     * @param userId
     * @param productId
     * @return ServerResponse<ResponseCartVo>
     */
    @Override
    @Transactional
    public ServerResponse<ResponseCartVo> checkCart(Integer userId, Integer productId) {
        int num=cartDao.checkCart(userId,productId);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"出现异常");
            return serverResponse;
        }
        return selectCartAll(userId);
    }

    /**
     * 取消选中
     * @param userId
     * @param productId
     * @return ServerResponse<ResponseCartVo>
     */
    @Override
    @Transactional
    public ServerResponse<ResponseCartVo> uncheckCart(Integer userId, Integer productId) {
        int num=cartDao.uncheckCart(userId,productId);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"出现异常");
            return serverResponse;
        }
        return selectCartAll(userId);
    }

    /**
     * 获取购物车中商品数量
     * @param userId
     * @return ServerResponse<ResponseCartVo>
     */
    @Override
    @Transactional
    public ServerResponse getCount(Integer userId) {
        int num=cartDao.getCount(userId);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"出现异常");
            return serverResponse;
        }
        ServerResponse serverResponse=ServerResponse.getFailServerResponse(2,null);
        return serverResponse;
    }

    /**
     * 选中购物车中所有商品
     * @param userId
     * @return ServerResponse<ResponseCartVo>
     */
    @Override
    @Transactional
    public ServerResponse<ResponseCartVo> checkAll(Integer userId) {
        int num=cartDao.checkAll(userId);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"出现异常");
            return serverResponse;
        }
        return selectCartAll(userId);
    }

    /**
     * 取消全选
     * @param userId
     * @return ServerResponse<ResponseCartVo>
     */
    @Override
    @Transactional
    public ServerResponse<ResponseCartVo> uncheckAll(Integer userId) {
        int num=cartDao.uncheckAll(userId);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"出现异常");
            return serverResponse;
        }
        return selectCartAll(userId);
    }
}
