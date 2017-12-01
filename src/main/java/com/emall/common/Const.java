package com.emall.common;

/**
 * Created by Administrator on 2017/11/29 0029.
 */
public class Const {
    public static final String CURRENT_USER="current_User";
    //通过定义内部接口的方式，将静态常量根据功能进行分组
    public interface Role{
        int ROLE_USER = 0;//用户权限
        int ROLE_ADMIN = 1;//管理员权限
    }
    //注册时用来校验用户名或者密码是否是有效的类型
    public interface ValidType{
        String EMAIL ="email";
        String USERNAME = "username";
    }
}
