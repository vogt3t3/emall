package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.pojo.User;

/**
 * Created by Administrator on 2017/11/27.
 */
public interface UserService {

    /**
     *  登录
     * @param username 用户名
     * @param password  密码
     * @return
     */
    public ServerResponse<User> login(String username, String password);

    public ServerResponse getByUsername(String username);//通过用户名获取用户

    public User getByUsername2(String username);//通过用户名获取用户shiro



    /**
     *
     * @param str 用户名或邮箱
     * @param type  类型，具体见Const中的ValidType(USERNAME,EMAIL)
     * @return
     */
    public ServerResponse<String> checkValid(String str, String type);

    public ServerResponse<String> registry(User user);//注册
    /**
     * 根据用户名获得密码问题
     * @param username
     * @return
     */
    public ServerResponse<String> getQuestionByUsername(String username);

    /**
     * 提交问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    public ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 忘记密码重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
   public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 登录状态下重置密码
     * @param passwordOld 旧密码
     * @param passwordNew  新密码
     * @param user  增加user参数，防止横向越权
     * @return
     */
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * 更改登录用户信息
     * @param user
     * @return
     */
    public ServerResponse<User> updateInformation(User user);

    /**
     * 根据id获取信息
     * @param id
     * @return
     */
    public ServerResponse<User> getInformation(Integer id);
}
