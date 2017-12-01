package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.vo.ResponseCartVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
public interface CartService {
    /**
     * 查询用户购物车中所有商品
     * @param user_id 用户id
     * @return ServerResponse<ResponseCartVo>
     */
    ServerResponse<ResponseCartVo> selectCartAll(Integer user_id);

    /**
     * 往购物车中添加商品
     * @param userId 用户id
     * @param productId 商品id
     * @param count 商品数量
     * @return ServerResponse<ResponseCartVo>
     */
    ServerResponse<ResponseCartVo> addCart(Integer userId, Integer productId, Integer count);

    /**
     * 更新购物车中商品数量
     * @param userId 用户id
     * @param productId 商品id
     * @param count 商品数量
     * @return ServerResponse<ResponseCartVo>
     */
    ServerResponse<ResponseCartVo> updateCount(Integer userId, Integer productId, Integer count);

    /**
     * 从购物车中删除商品，可批量删除
     * @param userId 用户id
     * @param productIds 商品id
     * @return ServerResponse<ResponseCartVo>
     */
    ServerResponse<ResponseCartVo> delCart(Integer userId, Integer productIds[]);

    /**
     * 选中购物车中的商品
     * @param userId  用户id
     * @param productId 商品id
     * @return ServerResponse<ResponseCartVo>
     */
    ServerResponse<ResponseCartVo> checkCart(Integer userId, Integer productId);

    /**
     * 取消选中商品
     * @param userId 用户id
     * @param productId 商品id
     * @return ServerResponse<ResponseCartVo>
     */
    ServerResponse<ResponseCartVo> uncheckCart(Integer userId, Integer productId);

    /**
     * 获取购物车中商品总数
     * @param userId  用户id
     * @return ServerResponse<ResponseCartVo>
     */
    ServerResponse getCount(Integer userId);

    /**
     * 选中购物车中所有商品
     * @param userId 用户id
     * @return int
     */
    ServerResponse<ResponseCartVo> checkAll(Integer userId);

    /**
     * 取消选中购物车中所有商品
     * @param userId 用户id
     * @return int
     */
    ServerResponse<ResponseCartVo> uncheckAll(Integer userId);
}
