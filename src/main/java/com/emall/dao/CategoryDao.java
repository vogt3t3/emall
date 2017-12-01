package com.emall.dao;

import com.emall.pojo.Category;

import java.util.List;
import java.util.Map;

public interface CategoryDao {
    public List<Category> findSonCategory(int id) throws Exception;
    public void addCategory(Category category) throws Exception;
    public void  updateCategory(Category category) throws Exception;
    public List<Integer> findDeepCategory(int id) throws Exception;
}