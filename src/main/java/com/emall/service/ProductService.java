package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.pojo.Product;
import com.emall.pojo.User;
import com.emall.vo.*;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
public interface ProductService {
    /**
     * 门户---通过商品id查看商品详情
     * @param id
     * @return
     */
    ServerResponse<Product> findById(Integer id);

    /**
     *后台-----商品上架下架
     * @param id
     * @param status
     * @return
     */
    ServerResponse<Product> updateProductStatus(Integer id, Integer status);

    /**
     * 后台----添加或更新商品
     * @param product
     * @return
     */
    public ServerResponse<Product> saveOrUpdateProduct(Product product);

    /**
     * 后台---通过商品id查看商品详情
     * @param productId
     * @return
     */
    public ServerResponse<ProductDetailVo> showById(Integer productId);

    /**
     *  后台-----通过商品id或商品名称搜索商品
     * @param productId
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo<ProductSerarchVo>> findByIdOrName(Integer productId, String productName, Integer pageNum, Integer pageSize,User user);

    /**
     * 后台-----查看商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo<ProductListVo>> findAll(Integer pageNum, Integer pageSize,User user);

    /**
     * 门户----商品查询及动态排序
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy  排序条件
     * @return  查到的商品信息
     */
    public ServerResponse<PageInfo<ProductListVo>> findListByKeyword(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy);

    /**
     * 后台----图片上传
     * @param productId
     * @param uri
     * @return
     */
    public ServerResponse<UploadVo> uploadImage(Integer productId, String uri);

    /**
     * 后台-----富文本上传
     * @param productId
     * @param pathUri
     * @return
     */
    public FullUploadVo fullUploadImage(Integer productId, List<String> pathUri);


}
