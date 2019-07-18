package commonUtils;

import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 日志工具类
 *
 * @author doye
 * @date 2018/7/18 17:02
 */
public class LogUtil {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LogUtil.class);

    /**
     * 获取Obj对象的fieldName属性的值
     *
     * @param obj       对象
     * @param fieldName 属性
     * @return 属性值
     * @author doye
     * @date 10:52 2018/5/28
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object fieldValue = null;
        if (null == obj) {
            return null;
        }
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (!methodName.startsWith("get")) {
                continue;
            }
            if (methodName.startsWith("get") && methodName.substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                try {
                    fieldValue = method.invoke(obj);
                } catch (Exception e) {
                    logger.error("取值出错，方法名 " + methodName);
                }
            }
        }
        return fieldValue;
    }
}
