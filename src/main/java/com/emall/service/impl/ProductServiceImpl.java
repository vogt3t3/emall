package com.emall.service.impl;

import com.emall.common.ResponseCode;
import com.emall.common.ServerResponse;
import com.emall.dao.ProductDao;
import com.emall.pojo.Product;
import com.emall.pojo.User;
import com.emall.service.ProductService;

import com.emall.vo.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Service("productService")
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;
    @Transactional
    @Override
    public ServerResponse<Product> findById(Integer id) {

        Product product = null;
        try {
            product = productDao.findById(id);
            if(product != null){
                return ServerResponse.getSuccessServerResponse(product,null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"该商品已下架或删除");
    }

    @Transactional
    @Override
    public ServerResponse<Product> updateProductStatus(Integer id, Integer status) {

        try {
            int col = productDao.updateProductStatus(id,status);
            if(col == 0){
                return ServerResponse.getFailServerResponse(null,"修改商品状态失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getSuccessServerResponse(null," 修改商品状态成功");
    }

    @Transactional
    @Override
    public ServerResponse<Product> saveOrUpdateProduct(Product product) {
        try {
            int col = productDao.saveOrUpdateProduct(product);
            if(col == 1){
                if(product.getId() != null){
                    return ServerResponse.getSuccessServerResponse(null,"更新商品成功");
                }else{
                    return ServerResponse.getSuccessServerResponse(null, "新增商品成功");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"更新商品失败");
    }

    @Transactional
    @Override
    public ServerResponse<ProductDetailVo> showById(Integer productId) {
        ProductDetailVo productDetailVo = null;
        try {
            productDetailVo = productDao.showById(productId);
            if (productDetailVo != null){
                productDetailVo.setImageHost("http://img.emall.com");
                return ServerResponse.getSuccessServerResponse(productDetailVo,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"没有权限");
    }

    @Transactional
    @Override
    public ServerResponse<PageInfo<ProductSerarchVo>> findByIdOrName(Integer productId, String productName, Integer pageNum, Integer pageSize,User user) {

        List<ProductSerarchVo> productSerarchVoList = new ArrayList<ProductSerarchVo>();

        if (pageNum != null && pageSize != null){
            PageHelper.offsetPage(pageNum-1,pageSize);
        }else if (pageSize != null){
            PageHelper.offsetPage(0,pageSize);
        }else if (pageNum != null){
            PageHelper.offsetPage(pageNum-1,10);
        }else {
            PageHelper.offsetPage(0,10);
        }

        try {
            if (user!=null){
                productSerarchVoList = productDao.findByIdOrName(productId,productName);
                PageInfo<ProductSerarchVo> page = new PageInfo<ProductSerarchVo>(productSerarchVoList);
                return ServerResponse.getSuccessServerResponse(page,null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getNoLoginServerResponse(null,"用户未登录,请登录");
    }

    @Transactional
    @Override
    public ServerResponse<PageInfo<ProductListVo>> findAll(Integer pageNum, Integer pageSize,User user) {
        List<ProductListVo> productListVoList = new ArrayList<>();
        if (pageNum != null && pageSize != null){
            PageHelper.offsetPage(pageNum-1,pageSize);
        }else if (pageSize != null){
            PageHelper.offsetPage(0,pageSize);
        }else if (pageNum != null){
            PageHelper.offsetPage(pageNum-1,10);
        }else {
            PageHelper.offsetPage(0,10);
        }
        try {
            if (user!=null){
                productListVoList = productDao.findAll();
                PageInfo<ProductListVo> page = new PageInfo<ProductListVo>(productListVoList);
                return ServerResponse.getSuccessServerResponse(page,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getNoLoginServerResponse(null,"用户未登录,请登录");
    }

    @Transactional
    @Override
    public ServerResponse<PageInfo<ProductListVo>> findListByKeyword(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        List<ProductListVo> productListVoList = new ArrayList<>();
        if (pageNum == null && pageSize == null && orderBy == null){
            PageHelper.offsetPage(0,10,"");
        }else if (pageNum == null && pageSize == null) {
            PageHelper.offsetPage(0, 10, orderBy);
        }else if (pageNum == null && orderBy == null){
            PageHelper.offsetPage(0,pageSize,orderBy);
        }else if (pageSize == null && orderBy == null){
            PageHelper.offsetPage(pageNum-1,10,"");
        }else if (pageNum == null){
            PageHelper.offsetPage(0,pageSize,orderBy);
        }else if (pageSize == null){
            PageHelper.offsetPage(pageNum-1,10,orderBy);
        }else {
            PageHelper.offsetPage(pageNum-1,pageSize,orderBy);
        }

        try {
            productListVoList = productDao.findListByKeyword(categoryId,keyword);
            PageInfo<ProductListVo> page = new PageInfo<ProductListVo>(productListVoList);
            return ServerResponse.getSuccessServerResponse(page,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"参数错误");
    }

    @Transactional
    @Override
    public ServerResponse<UploadVo> uploadImage(Integer productId, String uri) {

        try {
            int col= productDao.uploadImage(productId,uri);
            if (col == 1){
                UploadVo uploadVo = new UploadVo(uri);
                uploadVo.setUrl("http://img.emall.com/"+uploadVo.getUri());
                return ServerResponse.getSuccessServerResponse(uploadVo,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.getFailServerResponse(null,"图片上传失败");
    }

    @Override
    @Transactional
    public FullUploadVo fullUploadImage(Integer productId, List<String> pathUri) {

        String uri = "";
        FullUploadVo fullUploadVo = new FullUploadVo();
        for (int i=0;i<pathUri.size();i++){
            uri = uri + pathUri.get(i);
        }
        try {
            int col =  productDao.fullUploadImage(productId,uri);
            if (col == 1){
                fullUploadVo.setSuccess("true");
                fullUploadVo.setFile_path("http://img.emall.com/"+uri);
                fullUploadVo.setMsg("上传成功");
                return fullUploadVo;
            }else {
                fullUploadVo.setFile_path("d:/"+uri);
                fullUploadVo.setSuccess("false");
                fullUploadVo.setMsg("error message");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullUploadVo;

    }

}






















