package com.emall.dao;

import com.emall.vo.OrderItemVo;
import com.emall.vo.CartProductVoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartDao {
    public List<OrderItemVo> findCart(int userId) throws Exception;
    public void deleteCart(int userId) throws Exception;
    List<CartProductVoList> selectCartAll(@Param("userId") Integer user_id);

    int addCart(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("count") Integer count);

    int updateCount(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("count") Integer count);

    int delCart(@Param("userId") Integer userId, @Param("productIds") Integer productIds[]);

    int checkCart(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int uncheckCart(@Param("userId") Integer userId, @Param("productId") Integer productId);

    int getCount(@Param("userId") Integer userId);

    int checkAll(@Param("userId") Integer userId);

    int uncheckAll(@Param("userId") Integer userId);
}