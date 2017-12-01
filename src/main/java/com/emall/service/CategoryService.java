package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.dao.CategoryDao;
import com.emall.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
public interface CategoryService {
    /**
     *获取品类子节点
     * @param id 品类id
     * @return
     */
    public ServerResponse<List<Category>> findSonCategory(int id);

    /**
     *增加节点
     * @param category 品类
     * @return
     */
    public ServerResponse addCategory(Category category);

    /**
     * 更新品类的名称
     * @param category 品类
     * @return
     */
    public ServerResponse updateCategory(Category category);

    /**
     * 获取当前分类的id和递归子节点的id
     * @param id 品类id
     * @return
     */
    public ServerResponse<List<Integer>> findDeepCategory(int id);
}
