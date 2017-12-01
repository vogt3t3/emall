package com.emall.controller;

import com.emall.common.ServerResponse;
import com.emall.pojo.Category;
import com.emall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Controller
@RequestMapping("manage/category")
public class CategoryAction {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取品类节点
     * @param categoryId 品类id
     * @return
     */
    @RequestMapping(value="get_category.do/categoryId/{categoryId}",method= RequestMethod.GET)
    public @ResponseBody ServerResponse<Category> getCategory(@PathVariable("categoryId") Integer categoryId){
        ServerResponse sr=categoryService.findSonCategory(categoryId);
        System.out.println(sr);
        return sr;
    }

    /**
     * 增加商品类别
     * @param parent_id 父类别id(默认0)
     * @param categoryName 类别名
     * @return
     */
    @RequestMapping(value="add_category.do",method=RequestMethod.POST)
    public @ResponseBody ServerResponse addCategory(Integer parent_id,String categoryName){
        Category c=new Category(parent_id,categoryName);
        ServerResponse sr=categoryService.addCategory(c);
        return sr;
    }

    /**
     * 修改类别名
     * @param categoryId 类别id
     * @param categoryName 类别名
     * @return
     */
    @RequestMapping(value="update_category.do/categoryId/{categoryId}/categoryName/{categoryName}",method=RequestMethod.PUT)
    public @ResponseBody ServerResponse updateCategory(@PathVariable("categoryId") Integer categoryId,@PathVariable("categoryName") String categoryName){
            Category c=new Category(categoryId,categoryName);
        ServerResponse sr=categoryService.updateCategory(c);
        return sr;
    }

    /**
     * 获取深层次节点和当前节点id
     * @param categoryId 类别id
     * @return
     */
    @RequestMapping(value="get_deep_category.do/categoryId/{categoryId}",method=RequestMethod.GET)
    public @ResponseBody ServerResponse<List<Integer>> getDeepCategory(@PathVariable("categoryId") Integer categoryId){
        ServerResponse sr=categoryService.findDeepCategory(categoryId);
        return sr;
    }
}
