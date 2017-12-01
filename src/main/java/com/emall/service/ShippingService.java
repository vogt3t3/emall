package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.pojo.Shipping;
import com.github.pagehelper.PageInfo;

/**
 * Created by Administrator on 2017/11/28.
 */
public interface ShippingService {
    /**
     * 新增收货地址
     * @param record 收货地址对象
     * @return  ServerResponse<PageInfo<Shipping>>
     */
    ServerResponse<PageInfo<Shipping>> insert(Integer userId, Shipping record);

    /**
     * 删除收货地址
     * @param id 收货地址id
     * @return  ServerResponse<PageInfo<Shipping>>
     */
    ServerResponse<PageInfo<Shipping>> deleteByPrimaryKey(Integer userId, Integer id);

    /**
     * 更新收货地址
     * @param record 收货地址对象
     * @return  ServerResponse<PageInfo<Shipping>>
     */
    ServerResponse<PageInfo<Shipping>> updateByPrimaryKey(Integer userId, Shipping record);

    /**
     * 查询收货地址详细信息
     * @param id 收货地址id
     * @return ServerResponse<Shipping>
     */
    ServerResponse<Shipping> selectByPrimaryKey(Integer id);

    /**
     * 查询该用户所有收货地址并分页显示
     * @param usreId 用户id
     * @param pageNum 当前页码
     * @param pageSize  每页显示数量
     * @return ServerResponse<PageInfo<Shipping>>
     */
    ServerResponse<PageInfo<Shipping>> selectAll(Integer usreId, Integer pageNum, Integer pageSize);
}
