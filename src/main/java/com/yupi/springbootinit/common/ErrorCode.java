package com.yupi.springbootinit.common;

/**
 * 自定义错误码
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "Params error"),
    NOT_LOGIN_ERROR(40100, "Not login"),
    NO_AUTH_ERROR(40101, "No permission"),
    NOT_FOUND_ERROR(40400, "Data not found"),
    FORBIDDEN_ERROR(40300, "Forbidden"),
    SYSTEM_ERROR(50000, "System error"),
    OPERATION_ERROR(50001, "Operation error");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
