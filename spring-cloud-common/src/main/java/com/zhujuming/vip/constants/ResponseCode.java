package com.zhujuming.vip.constants;

/**
 * 响应码枚举对象。
 *
 * @author dragon
 */
public enum ResponseCode {

    OK(2000, "Success"), //通用

    REMOTE_CALL_FAIL(5000, "服务暂时不可用:"),//熔断专用

    INSERT_ERROR(-108, "新增失败");
//
//    DELETE_ERROR(-109, "数据删除失败，请刷新重试"),
//
//    UPDATE_ERROR(-107, "更新失败，请刷新重试"),
//
//    OPERATE_ERROR(5500, "操作失败"),
//
//    OPERATION_ERROR(5501, "The operation failure!"),
//
//    PARAM_INVALID(1000, "缺失参数或无效"),
//
//    PARAM_ISEMPTY(1007, "Param cannot be empty"),
//
//    PARAM_ILLEGAL(1008, "Illegal parameters"),
//
//    PARAM_INVALID_1(1001, "Missing parameter or invalid"),
//
//    THIRD_PARTY_EXCEPTION(3000, "第三方接口异常"),
//
//    THIRD_PARTY_EXCEPTION_1(3001, "Third-party interface exception"),
//
//    THIRD_PARTY_EXCEPTION_2(3002, "Personnel information acquisition failed"),
//
//    THIRD_PARTY_EXCEPTION_3(3003, "Image not uploaded"),
//
//    THIRD_PARTY_EXCEPTION_4(3004, "The image is malformed and must be GIF PNG JPG"),
//
//    THIRD_PARTY_EXCEPTION_5(3005, "取消失败"),
//
//    SYSTEM_EXCEPTION(5000, "系统异常"),
//
//    DOCUMENT_ERROR(5001, "document error"),
//
//    PARAMETER_EXCEPTION(5002, "Parameter exception"),
//
//    WX_INTERFACE_ERROR(7000, "请求微信接口异常"),
//
//    SUITE_ACCESS_TOKEN_EXCEPTION(8001, "获取SuiteAccessToken出现异常"),
//
//    SYSTEM_EXCEPTION_1(8002, "通过corpID获取授权企业信息出现异常"),
//
//    SYSTEM_EXCEPTION_3(8003, "没有获取到accessToken"),
//
//    SYSTEM_EXCEPTION_4(8004, "解析回调的xml出现异常"),
//
//    SYSTEM_EXCEPTION_2(8003, "获取第三方应用suite_ticket出现异常"),
//
//    SYSTEM_EXCEPTION_5(8005, "redis参数缺失"),
//
//    SYSTEM_EXCEPTION_8(8008, "JSON转换错误"),
//
//    ACCESS_TOKEN_EXCEPTION(9000, "获取access_token失败"),
//
//    AUTH_INVALID(4000, "身份验证失败"),
//
//    TOKEN_MISSING(4001, "token缺失"),
//
//    TOKEN_INVALID(4002, "token Fail"),
//
//    ACCESS_DENIED(4003, "没有权限"),
//
//    AUTH_INVALID_ERROR(4005, "身份验证失败");


    private final int value;
    private final String description;

    ResponseCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int value() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static ResponseCode valueOf(int code) {
        for (ResponseCode responseCode : values()) {
            if (responseCode.value == code) {
                return responseCode;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

}
