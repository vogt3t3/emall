package com.emall.service.impl;

import com.emall.common.ServerResponse;
import com.emall.dao.ShippingDao;
import com.emall.pojo.Shipping;
import com.emall.service.ShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
@Service("shippingSer")
public class ShippingServiceimpl implements ShippingService {
    @Autowired
    private ShippingDao shippingDao;

    @Transactional
    @Override                  //删除收货地址
    public  ServerResponse<PageInfo<Shipping>> deleteByPrimaryKey(Integer userId,Integer id) {
        int num=shippingDao.deleteByPrimaryKey(id);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"删除地址失败");
            return serverResponse;
        }
        ServerResponse serverResponse=selectAll(userId,null,null);
        serverResponse.setMsg("删除地址成功");
        return serverResponse;
    }

    @Transactional
    @Override                   //添加收货地址
    public  ServerResponse<PageInfo<Shipping>> insert(Integer userId,Shipping shipping) {
        int num=shippingDao.insert(shipping);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"新建地址失败");
            return serverResponse;
        }
        ServerResponse serverResponse=selectAll(userId,null,null);
        serverResponse.setMsg("新建地址成功");
        return serverResponse;
    }

    @Transactional
    @Override                       //查询收货地址详细信息
    public ServerResponse<Shipping> selectByPrimaryKey(Integer id) {
        Shipping shipping=shippingDao.selectByPrimaryKey(id);
        ServerResponse serverResponse=ServerResponse.getSuccessServerResponse(shipping,null);
        return serverResponse;
    }

    @Transactional
    @Override                       //更改收货地址
    public  ServerResponse<PageInfo<Shipping>> updateByPrimaryKey(Integer userId,Shipping record) {
        int num=shippingDao.updateByPrimaryKey(record);
        if(num<0){
            ServerResponse serverResponse=ServerResponse.getFailServerResponse(null,"更新地址失败");
            return serverResponse;
        }
        ServerResponse serverResponse=selectAll(userId,null,null);
        serverResponse.setMsg("更新地址成功");
        return serverResponse;
    }

    @Transactional
    @Override                     //查询所有收货地址并分类显示
    public ServerResponse<PageInfo<Shipping>> selectAll(Integer userId, Integer pageNum, Integer pageSize) {
        if (pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }

        PageHelper.offsetPage(pageNum-1,pageSize);
        List<Shipping> lis = shippingDao.selectAll(userId);
        PageInfo<Shipping> page = new PageInfo<Shipping>(lis);
        ServerResponse serverResponse=ServerResponse.getSuccessServerResponse(page,null);
        return serverResponse;
    }
}
