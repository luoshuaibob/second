package cn.hey.second.exception;


/**
 * 自定义全局异常类
 * @author Long
 */
public class GlobalException extends RuntimeException {

    private static final long SERVIAL_VERSIONUID = 1L;

    private final CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
