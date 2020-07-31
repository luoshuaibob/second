package cn.hey.second.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 自定义全局异常拦截器
 * @author Long
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 拦截所有异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        // 打印异常信息
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            log.info("GlobalException ==> "+ex.getCodeMsg());
            return Result.error(ex.getCodeMsg());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            //绑定错误返回很多错误，是一个错误列表，只需要第一个错误
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            log.info("BindException ==> "+msg);
            //给状态码填充参数
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }

    }
}
