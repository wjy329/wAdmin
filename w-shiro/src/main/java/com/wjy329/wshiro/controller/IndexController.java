package com.wjy329.wshiro.controller;

import com.wjy329.wcommon.constant.ResultCode;
import com.wjy329.wcommon.utils.WebUtils;
import com.wjy329.wshiro.entity.User;
import com.wjy329.wshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**

 * @description:
 *
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2018-09-03 20:11
 **/
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/unauth")
    @ResponseBody
    public String unauth(){
        return "你访问的页面不存在";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            subject.logout();
        }
        return "login";
    }

    @RequestMapping(value="login",method= RequestMethod.POST)
    @ResponseBody
    public String loginUser(@RequestParam("username")String username,
                            @RequestParam("password")String password,
                            @RequestParam("captcha") String captcha,
                            HttpSession httpSession){
        String sessionCaptcha = httpSession.getAttribute("KAPTCHA_SESSION_KEY").toString();
        if(!captcha.equals(sessionCaptcha)){
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, "验证码输入错误");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
            User user = (User) subject.getPrincipal();
            httpSession.setAttribute("user",user);
            return WebUtils.getInstance().writeMsg(ResultCode.OK, "登陆成功");
        }catch (Exception e){
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, "用户名或密码错误");
        }
    }

    @RequestMapping("/register")
    public String register(){
        return "/register";
    }

    /**
     * @description  注册
     * @author wjy329
     * @Date 2018/12/8
     */
    @ResponseBody
    @RequestMapping(value="/register",produces="application/json;charset=UTF-8")
    public String registerData(User user){
        try{
            this.userService.regiserUser(user);
            return WebUtils.getInstance().writeMsg(ResultCode.OK,"注册成功");
        }catch(Exception e){
            return WebUtils.getInstance().writeMsg(ResultCode.ERROR, e.getMessage());
        }
    }

    @RequestMapping("/test")
    public String test(){
        return "/test";
    }

    @RequestMapping("/404")
    public String page404(){
        return "/404";
    }
}
