package cn.hey.second.controller;

import cn.hey.second.exception.CodeMsg;
import cn.hey.second.exception.GlobalException;
import cn.hey.second.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 登录处理器
 * @author Long
 */
@Slf4j
@Controller
public class LoginController {

    @PostMapping("/do/login")
    @ResponseBody
    public String doLogin( @Valid LoginVO loginVO){
        log.info(loginVO.toString());
        String sno = loginVO.getSno();
        String password = loginVO.getPassword();
        // 获取当前的subject
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(sno, password);
        // 执行登录方法，如果没有异常就说明正常
        try {
            // shiro 登录 授权 认证
            subject.login(token);
            // 放到session中
            subject.getSession().setAttribute("sno",sno);
            String msg = "success";
            log.info("do login result ==> "+msg);
            return msg;
        } catch (UnknownAccountException e) {
            log.warn("do login result ==> "+CodeMsg.STUDENT_NUM_NOT_EXISTS.getMsg());
            throw new GlobalException(CodeMsg.STUDENT_NUM_NOT_EXISTS);
        } catch (IncorrectCredentialsException e){
            log.warn("do login result ==> "+CodeMsg.PASSWORD_WRONG.getMsg());
            throw new GlobalException(CodeMsg.PASSWORD_WRONG);
        } catch (LockedAccountException e){
            log.warn("do login result ==> "+CodeMsg.ACCOUNT_LOCKED.getMsg());
            throw new GlobalException(CodeMsg.ACCOUNT_LOCKED);
        }
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        // 移除session中的sno
        subject.getSession().removeAttribute("sno");
        // 登出
        subject.logout();
        log.info("User "+subject.getPrincipal()+" logout.");
        return "redirect:/home";
    }
}
