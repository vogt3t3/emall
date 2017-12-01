package com.emall.controller;

import com.emall.common.ServerResponse;
import com.emall.pojo.Product;
import com.emall.service.ProductService;
import com.emall.vo.ProductListVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Controller
@RequestMapping("/product/")
public class ProductAction {

    @Autowired
    private ProductService productService;

    /**
     * 门户 ---- 通过商品id查找商品详情
     * @param productId
     * @return
     */
    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    public @ResponseBody
    ServerResponse<Product> findById(Integer productId){

        return productService.findById(productId);
    }

    /**
     * 门户----商品查询及动态排序
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo<ProductListVo>> findListByKeyword(Integer categoryId,String keyword,Integer pageNum,Integer pageSize,String orderBy){
        return productService.findListByKeyword(categoryId,keyword,pageNum,pageSize,orderBy);

    }

}
