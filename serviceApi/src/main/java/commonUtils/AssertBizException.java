package commonUtils;

import exception.BizException;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: xuanyanxu
 * @Description: AssertBizException抽象类继承Assert抽象类, 可以使用本类调动Spring框架提供的断言方法(未过时)进行验证, 当验证信息不通过时, 抛出BizException!
 * @Date: Created in 2018/5/10 14:12
 * @since: 1.0.0
 * @Modified By:
 */
public abstract class AssertBizException {

    /**
     * AssertBizException 提供一个boolean类型的表达式,
     * 如果表达式expression的结果返回{@code false},则抛出一个{@code BizException}
     * 例:AssertBizException.state(id == null,"必须初始化ID属性");
     *
     * @param expression 一个布尔类型表达式
     * @param message    如果断言结果不成立(expression=false),抛出去的异常信息提示
     * @throws BizException 如果{@code expression} 结果是{@code false}
     */
    public static void state(boolean expression, String message) {
        try {
            Assert.state(expression, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供一个boolean类型的表达式,
     * 如果表达式expression的结果返回{@code false},则抛出一个{@code BizException}
     * 例:AssertBizException.isTrue(id != null,"属性ID不能为null");
     *
     * @param expression 一个布尔类型表达式
     * @param message    如果断言结果不成立(expression=false),抛出去的异常信息提示
     * @throws BizException 如果{@code expression} 结果是{@code false}
     */
    public static void isTrue(boolean expression, String message) {
        try {
            Assert.isTrue(expression, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供一个断言对象进行判定,对象必须是{@code null}
     * 例:AssertBizException.isNull(value,"value对象必须为null");
     *
     * @param object  需要检测的对象
     * @param message 如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果object结果不是{@code null}
     */
    public static void isNull(Object object, String message) {
        try {
            Assert.isNull(object, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供一个断言对象进行判定,对象必须不是{@code null}
     * 例:AssertBizException.notNull(value,"value对象必须不能为null");
     *
     * @param object  需要检测的对象
     * @param message 如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果object结果是{@code null}
     */
    public static void notNull(Object object, String message) {
        try {
            Assert.notNull(object, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供字符串进行校验
     * 字符串不能为@{code null}并且长度大于0
     * 例:AssertBizException.hasLength(name,"name不能为null并且name字符串长度大于0");
     *
     * @param text    需要检测的字符串
     * @param message 如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果text为null或字符串长度小于等于0
     */
    public static void hasLength(String text, String message) {
        try {
            Assert.hasLength(text, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供有效字符串文本内容进行校验
     * 字符串文本不能为@{code null}并且必须至少包含一个非空白字符
     * 例:AssertBizException.hasText(name,"name必须不能为null和''并且name字符串长度必须大于0");
     *
     * @param textContent 需要检测的字符串文本
     * @param message     如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果textContent不包含有效的文本内容
     */
    public static void hasText(String textContent, String message) {
        try {
            Assert.hasText(textContent, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供的textToSearch字符串不能包含substring字符串信息
     * Note:不忽略大小写
     * 例:AssertBizException.doesNotContain(name,"kaffa","name中不能包含'kaffa'字符串");
     *
     * @param textToSearch 需要被检测的字符串信息
     * @param substring    需要验证的字符串信息(textToSearch字符串中不能包含substring字符串)
     * @param message      如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果textToSearch字符串中包含substring字符串
     */
    public static void doesNotContain(String textToSearch, String substring, String message) {
        try {
            Assert.doesNotContain(textToSearch, substring, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供一个含有元素的数组
     * array数组不能为{@code null} 并且必须至少包含一个元素
     * 例:AssertBizException.notEmpty(array,"array不能为null,并且array中必须含有至少一个元素");
     *
     * @param array   需要验证的数组对象
     * @param message 如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果数组对象array为{@code null}或者数组不包含元素
     */
    public static void notEmpty(Object[] array, String message) {
        try {
            Assert.notEmpty(array, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供非null元素的数组
     * Note:如果数组array为null,则不进行校验
     * 例:AssertBizException.noNullElements(array,"array不能为null,并且array中元素不能为null");
     *
     * @param array   需要验证的数组
     * @param message 如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果数组array对象包含{@code null}元素
     */
    public static void noNullElements(Object[] array, String message) {
        try {
            Assert.noNullElements(array, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供一个非空元素的集合
     * 集合collection不能为{@code null}并且至少包含一个元素(元素可以为null)
     * 例:AssertBizException.notEmpty(collection,"collection不能为null,并且collection中必须含有元素");
     *
     * @param collection 需要检测的集合
     * @param message    如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果集合collection对象为{@code null}或者没有任何元素
     */
    public static void notEmpty(Collection<?> collection, String message) {
        try {
            Assert.notEmpty(collection, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 提供一个非空映射
     * 映射map不能为{@code null}并且至少包含一个entry,同时entry的key和value均不能为null
     * Note:如果entry的key或value为null,会抛出NullPointerException
     * 例:AssertBizException.notEmpty(map,"map不能为null,并且map中必须含有至少一组非null键值对");
     *
     * @param map     需要检测的映射
     * @param message 如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果映射为{@code null}或者不含entry
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        try {
            Assert.notEmpty(map, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 检测type是obj的一个实例对象
     * 例:AssertBizException.isInstanceOf(Foo.class, foo, "Foo expected");
     *
     * @param type    需要检测的字节码
     * @param obj     需要比较的对象
     * @param message 如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果type不是obj的实例
     */
    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        try {
            Assert.isInstanceOf(type, obj, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException 检测type是obj的一个实例对象
     * 例:AssertBizException.isInstanceOf(Foo.class, foo);
     *
     * @param type 需要检测的单元
     * @param obj  需要比较的对象
     * @throws BizException 如果type不是obj的实例
     */
    public static void isInstanceOf(Class<?> type, Object obj) {
        try {
            Assert.isInstanceOf(type, obj);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * 例:Assert.isAssignable(Number.class, myClass, "Number expected");
     *
     * @param superType 需要检测的父类型
     * @param subType   需要检测的子类型
     * @param message   如果断言结果不成立,抛出去的异常信息提示
     * @throws BizException 如果subType不是superType指定的单元
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        try {
            Assert.isAssignable(superType, subType, message);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }

    /**
     * AssertBizException that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * 例:Assert.isAssignable(Number.class, myClass, "Number expected");
     *
     * @param superType 需要检测的父类型
     * @param subType   需要检测的子类型
     * @throws BizException 如果subType不是superType指定的单元
     */
    public static void isAssignable(Class<?> superType, Class<?> subType) {
        try {
            Assert.isAssignable(superType, subType);
        } catch (Exception e) {
            throw new BizException(e.getMessage(), e);
        }
    }
}
