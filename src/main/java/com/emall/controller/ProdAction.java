package com.emall.controller;

import com.emall.common.Const;
import com.emall.common.ServerResponse;
import com.emall.pojo.Product;
import com.emall.pojo.User;
import com.emall.service.ProductService;
import com.emall.vo.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/29 0029.
 */
@Controller
@RequestMapping("/manage/product/")
public class ProdAction {

    @Autowired
    private ProductService productService;

    /**
     * 后台-----商品上架下架
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Product> updateProductStatus(Integer productId, Integer status){

       return productService.updateProductStatus(productId,status);

    }

    /**
     * 后台----添加或更新商品
     * @param product
     * @return
     */
    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Product> saveOrUpdateProduct(Product product){

        return productService.saveOrUpdateProduct(product);
    }

    /**
     * 后台---通过商品id查看商品详情
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ProductDetailVo> showById(Integer productId, HttpSession session){
        ServerResponse<ProductDetailVo> productDetailVoServerResponse =  productService.showById(productId);
        if (productDetailVoServerResponse.getStatus() == 1){
            session.setAttribute("productId",productId);
        }
        return productService.showById(productId);
    }

    /**
     * 后台-----通过商品id或商品名称搜索商品
     * @param productId
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "search.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo<ProductSerarchVo>> findByIdOrName(Integer productId, String productName, Integer pageNum, Integer pageSize,HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        return productService.findByIdOrName(productId,productName,pageNum,pageSize,user);
    }

    /**
     * 后台----查看商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo<ProductListVo>> findAll(Integer pageNum,Integer pageSize,HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        return productService.findAll(pageNum,pageSize,user);
    }

    /**
     * 后台----图片上传
     * @param sourceFile
     * @param session
     * @return
     */

//实现文件上传功能

    @RequestMapping(value="upload.do",method=RequestMethod.POST)
    @ResponseBody
    public ServerResponse<UploadVo> uploadImage(@RequestParam("filePath") MultipartFile sourceFile,HttpSession session) throws Exception{
        Integer productId = (Integer) session.getAttribute("productId");
        String pathUri = "";
        if(!sourceFile.isEmpty()){
            File targetFile = new File("D:/3_develop/temp", System.currentTimeMillis()+sourceFile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(sourceFile.getInputStream(),targetFile );
            pathUri = targetFile.getName();
        }
        return productService.uploadImage(productId,pathUri);
    }

    /**
     * 后台-----富文本上传
     * @param sourceFile
     * @param session
     * @return
     */
    @RequestMapping(value = "richtext_img_upload.do",method = RequestMethod.POST)
    @ResponseBody
    public FullUploadVo fullUploadImage(@RequestParam("filePaths") MultipartFile[] sourceFile, HttpSession session){

        Integer productId = (Integer) session.getAttribute("productId");
        List<String> pathUriList = new ArrayList<>();
        for (int i=0;i<sourceFile.length;i++){
            MultipartFile file = sourceFile[i];
            if (!file.isEmpty()){
                File targetFile = new File("D:/temp",System.currentTimeMillis()+file.getOriginalFilename());
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(),targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pathUriList.add(targetFile.getName());
            }
        }

        return productService.fullUploadImage(productId,pathUriList);
    }

}
