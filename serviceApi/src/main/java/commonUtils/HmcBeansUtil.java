package commonUtils;

import base.PageList;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO与数据库model相互转化工具类
 * <p>
 * Created by huangyuheng on 2017/12/1.
 */
public class HmcBeansUtil {

    /**
     * 将sourceClazz转化成targetClazz，通过BeanUtils复制
     *
     * @param sourceObject
     * @param targetClazz
     * @param <T>
     * @return
     * @author huangyuheng 20171201
     */
    public static <T> T convertClazzObject(Object sourceObject, Class<T> targetClazz) {

        if (null == sourceObject) {
            return null;
        }

        T targetObject = newClass(targetClazz);
        BeanUtils.copyProperties(sourceObject, targetObject);

        return targetObject;
    }

    /**
     * 将sourceClazz List转化成targetClazz List，通过BeanUtils复制
     *
     * @param sourceList
     * @param clazz
     * @param <S>
     * @param <T>
     * @return
     * @author huangyuheng 20171201
     */
    public static <S, T> List<T> convertClazzObjectList(List<S> sourceList, Class<T> clazz) {
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S source : sourceList) {
            if (null != source) {
                T target = newClass(clazz);
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
            } else {
                targetList.add(null);
            }
        }

        return targetList;
    }

    /**
     * 分页设置，传入目标class，将数据类型转换成pageList
     *
     * @param clazz
     * @param sourceList
     * @param <S>
     * @param <T>
     * @return
     * @author huangyuheng 20171201
     */
    public static <S, T> PageList<T> toPageList(List<S> sourceList, Class<T> clazz) {
        List<T> targetList = convertClazzObjectList(sourceList, clazz);

        PageInfo<S> pageInfo = new PageInfo(sourceList);

        //设置分页
        PageList<T> pageList = new PageList<>();
        pageList.setPageIndex(pageInfo.getPageNum());
        pageList.setPageSize(pageInfo.getPageSize());
        pageList.setTotalCount(pageInfo.getTotal());
        pageList.setResult(targetList);

        return pageList;
    }

    /**
     * 泛型实例化class
     *
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T newClass(Class<T> clazz) {
        T target = null;
        try {
            target = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return target;
    }

}
