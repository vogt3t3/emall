package com.emall.dao;

import com.emall.pojo.Shipping;
import com.emall.vo.ShippingVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingDao {
    public ShippingVo createShippingVo(int shippingId);
    int insert(Shipping shipping);

    int deleteByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKey(@Param("record") Shipping record);

    Shipping selectByPrimaryKey(@Param("id") Integer id);

    List<Shipping> selectAll(@Param("userId") Integer userId);
}