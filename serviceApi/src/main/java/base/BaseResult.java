package base;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 基础返回结果集，Json数据返回
 *
 * @author zhouji
 */
public class BaseResult<T> {

    private static final int CODE_JUMP_URL = 301;
    private static final int CODE_JUMP_TO_SELECT_BUSINESS_UNIT_PAGE = 333;
    private static final int CODE_JUMP_TO_CHANGE_PASSWORD_PAGE = 334;
    private static final int CODE_JUMP_TO_ERROR_PAGE = 444;

    private static final int CODE_SUCCESS = 1;
    private static final int CODE_FAIL = -1;
    private static final String STR_SUCCESS = "成功";
    private static final String STR_FAIL = "失败";

    private boolean success;
    private int statusCode;
    private String message;
    @JSONField(serialzeFeatures = {SerializerFeature.WriteMapNullValue})
    private T data;

    public void buildSuccess() {
        this.buildSuccess((T) new Object());
    }

    public void buildSuccess(T data) {
        this.buildSuccess(data, STR_SUCCESS);
    }

    public void buildSuccess(T data, String message) {
        this.buildSuccess(CODE_SUCCESS, data, message);
    }

    public void buildSuccess(int code, T data, String message) {
        this.statusCode = code;
        this.data = data;
        this.message = message;
        this.success = true;
    }

    public void buildFail() {
        this.buildFail(STR_FAIL);
    }

    public void buildFail(String message) {
        this.buildFail((T) new Object(), message);
    }

    public void buildFail(T data, String message) {
        this.buildFail(CODE_FAIL, data, message);
    }

    public void buildFail(int code, T data, String message) {
        this.statusCode = code;
        this.data = data;
        this.message = message;
    }

    public void buildJumpToErrorPage(T data) {
        this.buildFail(CODE_JUMP_TO_ERROR_PAGE, data, null);
    }

    public void buildJumpToSelectBusinessUnitPage(T data) {
        this.buildFail(CODE_JUMP_TO_SELECT_BUSINESS_UNIT_PAGE, data, null);
    }

    public void buildJumpToSelectChangePasswordPage(T data) {
        this.buildFail(CODE_JUMP_TO_CHANGE_PASSWORD_PAGE, data, null);
    }

    public void buildJumpToUlr(T data) {
        this.buildFail(CODE_JUMP_URL, data, null);
    }

    public void buildJumpToUlr(T data, String message) {
        this.buildFail(CODE_JUMP_URL, data, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
