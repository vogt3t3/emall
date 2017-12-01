package com.emall.service.impl;

import com.emall.common.Const;
import com.emall.common.ServerResponse;
import com.emall.common.TokenCache;
import com.emall.dao.UserDao;
import com.emall.pojo.User;
import com.emall.service.UserService;
import com.emall.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by Administrator on 2017/11/28.
 */
@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    public static String simpleMD5(String originStr) {
        return new Md5Hash(originStr).toString();
    }
    @Override
    @Transactional
    public ServerResponse<String> registry(User user) {
        //1-校验用户名是否已经存在
        ServerResponse<String> validResponse = this.checkValid(user.getUsername(),Const.ValidType.USERNAME);
        if(!validResponse.isSuccess()){
            return ServerResponse.getFailServerResponse(null,"用户已存在");
        }
        //2-校验email是否已存在
         validResponse = this.checkValid( user.getEmail(),Const.ValidType.EMAIL);
        if(!validResponse.isSuccess()){
            return ServerResponse.getFailServerResponse(null,"邮箱已存在");
        }
        //3-设置用户默认权限为用户权限（常量）
        user.setRole(Const.Role.ROLE_USER);
        //4-MD5对密码进行加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int count = userDao.registry(user);
        if(count == 0){
            return ServerResponse.getFailServerResponse(null,"注册失败");
        }
        return ServerResponse.getSuccessServerResponse(null,"注册成功");
    }
    @Transactional(readOnly = true)
    @Override
    public ServerResponse<User> login(String username, String password){
        //1-验证用户名是否已存在，不存在，返回错误信息
        int count = userDao.check_username(username);
        if(count == 0){
            return ServerResponse.getFailServerResponse(null,"用户名不存在");
        }
        //TODO 2-对password进行MD5密码处理
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        //3-登录验证密码是否正确，密码错误，返回错误信息
        User login = userDao.login(username, md5Password);
        if(login == null){
        //4-如果登录成功，返回数据中清除密码，不应保留密码
            return ServerResponse.getFailServerResponse(null,"密码错误");
        }
        //应用了org.apche.commons.lang3.StringUtils工具类，返回空字符串""
        login.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.getSuccessServerResponse(login,"登录成功");
    }

    @Override
    @Transactional
    public ServerResponse getByUsername(String username) {
        User byUsername = null;
        try {
            byUsername = userDao.getByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(byUsername != null){
            return ServerResponse.getSuccessServerResponse(null,"用户已存在");
        }
        return  ServerResponse.getFailServerResponse(null,"校验成功");
}

    @Override
    @Transactional
    public User getByUsername2(String username) {
        User user = null;
        try {
            user = userDao.getByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    @Transactional
    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if(org.apache.commons.lang3.StringUtils.isNotBlank(type)){
            if(Const.ValidType.USERNAME.equals(type)){
                int count = userDao.check_username(str);
                System.out.println(count);
                if(count > 0){
                    return ServerResponse.getFailServerResponse(null,"用户已存在");
                }
            } else if(Const.ValidType.EMAIL.equals(type)){
                int count = userDao.check_email(str);
                if(count > 0){
                    return ServerResponse.getFailServerResponse(null,"该邮箱账号已存在");
                }
            } else{
            return ServerResponse.getFailServerResponse(null,"参数错误，只能是用户名或者email");
        }
        }
        return ServerResponse.getSuccessServerResponse(null,"用户名或邮箱可用，校验成功");
    }
    @Transactional
    @Override
    public ServerResponse<String> getQuestionByUsername(String username){
        ServerResponse<String> validResponse = this.checkValid(username, Const.ValidType.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.getFailServerResponse(null,"用户名不存在");
        }
        String question = userDao.selectQuestionByUsername(username);
        if(StringUtils.isNoneBlank(question)){
            return ServerResponse.getFailServerResponse(question,null);
        }
        return ServerResponse.getFailServerResponse(null,"该用户没有设置找回密码的问题");
    }

    @Transactional
    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int count = userDao.checkAnswer(username,question,answer);
        if(count > 0){
            //此时，用户提交的答案是正确的，首先通过UUID算法生成唯一的token值
            String forgetToken = UUID.randomUUID().toString();
            //通过GUAVA的缓存存放该Token令牌
            TokenCache.setKey("token_"+username,forgetToken);
            //将token令牌存入ServerResponse进行返回
            return ServerResponse.getSuccessServerResponse(forgetToken,null);
        }
        return ServerResponse.getSuccessServerResponse(null,"预设问题或答案错误，您无法修改密码");
    }
    @Transactional
    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        //1-校验令牌是否存在
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.getFailServerResponse(null,"参数错误，需要传递Token令牌！");
        }
        //2-校验用户名是否为空，如果username不存在，则直接返回错误信息，否则token_就可以获得forgetToken,存在安全隐患
        ServerResponse<String> validResponse = this.checkValid(username, Const.ValidType.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.getFailServerResponse(null,"用户名不存在");
        }
        //3-从GUAVA缓存中获取Token令牌进行非空校验
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.getFailServerResponse(null,"Token无效或已过期！");
        }
        //4-对比前端传来的token与缓存中的token值是否一致，应用StringUtils进行equals比较,增强代码健壮性和安全性
        if (StringUtils.equals(token, forgetToken)) {
            //MD5密码加密
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            //修改密码
            int count = userDao.updatePasswordByUsername(username, md5Password);
            if (count > 0) {
                return ServerResponse.getSuccessServerResponse(null,"密码修改成功");
            }
        } else {
                return ServerResponse.getFailServerResponse(null,"Token无效，请重新获取");
            }
        return ServerResponse.getFailServerResponse(null,"修改密码失败");
        }
    @Transactional
    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        //验证旧密码
        int count = userDao.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(count == 0){
            return ServerResponse.getFailServerResponse(null,"旧密码错误，请重新输入！");
        }
        //更改密码
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userDao.updateByPrimaryKey(user);
        if(updateCount > 0){
            return  ServerResponse.getSuccessServerResponse(null,"更改密码成功");
        }
        return ServerResponse.getFailServerResponse(null,"更新密码失败");
    }
    @Transactional
    @Override
    public ServerResponse<User> updateInformation(User user) {
        //1-不允许修改username
        //2-当email有修改时，检查修改的email不能是已经注册过的email
        int count = userDao.checkEmailByUserId(user.getEmail(),user.getId());
        if(count > 0){
            return ServerResponse.getFailServerResponse(null,"该邮箱地址已被其他用户注册，请修改");
        }
        //把更新写入到对象中
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        //更新
        int updateCount = userDao.updateByPrimaryKey(updateUser);
        if(updateCount > 0){
            return ServerResponse.getSuccessServerResponse(updateUser,"更新个人信息成功！");
        }
        return ServerResponse.getFailServerResponse(null,"更新个人信息失败");
    }
    @Transactional
    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user = userDao.selectById(id);
        if(user == null){
            return ServerResponse.getFailServerResponse(null,"找不到当前用户");
        }
        //置空密码
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.getSuccessServerResponse(user,null);
    }

}


