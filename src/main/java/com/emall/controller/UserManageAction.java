package com.emall.controller;

import com.emall.common.Const;
import com.emall.common.ServerResponse;
import com.emall.pojo.User;
import com.emall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class UserManageAction {
    @Autowired
    private UserService userService;

    /**
     * 管理员登录系统
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        //1-复用门户登录接口login
        ServerResponse<User> login = userService.login(username, password);
        //2-登录成功，对用户权限进行校验
        if(login.isSuccess()){
            User user = login.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
            //3-登录成功并符合管理员权限，将user信息写入session(此时，user的密码已经在service中进行了置空)
            session.setAttribute(Const.CURRENT_USER,user);
            }else{
                return ServerResponse.getFailServerResponse(null,"该用户没有管理权限，无法登录后台管理系统！");
            }
        }
        return login;
    }
}
