package com.emall.controller;

import com.emall.common.Const;
import com.emall.common.ResponseCode;
import com.emall.common.ServerResponse;
import com.emall.pojo.User;
import com.emall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/11/28.
 */
@Controller
@RequestMapping("user")
public class UserAction {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "registry.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> registry(User user) {
        return userService.registry(user);
    }

    /**
     *查看当前登录用户的信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        System.out.println(user);
        if(user!=null){
            return ServerResponse.getSuccessServerResponse(user,null);
        }
        return ServerResponse.getFailServerResponse(null,"用户未登录，请先登录");
    }
    /**
     *用户登录系统
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
   public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> login = userService.login(username, password);
        if(login.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,login.getData());
            System.out.println(login.getData());
        }
        return login;
    }

    /**
     * 检查邮箱和用户名是否已存在
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> check_valid(String str, String type) {
       return userService.checkValid(str,type);
    }

    /**
     * 注销
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.getSuccessServerResponse(null,null);
    }

    /**
     * 忘记密码，获取问题
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetPasswordGetQuestion(String username){
        return userService.getQuestionByUsername(username);
    }

    /**
     * 忘记密码，提交问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetPasswordCheckAnswer(String username,String question,String answer){
        return  userService.checkAnswer(username,question,answer);
    }

    /**
     * 预留答案验证后重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */

    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return userService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 登录状态下重置密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "resetPassword.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.getFailServerResponse(null,"用户尚未登录");
        }
        return userService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 登录状态修改登录信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> resetPassword(HttpSession session,User user){
        //验证用户是否已经登录
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.getFailServerResponse(null,"用户尚未登录");
        }
        //更新时userId和username不允许被修改，因此要从session中获取
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        //更新信息，放入session
        ServerResponse<User> resultResponse = userService.updateInformation(user);
        if(resultResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,resultResponse.getData());
        }
        return resultResponse;
    }

    /**
     * 未登录强制登录并获取信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.getNoLoginServerResponse(null,"用户未登录，返回状态码10，前端判断状态码并实现强制登录");
        }
        return userService.getInformation(user.getId());
    }
}
