package cn.hey.second.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 信息码
 * @author Long
 */
@Slf4j
public class CodeMsg {

    private int code;
    private String msg;

    /**
     * 通用的错误码 5001xx
     */
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问高峰期，请稍等！");
    /**
     * 登录模块 502xx
     */
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static CodeMsg STUDENT_NUM_EMPTY = new CodeMsg(500211, "学号不能为空");
    public static CodeMsg PASSWORD_WRONG = new CodeMsg(500212, "密码错误");
    public static CodeMsg STUDENT_NUM_NOT_EXISTS = new CodeMsg(500213, "学号不存在");
    public static CodeMsg ACCOUNT_LOCKED = new CodeMsg(500213, "账户被锁定");


    /**
     * 查询课表模块
     * 5003xx
     */
    public static CodeMsg CLASS_NOT_EXIST = new CodeMsg(500300, "课表不存在");

    /**
     * 选课模块
     * 5004xx
     */
    public static CodeMsg CLASS_OVER = new CodeMsg(500400, "该课程已经被选完");
    public static CodeMsg REPEAT_CHOOSE = new CodeMsg(500401, "不能重复选择同一门课");
    public static CodeMsg CHOOSE_FAIL = new CodeMsg(500402, "提交失败");
    public static CodeMsg CLASS_NO_PLAN = new CodeMsg(500403, "没有该课程安排");

    /**
     * 构造函数
     */
    private CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * get set 方法
     * @return 属性
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 返回带参数的错误码
     * @param args 参数
     * @return 带参数的错误码对象
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        log.info(code+" <==> "+message);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }


}
