package commonUtils;

import exception.BizException;

import java.util.*;

/**
 * 业务工具类

 *
 * @author
 * @date
 */
public class BusinessUtil {





    /**
     * 营销系统
     * 根据日期 获取生效日期和失效日期获取活动状态
     * 0未开始，1进行中，2已失效
     *
     * @param beginDate 生效日期
     * @param endDate   失效日期
     * @return
     */
    public static int getStatusType(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            return -1;
        }
        Date today = new Date();
        if (today.before(beginDate)) {
            return 0;
        }
        if (endDate.before(today)) {
            return 2;
        }
        return 1;
    }



    /**
     * 是否过了售后期 (7天为限)
     * <p>
     * true 过售后期 false 未过售后期
     */
    public static boolean checkLateSale(Date date) {
        return checkLateSale(date, 7);
    }



    /**
     * 是否过了售后期 (7天为限)
     * <p>
     * true 过售后期 false 未过售后期
     */
    public static boolean checkLateSale(Date date, int num) {
        if (date == null) {
            return false;
        }
        Date lastDate = DateUtil.addDate(date, num);
        Date nowDate = new Date();
        if (lastDate.after(nowDate)) {
            return false;
        }
        return true;
    }

    /**
     * 根据 offset 和limit 获取起始页数
     *
     * @param offset
     * @param limit
     * @return
     */
    public static int getPage(Integer offset, Integer limit) {
        return offset / limit + 1;
    }

    /**
     * 年月日转为年月日时分秒
     *
     * @return
     */
    public static String changeToYearToSec(String date, boolean begin) {
        if (date.length() == DateUtil.YEAR_TO_SEC.length()) {
            return date;
        }
        if (date.length() == DateUtil.YEAR_TO_DAY.length()) {
            date = begin ? date + " 00:00:00" : date + " 23:59:59";
            return date;
        }
        return date;
    }



    /**
     * 把map中值为null的key的值设置为零 避免空指针
     *
     * @param map
     * @param idSet
     * @return
     */
    public static Map<Long, Integer> setNullToInteger(Map<Long, Integer> map, Collection<Long> idSet) {
        List<Long> list = ListUtil.getDifferenceList(new ArrayList<>(map.keySet()), new ArrayList<>(idSet));
        if (list.size() == 0) {
            return map;
        }
        for (Long key : list) {
            map.put(key, 0);
        }
        return map;
    }



    /**
     * 取最小值
     *
     * @param quantity1
     * @param quantity2
     * @return
     */
    public static Integer getMin(Integer quantity1, Integer quantity2) {
        return quantity1 < quantity2 ? quantity1 : quantity2;
    }



    /**
     * 获取资源状态具体描述(同一天内具体到小时)
     *
     * @param showBegin
     * @param showEnd
     * @return
     * @Author zhouji
     */
    public static String getSourceStatus(Date showBegin, Date showEnd) {
        Date currentTime = new Date();
        int day = 0;
        if (currentTime.getTime() < showBegin.getTime()) {
            day = getIntervalDays(currentTime, showBegin);
            if (day == 0) {
                Long hours = (showBegin.getTime() - currentTime.getTime()) / (1000 * 60 * 60);
                return hours + "小时后生效";
            } else {
                return day + "天后生效";
            }
        } else if (currentTime.getTime() >= showBegin.getTime() && currentTime.getTime() <= showEnd.getTime()) {
            day = getIntervalDays(currentTime, showEnd);
            if (day == 0) {
                Long hours = (showEnd.getTime() - currentTime.getTime()) / (1000 * 60 * 60);
                return hours + "小时后失效";
            } else {
                return day + "天后失效";
            }
        } else {
            day = getIntervalDays(showEnd, currentTime);
            if (day == 0) {
                Long hours = (currentTime.getTime() - showEnd.getTime()) / (1000 * 60 * 60);
                return "已失效" + hours + "小时";
            } else {
                return "已失效" + day + "天";
            }

        }

    }

    /**
     * 获取两个日期相隔天数(同一天内视为0天)
     *
     * @param date1
     * @param date2
     * @return
     * @Author zhouji
     */
    public static int getIntervalDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //不是同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //同年
        {
            return day2 - day1;
        }
    }

    /**
     * 验证基本数据
     *
     * @param businessUnitId
     * @param operator
     * @param operatorId
     */
    public static void checkBaseData(Long businessUnitId, String operator, Long operatorId) {
        AssertBizException.isTrue(null != businessUnitId && businessUnitId > 0, "businessUnitId无效");
        AssertBizException.isTrue(operatorId != null && operatorId > 0, "operatorId错误");
        AssertBizException.notNull(operator, "operator不能为空");
    }


    /**
     * 检查excel模板是否正确  并检查数据是否按照要求填写
     *
     * @param tableHeadList      传进来的excel表头
     * @param rightTableHeadList 正确的模板的excel表头
     * @param excelData          传进来的表格数据
     * @return
     */
    public static void checkExcelModel(List<Object> tableHeadList, List<String> rightTableHeadList,int effectiveTableHeadSize,
                                       List<List<Object>> excelData) {
        List<String> errorMessageList = new ArrayList<>();
        if (tableHeadList.size() != rightTableHeadList.size()) {
            //throw new BizException("缺少必要的列，请下载模板！");
            throw new BizException("OMS:模板不对，请下载最新的模板！");
        }
        for (Object o : tableHeadList) {
            if ("".equals(o.toString().trim())) {
                throw new BizException("有必要的列头名称没有填写，请检查或下载模板！");
            }
        }
        for (int i = 0, j = rightTableHeadList.size(); i < j; i++) {
            if (!rightTableHeadList.get(i).equals(tableHeadList.get(i).toString().trim())) {
                errorMessageList.add("表头第1个单元格名称错误：" + tableHeadList.get(i) + ",正确的为:" + rightTableHeadList.get(i));
            }
        }
        int rowNum = 1;
        for (List<Object> list : excelData) {
            if (list.size() != effectiveTableHeadSize && rowNum >1) {
                //errorMessageList.add("第" + rowNum + "行缺少数据或者有多余的数据，该行有单元格数据可能合并单元格（跨列）存放数据！");
                errorMessageList.add("第" + rowNum + "行填写信息有误！");
            }
            rowNum++;
        }
        if (0 != errorMessageList.size()) {
            throw new BizException(errorMessageList.toString());
        }
    }

}
