package com.emall.dao;

import com.emall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    int registry(User user);//注册

    /**
     *y验证用户名是否存在
     * @param username
     * @return
     */
    int check_username(@Param("username") String username);
    /**
     *验证用户名和密码登录
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    User login(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User getByUsername(String username)throws Exception;

    /**
     *检验邮箱是否已存在
     * @param email 邮箱账号
     * @return
     */
    int check_email(@Param("email") String email);

    /**
     *根据用户名查询提示问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(@Param("username") String username);

    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int updatePasswordByUsername(@Param("username") String username, @Param("md5Password") String md5Password);

    int updateByPrimaryKey(User record);

    int checkPassword(@Param("password") String password, @Param("id") Integer id);

    int checkEmailByUserId(@Param("email") String email, @Param("id") Integer id);
    User selectById(@Param("id") Integer id);
}