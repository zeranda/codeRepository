package exception;

/**
 * 业务异常类
 * Created by Huangyuheng on 2017/9/11.
 */
public class BizException extends RuntimeException {

    private Integer code;

    private boolean success = false;

    private Object data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Integer code) {
        super(message);
        this.setCode(code);
    }

    public BizException(String message, Integer code, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String message, boolean success) {
        this(message, success, null);
    }

    public BizException(String message, boolean success, Integer code) {
        this(message, success, code, null);
    }

    public BizException(String message, boolean success, Integer code, Object data) {
        super(message);
        this.success = success;
        this.code = code;
        this.data = data;
    }

}
