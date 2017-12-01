package com.emall.service.impl;

import com.emall.common.ResponseCode;
import com.emall.common.ServerResponse;
import com.emall.dao.CategoryDao;
import com.emall.pojo.Category;
import com.emall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryDao categoryDao;
    @Override
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED )
    public ServerResponse<List<Category>> findSonCategory(int id) {
        List<Category> list=null;
        try{
            list=categoryDao.findSonCategory(id);
            if(list!=null) {
                String msg = null;
                return ServerResponse.getSuccessServerResponse(list, msg);
            }
        }catch (Exception e){
        e.printStackTrace();
        }
        String msg="未找到该品类";
        return ServerResponse.getFailServerResponse(list,msg);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse addCategory(Category category){
        String msg="添加品类失败";
        try{
        categoryDao.addCategory(category);
           msg="添加品类成功";
            return ServerResponse.getSuccessServerResponse(null,msg);
        }catch (Exception e){
        }
        return ServerResponse.getFailServerResponse(null,msg);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse updateCategory(Category category) {
        String msg="更新品类名字失败";
        try{
            categoryDao.updateCategory(category);
            msg="更新品类名字成功";
            return ServerResponse.getSuccessServerResponse(null,msg);
        }catch (Exception e){
        }
        return ServerResponse.getFailServerResponse(null,msg);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public ServerResponse<List<Integer>> findDeepCategory(int id) {
        List<Integer> list=null;
        try {
            list=categoryDao.findDeepCategory(id);
            return ServerResponse.getSuccessServerResponse(list,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(list,"无权限");
    }
}
