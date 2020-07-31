package cn.hey.second.vo;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 登录的实体封装
 *
 * @author Long
 */
public class LoginVO {

    @NotEmpty(message = "学号不能为空")
    String sno;

    String password;

    public String getSno() {
        return sno;
    }

    public String getPassword() {
        return password;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVO ==> {" +
                "sno=" + sno +
                ", password='" + password + '\'' +
                '}';
    }
}
