package commonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtil {

    private ListUtil() {
    }

    /**
     * 得到分页后的数据
     *
     * @return 分页后结果
     */
    public static <T> List<T> getPageList(List<T> data, int nowPage, int pageSize) {
        if (data == null) {
            return data;
        }
        int fromIndex = (nowPage - 1) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList(); // 空数组
        }
        if (fromIndex < 0) {
            return Collections.emptyList(); // 空数组
        }
        int toIndex = nowPage * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }

    /**
     * 求差集 适用于字符串和数字 如果是对象要重写equals方法
     *
     * @param sourceList 第一个集合
     * @param targetList 第二个集合
     * @return 返回两个集合的差集
     * @author doye
     * @date 18:23 2018/7/18
     */
    public static <T> List<T> getDifferenceList(List<T> sourceList, List<T> targetList) {
        List<T> tempSourceList = new ArrayList<>();
        List<T> tempTargetList = new ArrayList<>();
        tempSourceList.addAll(sourceList);
        tempTargetList.addAll(targetList);
        List<T> temp = new ArrayList<>();
        temp.addAll(sourceList);
        temp.retainAll(targetList);
        tempSourceList.removeAll(temp);
        tempTargetList.removeAll(temp);
        List<T> differenceList = new ArrayList<>();
        differenceList.addAll(tempSourceList);
        differenceList.addAll(tempTargetList);
        return differenceList;
    }

    /**
     * 求交集 适用于字符串和数字 如果是对象要重写equals方法
     *
     * @param sourceList 第一个集合
     * @param targetList 第二个集合
     * @return 返回两个集合的交集
     * @author doye
     * @date 18:23 2018/7/18
     */
    public static <T> List<T> getMixedList(List<T> sourceList, List<T> targetList) {
        List<T> temp = new ArrayList<>(sourceList);
        temp.retainAll(targetList);
        return temp;

    }

    /**
     * 求并集 适用于字符串和数字 如果是对象要重写equals方法
     *
     * @param sourceList 第一个集合
     * @param targetList 第二个集合
     * @return 返回两个集合的交集
     * @author doye
     * @date 18:23 2018/7/18
     */
    public static <T> List<T> getUnionList(List<T> sourceList, List<T> targetList) {
        List<T> differenceList = getDifferenceList(sourceList, targetList);
        List<T> mixList = getMixedList(sourceList, targetList);
        List<T> temp = new ArrayList<>();
        temp.addAll(differenceList);
        temp.addAll(mixList);
        return temp;
    }

    /**
     * 判断list是否为空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断list不是空，代码判断看的更舒服
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }

}
