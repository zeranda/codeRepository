package base;

public class JsonResult<T> extends BaseResult<T> {

    public static JsonResult success() {
        JsonResult result = new JsonResult();
        result.buildSuccess();
        return result;
    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> result = new JsonResult<>();
        result.buildSuccess(data);
        return result;
    }

    public static <T> JsonResult<T> success(T data, String message) {
        JsonResult<T> result = new JsonResult<>();
        result.buildSuccess(data, message);
        return result;
    }

    public static <T> JsonResult<T> success(int code, T data, String message) {
        JsonResult<T> result = new JsonResult<>();
        result.buildSuccess(code, data, message);
        return result;
    }

    public static JsonResult fail() {
        JsonResult result = new JsonResult<>();
        result.buildFail();
        return result;
    }

    public static JsonResult fail(String message) {
        JsonResult result = new JsonResult();
        result.buildFail(message);
        return result;
    }

    public static <T> JsonResult<T> fail(String message, T data) {
        JsonResult<T> result = new JsonResult<>();
        result.buildFail(data, message);
        return result;
    }

    public static <T> JsonResult<T> fail(int code, String message) {
        JsonResult<T> result = new JsonResult<>();
        result.buildFail(code, null, message);
        return result;
    }

    public static <T> JsonResult<T> jumpToErrorPage(T data) {
        JsonResult<T> productJsonResult = new JsonResult<>();
        productJsonResult.buildJumpToErrorPage(data);
        return productJsonResult;
    }

    public static <T> JsonResult<T> jumpToSelectBusinessUnitPage(T data) {
        JsonResult<T> productJsonResult = new JsonResult<>();
        productJsonResult.buildJumpToSelectBusinessUnitPage(data);
        return productJsonResult;
    }

    public static <T> JsonResult<T> jumpToChangePasswordPage(T data) {
        JsonResult<T> productJsonResult = new JsonResult<>();
        productJsonResult.buildJumpToSelectChangePasswordPage(data);
        return productJsonResult;
    }

    public static JsonResult<String> jumpToUrl(String url) {
        JsonResult<String> productJsonResult = new JsonResult<>();
        productJsonResult.buildJumpToUlr(url);
        return productJsonResult;
    }

    public static JsonResult<String> jumpToUrl(String url, String message) {
        JsonResult<String> productJsonResult = new JsonResult<>();
        productJsonResult.buildJumpToUlr(url, message);
        return productJsonResult;
    }
}