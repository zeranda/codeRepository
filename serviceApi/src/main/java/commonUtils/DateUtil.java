package commonUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author doye
 * @date 2018/7/18 10:08
 */
public class DateUtil {

    public static String[] allFormatStrings = new String[]{"yyyy-MM-dd HH:ss", "yyyyMMddHHss", "yyyy-MM-dd HHss",
            "yyyy-MM-dd", "yyyyMMddHHmm"};

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String YEAR_TO_DAY = "yyyy-MM-dd";

    public static final String YEAR_TO_DAY_WITH_POINT = "yyyy.MM.dd";

    public static final String MONTH_TO_MINUTE_WITH_WORD = "MM月dd日 HH:mm";
    /**
     * 24小时制，精确到秒
     */
    public static final String YEAR_TO_SEC = "yyyy-MM-dd HH:mm:ss";

    /**
     * 24小时制，精确到毫秒
     */
    public static final String YEAR_TO_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 24小时制，精确到分
     */
    public static final String YEAR_TO_MINUTE = "yyyy-MM-dd HH:mm";

    public static final String MONTH_TO_MINUTE = "MM-dd HH:mm";

    public static final String MONTH_TO_DAY = "MM-dd";

    public static final String HOUR_TO_MIN = "HH:mm";

    public static final String HOUR_TO_SEC = "HH:mm:ss";

    public static final String YEAR_TO_MS_UN_LINE = "yyyyMMdd HHmmssSSS";

    public static final String YEAR_TO_SEC_UN_LINE = "yyyyMMdd HHmmss";

    public static final String YEAR_TO_MI_UN_LINE = "yyyyMMdd HHmm";

    public static final String YEAR_TO_DAY_UN_LINE = "yyyyMMdd";

    public static final String YEAR_TO_MS_NO_BLANK = "yyyyMMddHHmmssSSS";

    public static final String YEAR_TO_SEC_NO_BLANK = "yyyyMMddHHmmss";

    public static final String YEAR_TO_MI_NO_BLANK = "yyyyMMddHHmm";

    public static final String DAY_TO_MINUTE = "dd HH:mm";

    public static final String DAY = "dd";

    public static final String CST_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static final String CHINESE_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
    /**
     * 系统和数据时间差
     */
    private static long SYSTEM_DIFF_TIME = 0;

    private static final String YEAR_TO_DAY_SLASH = "yyyy/MM/dd";

    private static final String YEAR_TO_DAY_TIME_SLASH = "yyyy/MM/dd HH:mm:ss.SSS";

    /**
     * Mon Apr 17 09:10:15 CST 2017 转为指定格式
     *
     * @param cstStr CST格式字符串 Mon Apr 17 09:10:15 CST 2017
     * @param format 指定格式 如 "yyyy/MM/dd"
     * @return 指定格式的时间字符串
     * @author doye
     * @date 15:02 2018/7/19
     */
    public static String formatCSTStr(String cstStr, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(CST_FORMAT, Locale.US);
        Date date = (Date) sdf.parse(cstStr);
        return formatDate(date, format);
    }

    /**
     * @param date 日期
     * @return String(返回值说明)
     * @author soarin 2013-6-5
     */
    public static String formatDate(Date date, String format) {
        if (null == date) {
            return "";
        }
        java.text.DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 日期转换成 dd HH:mm 字符
     *
     * @param date 日期
     * @return String(返回值说明)
     * @author soarin 2014-7-8
     */
    public static String getDayToMin(final Date date) {
        if (null == date) {
            return "";
        }
        return format(date, DAY_TO_MINUTE);
    }

    /**
     * yyyyMMdd格式字符转换到时间
     *
     * @param date 日期
     * @return String(返回值说明)
     * @author soarin 2013-6-5
     */
    public static String getYearToDayUnLine(Date date) {
        if (null == date) {
            return "";
        }
        return format(date, YEAR_TO_DAY_UN_LINE);
    }

    /**
     * 将yyyyMMdd HHmm格式字符转换为日期
     *
     * @param dateStr yyyMMdd HHmm格式字符
     * @return Date(返回值说明)
     * @author soarin 2013-6-5
     */
    public static Date parseYearToMinUnLine(String dateStr) {
        return parseDate(dateStr, YEAR_TO_MI_UN_LINE);
    }

    /**
     * 将字符串HHmm转换为离参照时间最近的时间 如参照时间为9月30日14点，传入1150,则返回9月30日11点50分
     * 如参照时间为9月30日0点10分，传入2350,则返回9月29日23点50分
     * 如参照时间为9月29日23点50分，传入0010,则返回9月30日00点10分
     *
     * @param refTm   参照时间
     * @param hourMin 'HHmm'格式
     * @return Date(返回值说明)
     * @author soarin 2014-3-5
     */
    public static Date getLatestDateFromHourMinUnLine(Date refTm, String hourMin) {
        String strCurrentDt = DateUtil.getYearToDayUnLine(refTm);
        strCurrentDt += " ";
        strCurrentDt += hourMin;
        Date newTm = DateUtil.parseYearToMinUnLine(strCurrentDt);
        // 往前一天
        Date newTmPre = DateUtil.addDate(newTm, -1);
        // 往后一天
        Date newTmAfter = DateUtil.addDate(newTm, 1);
        Date finalTm;
        // 取和参照时间最近的时间
        if (Math.abs(DateUtil.diffMinute(refTm, newTm)) > Math.abs(DateUtil.diffMinute(refTm, newTmPre))) {
            finalTm = newTmPre;
        } else {
            finalTm = newTm;
        }
        if (Math.abs(DateUtil.diffMinute(refTm, finalTm)) > Math.abs(DateUtil.diffMinute(refTm, newTmAfter))) {
            finalTm = newTmAfter;
        }

        return finalTm;
    }

    /**
     * 将字符串HHMM转换为离当前时刻最近的时间 如当前时间为9月30日14点，传入1150,则返回9月30日11点50分
     * 如当前时间为9月30日0点10分，传入2350,则返回9月29日23点50分
     * 如当前时间为9月29日23点50分，传入0010,则返回9月30日00点10分
     *
     * @param hourMin 'HHMM'格式
     * @return Date(返回值说明)
     * @author soarin 2013-9-30
     */
    public static Date getLatestDateFromHourMinUnLine(String hourMin) {
        Date currentTm = DateUtil.getSystemTm();

        String strCurrentDt = DateUtil.getYearToDayUnLine(currentTm);
        strCurrentDt += " ";
        strCurrentDt += hourMin;

        Date newTm = DateUtil.parseYearToMinUnLine(strCurrentDt);
        // 往前一天
        Date newTmPre = DateUtil.addDate(newTm, -1);
        // 往后一天
        Date newTmAfter = DateUtil.addDate(newTm, 1);

        Date finalTm;
        // 取和当前时间最近的时间
        if (Math.abs(DateUtil.diffMinute(currentTm, newTm)) > Math.abs(DateUtil.diffMinute(currentTm, newTmPre))) {
            finalTm = newTmPre;
        } else {
            finalTm = newTm;
        }

        if (Math.abs(DateUtil.diffMinute(currentTm, finalTm)) > Math.abs(DateUtil.diffMinute(currentTm, newTmAfter))) {
            finalTm = newTmAfter;
        }

        return finalTm;
    }

    /**
     * 字符串日期转换到Date类型
     * Examples:
     *
     * @param dateStr yyyy-MM-dd格式的字符串
     * @return 日期类型
     * @author soarin 2013-1-12
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, YEAR_TO_DAY);
    }


    /**
     * 转换DATE为yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     */
    public static String getDateTimeSec(Date date) {
        if (null == date) {
            return "";
        }
        return format(date, YEAR_TO_SEC);
    }

    /**
     * 转换DATE为yyyy年MM月dd日 HH:mm:ss格式
     * @param date
     * @return
     */
    public static String getDateChsTimeSec(Date date) {
        if (null == date) {
            return "";
        }
        return format(date, CHINESE_FORMAT);
    }


    /**
     * 方法作用说明
     * Examples: 列举一些调用的例子
     *
     * @param dateStr 日期字符串
     * @param format  格式
     * @return 字符串
     * @author soarin 2013-1-12
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            java.text.DateFormat df = new SimpleDateFormat(format);
            return (Date) df.parse(dateStr);
        } catch (Exception e) {
            logger.error("parse Date error: %s|%s", dateStr, format);
        }
        return null;
    }

    /**
     * 方法作用说明
     * Examples: 列举一些调用的例子
     *
     * @param dateStr 日期字符串
     * @param format  格式
     * @return 字符串
     * @author soarin 2013-1-12
     */
    public static Date parseDateStr(String dateStr, String format) throws ParseException {
        java.text.DateFormat df = new SimpleDateFormat(format);
        return (Date) df.parse(dateStr);
    }

    /**
     * 方法作用说明 按format格式将时间转换成字符串 Examples: 列举一些调用的例子
     *
     * @param date   日期
     * @param format 格式化形式
     * @return String 时间格式的字符串
     * @author soarin 2013-1-12
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                java.text.DateFormat df = new SimpleDateFormat(format);
                result = df.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 把包含日期值转换为字符串
     *
     * @param date 日期（日期+时间）
     * @param type 输出类型
     * @return 字符串
     */
    public static String dateTimeToString(Date date, String type) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatDate = new SimpleDateFormat(type, Locale.getDefault());
        return formatDate.format(date);
    }

    /**
     * 把包含日期值转换为字符串
     *
     * @param dateObj（日期+时间）
     * @param type           输出类型
     * @return 字符串
     */
    public static String dateTimeToString(Object dateObj, String type) {
        String DateString = "";
        if (dateObj == null) {
            DateString = "";
        } else {
            SimpleDateFormat formatDate = new SimpleDateFormat(type, Locale.getDefault());
            DateString = formatDate.format(dateObj);
        }
        return DateString;
    }

    /**
     * 将指定格式的日期/时间字符串转换成Date格式
     *
     * @param strDate   String类型，日期字符
     * @param strFormat String类型，格式
     * @return Date类型
     */
    public static Date stringToUtilDate(String strDate, String strFormat) {
        try {
            if (strDate == null || "".equals(strDate)) {
                return null;
            } else {
                SimpleDateFormat formatDate = new SimpleDateFormat(strFormat, Locale.getDefault());
                Date date = new Date((formatDate.parse(strDate)).getTime());
                return date;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期转换成strFormat的转换
     *
     * @param startDate 要转换的日期
     * @param strFormat 要转换的格式
     * @return date对象
     */
    public static Date dateToFormat(Date startDate, String strFormat) {
        try {
            if (startDate != null && !"".equals(strFormat)) {
                String date = dateTimeToString(startDate, strFormat);
                return stringToUtilDate(date, strFormat);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return startDate;
    }

    /**
     * 根据日期Date计算星期
     *
     * @param date 日期
     * @return 返回星期
     */
    public static String getWeek(Date date) {
        String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return weekDaysName[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取日期是星期几，0星期天，1是星期一，如此类推
     *
     * @param date
     * @return int
     */
    public static int getWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }


    /**
     * 返回年份
     *
     * @param date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 返回月份
     *
     * @param date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static String getMonth(int month) {
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        return months[month - 1];
    }

    public static String getMonth(String month) {
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        for (int i = 1; i <= months.length; i++) {
            if (months[i - 1].equals(month)) {
                if (i < 10) {
                    return "0" + i;
                } else {
                    return "" + i;
                }
            }
        }
        return null;
    }

    /**
     * 返回日份
     *
     * @param date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    /**
     * 返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 日期相加
     *
     * @param date 日期
     * @param day  天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 增加小时
     *
     * @param date 原来的时间
     * @param hour 小时
     * @return Date 新的时间
     */
    public static Date addHour(Date date, double hour) {
        if (date == null) {
            return null;
        }
        return addHour(date, (int) hour);
    }

    public static Date addHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hour);
        return c.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minute);
        return c.getTime();
    }

    /**
     * 日期增加秒 方法作用说明
     * Examples: 列举一些调用的例子
     *
     * @param date   日期
     * @param second 秒
     * @return 返回值说明
     */
    public static Date addSecond(Date date, double second) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, (int) (second));
        return c.getTime();
    }


    /**
     * 日期相减
     *
     * @param date 日期
     * @param day  天数
     * @return 返回相减后的日期
     */
    public static Date diffDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) - ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 日期相减
     *
     * @param date  日期
     * @param date1 日期
     * @return 返回相减后相差的天数
     */
    public static long diffDate(Date date, Date date1) {
        return ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 日期相减
     *
     * @param date  日期
     * @param date1 日期
     * @return 返回相减后相差的小时数
     */
    public static long diffHour(Date date, Date date1) {
        return ((getMillis(date) - getMillis(date1)) / (3600 * 1000));
    }


    /**
     * 日期相减
     *
     * @param date  日期
     * @param date1 日期
     * @return 返回相减后相差的分钟数
     */
    public static long diffSenconds(Date date, Date date1) {
        return (getMillis(date) - getMillis(date1)) / (60 * 1000);
    }

    /**
     * 日期相减,返回相减后的毫秒数 Examples:(列举一些调用的例子)
     *
     * @param date  日期
     * @param date1 日期
     * @return int相减后的毫秒数
     */
    public static long diffMillis(Date date, Date date1) {
        return getMillis(date) - getMillis(date1);
    }


    /**
     * 取得指定月份的第一天
     *
     * @param strDate String
     * @return String
     */
    public static String getMonthBegin(String strDate) {
        Date date = parseDate(strDate);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 取得指定月份的最后一天
     *
     * @param strDate String
     * @return String
     */
    public static String getMonthEnd(String strDate) {
        Date date = parseDate(getMonthBegin(strDate));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 常用的格式化日期
     *
     * @param date Date
     * @return String
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return "";
        }
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 以指定的格式来格式化日期
     *
     * @param date   Date
     * @param format String
     * @return String
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 去掉Date里面的时分秒
     *
     * @param date 日期
     * @return 去掉时分秒后的Date，若参数为空则返回原日期
     */
    public static Date trimHmsForDate(Date date) {
        Date result = date;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);
            result = cal.getTime();
        }
        return result;
    }

    /**
     * 判断两个时间是否在同一天
     *
     * @param d1 时间
     * @param d2 时间
     * @return boolean(返回值说明)
     */
    public static boolean bSameDay(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return DateUtil.formatDate(d1).equals(DateUtil.formatDate(d2));
    }

    /**
     * 以当前日期为参照，根据偏移量，获取日期
     *
     * @param dOffset 日期偏移量(以天为单位)
     * @return 日期
     */
    public static Date getDateByOffset(int dOffset) {
        return getDateByOffset(getSystemTm(), dOffset);
    }

    /**
     * 根据偏移量，获取日期
     *
     * @param dOffset 日期偏移量(以天为单位)
     * @return 日期
     */
    public static Date getDateByOffset(Date d, int dOffset) {
        if (null == d) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, dOffset);
        return c.getTime();
    }

    /**
     * 以当前日期为参照，根据d/h/m移量，获取日期
     *
     * @param dOffset 日期偏移量(以天为单位)
     * @param hOffset 小时偏移量
     * @param mOffset 分钟偏移量
     * @return 日期
     */
    public static Date getDateByOffset(int dOffset, int hOffset, int mOffset) {
        return getDateByOffset(getSystemTm(), dOffset, hOffset, mOffset);
    }

    /**
     * 根据d/h/m移量，获取日期
     *
     * @param dOffset 日期偏移量(以天为单位)
     * @param hOffset 小时偏移量
     * @param mOffset 分钟偏移量
     * @return 日期
     */
    public static Date getDateByOffset(Date d, int dOffset, int hOffset, int mOffset) {
        if (null == d) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, dOffset);
        c.add(Calendar.HOUR, hOffset);
        c.add(Calendar.MINUTE, mOffset);
        return c.getTime();
    }

    /**
     * 比较两个日期(第二个日期减去第一个日期)，返回以分钟为最小单位的，最大以小时显示的字符窜(如：36H28 表示36小时28分)
     *
     * @param firstDt  第一个日期
     * @param secondDt 第二个日期
     * @return 字符串
     */
    public static String compareDate(Date firstDt, Date secondDt) {
        if (null == firstDt || null == secondDt) {
            return "";
        }
        long diff = (secondDt.getTime() - firstDt.getTime()) / (60 * 1000);
        return formatHourAndMinByMin(diff);
    }

    /**
     * 根据分钟数格式化成小时和分钟字符窜(如：200 -> 3H20)
     *
     * @param minute 分钟数格式
     * @return 字符串
     */
    public static String formatHourAndMinByMin(long minute) {
        StringBuilder sb = new StringBuilder();
        if (minute < 0) {
            sb.append("-");
        }
        long m = Math.abs(minute % 60);
        long h = Math.abs(minute / 60);
        if (h > 0) {
            sb.append(h);
            sb.append("H");
        }
        if (m != 0) {
            sb.append(m);
        }
        return sb.toString();
    }

    /**
     * 比较两个日期(第二个日期减去第一个日期)，间隔2小时以上以分钟为单位，间隔2小时以上以小时+分钟格式显示的字符窜(如：36H28
     * 表示36小时28分，91表示1小时31分钟)
     *
     * @param firstDt  第一个日期
     * @param secondDt 第二个日期
     * @return 字符串
     */
    public static String compareDateTransfer(Date firstDt, Date secondDt) {
        if (null == firstDt || null == secondDt) {
            return "";
        }
        long diff = (secondDt.getTime() - firstDt.getTime()) / (60 * 1000);
        return formatHourAndMinByMinTransfer(diff);
    }

    /**
     * 2小时及以下直接显示分钟数，2小时以上根据分钟数格式化成小时和分钟字符窜(如：200 -> 3H20,91 -> 91)
     *
     * @param minute 分钟数
     * @return 字符串
     */
    public static String formatHourAndMinByMinTransfer(long minute) {
        StringBuilder sb = new StringBuilder();
        if (minute < 0) {
            sb.append("-");
        }
        if (minute >= 0 && minute <= 120) {
            sb.append(minute);
        } else {
            long m = Math.abs(minute % 60);
            long h = Math.abs(minute / 60);
            if (h > 0) {
                sb.append(h);
                sb.append("H");
            }
            if (m != 0) {
                sb.append(m);
            }
        }
        return sb.toString();
    }

    /**
     * 两个时间差距多少秒
     *
     * @return long
     */
    public static long getDateMinus(Date d1, Date d2) {
        long v = d1.getTime() - d2.getTime();
        if (v < 0) {
            v = -v;
        }
        return v;
    }

    /**
     * 时间减去多少分
     *
     * @param date 日期
     * @param min  减去的分钟数
     * @return 日期
     */
    public static Date deffDate(Date date, String min) {
        if (date == null || min == null || "".equals(min)) {
            return null;
        }
        try {
            int m = Integer.parseInt(min);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.setTimeInMillis(c.getTimeInMillis() - ((long) m) * 60 * 1000);
            return c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期相减
     *
     * @param date  日期
     * @param date1 日期
     * @return 返回相减后的分钟数
     */
    public static int diffMinute(Date date, Date date1) {
        if (date == null || date1 == null) {
            return 0;
        }
        return (int) ((getMillis(date) - getMillis(date1)) / (60 * 1000));
    }

    /**
     * 转换2013-01-18 00:00:0 字符日期格式为2013-01-18字符日期格式 百年之后这个函数就不对了。 by soarin
     */
    public static String transDateStrToStr(String fltDtStr) {
        String fltDt = "";
        if (fltDtStr != null && !"".equals(fltDtStr)) {
            String[] fltDtArr = fltDtStr.split(" ");
            fltDt = fltDtArr[0];
        }
        return fltDt;
    }

    /**
     * 两个时间是否相等
     *
     * @param d1 时间
     * @param d2 时间
     * @return boolean(返回值说明)
     * @author soarin 2013-12-6
     */
    public static boolean bEqual(Date d1, Date d2) {
        if (null == d1) {
            return null == d2;
        } else {
            if (null == d2) {
                return false;
            }
            return d1.compareTo(d2) == 0;
        }
    }

    public static void setSystemDiffTime(long diffTime) {
        SYSTEM_DIFF_TIME = diffTime;
    }

    /**
     * 获取系统时间
     *
     * @return Date(返回值说明)
     * @author soarin 2013-5-13
     */
    public static Date getSystemTm() {
        return new Date(System.currentTimeMillis() + SYSTEM_DIFF_TIME);
    }

    /**
     * 以指定的格式把Date转换成String（Locale为English)
     *
     * @param date   日期
     * @param format 格式
     * @return String
     * @since 2013-6-9
     */
    public static String formatDateByFormatWithEnglishLocale(Date date, String format) {
        String result = "";
        if (date != null && StringUtils.isNotBlank(format)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 以指定的格式把String转换成Date（Locale为English)
     *
     * @param dateStr 日期字符串
     * @param format  格式
     * @return Date
     * @since 2013-6-9
     */
    public static Date parseDateByFormatWithEnglishLocale(String dateStr, String format) {
        Date result = null;
        if (StringUtils.isNotBlank(dateStr) && StringUtils.isNotBlank(format)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                result = (Date) sdf.parse(dateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 日期验证
     * 因为parseDate方法当dateStr不是日期格式时，也会返回一个错误的日期，不满足要求，所以新增日期验证方法
     *
     * @param dateStr yyyyMMddHHmmss或者yyyyMMdd 格式字符串
     * @return 是日期，返回true
     * @author John 2013-12-17
     */
    public static boolean isValidDate(String dateStr) {
        // String eL =
        // "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))((((0?[0-9])|([1][0-9])|([2][0-3]))([0-5]?[0-9])((\\s)|(([0-5]?[0-9])))))?$";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(dateStr);
        return m.matches();
    }

    /**
     * 设置日期偏移量
     *
     * @param d      所要偏移的日期
     * @param field  字段(例如:Calendar.MINUTE,Calendar.HOUR),field the calendar
     *               field.注意：此参数必须与Calendar类定义的常量对应，否则会有错误
     * @param amount 偏移量 the amount of date or time to be added to the field.
     * @return Date(返回值说明)
     * @author luxinming 2013-11-20
     */
    public static Date setDateByOffset(Date d, int field, int amount) {
        if (null == d) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * 获取2个时间的差值，并格式化成xxdxxmxxs 扣除整数天后，如果时间小于等于二小时以内，则格式化成xxdxxm
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 字符串
     * @author liyongsen
     * @date 2014-07-29
     */
    public static String getConnectTimeStr(Date startDate, Date endDate) {
        if (null == startDate || null == endDate) {
            return null;
        }
        long diffTime = diffMinute(endDate, startDate);
        return timeFormat((diffTime * 60 * 1000));
    }

    /**
     * 把时间转成字符串xxdxxmxxs 扣除整数天后，如果时间小于等于二小时以内，则格式化成xxdxxm
     *
     * @param time 时间
     * @return String 字符串形式的时间
     * @author liyongsen
     * @date 2014-07-29
     */
    private static String timeFormat(long time) {
        StringBuilder buffer = new StringBuilder();
        // 计算天数
        long day = time / (24 * 60 * 60 * 1000);
        long hour;
        long min;
        if (time < 0) {
            time = time * -1;
            // 负数
            buffer.append("-");
        }
        if (day > 0) {
            // 剩下多少小时
            time = time - day * (24 * 60 * 60 * 1000);
            buffer.append(day).append("d");
        }
        // 计算小时
        hour = time / (60 * 60 * 1000);
        if (hour >= 0) {
            // 如果时间小于等于二小时以内
            if (time <= (2 * 60 * 60 * 1000)) {
                long minTemp = time / (60 * 1000);
                buffer.append(minTemp).append("m");
            } else {
                // 剩下多少小时
                time = time - hour * 60 * 60 * 1000;
                buffer.append(hour).append("h");
                // 剩下多少分钟
                min = time / (60 * 1000);
                buffer.append(min).append("m");
            }
        }
        return buffer.toString();
    }

    /**
     * 输入11NOV日期格式，根据当前系统时间判断，返回最近的yyyy/MM/dd格式的日期
     *
     * @param date 日期
     * @return Date(返回值说明)
     * @author ljxu 2014-12-14
     */
    public static Date chgHostMothDay2Common(String date) {
        String hostDateUpper = date.toUpperCase();
        String monName = hostDateUpper.substring(2, 5);
        String month = getMonth(monName);
        if (null == month) {
            logger.info("待转换日期错误： " + date);
            return null;
        }
        String day = hostDateUpper.substring(0, 2);
        String monthDay = "/" + month + "/" + day;

        Date currentTm = DateUtil.getSystemTm();
        String strCurrentDt = DateUtil.getYearToDayWithSlash(currentTm);

        Date currentDt = DateUtil.parseDateWithSlash(strCurrentDt);
        Calendar calendar = Calendar.getInstance();

        int currentYear = calendar.get(Calendar.YEAR);

        int nextYear = currentYear + 1;

        int preYear = currentYear - 1;

        Date preTm = DateUtil.parseDateWithSlash(String.valueOf(preYear) + monthDay);

        Date nextTm = DateUtil.parseDateWithSlash(String.valueOf(nextYear) + monthDay);

        Date nowTm = DateUtil.parseDateWithSlash(String.valueOf(currentYear) + monthDay);

        if (preTm == null || nextTm == null || nowTm == null) {
            logger.error("待转换日期错误： " + date);
            return null;
        }
        Date finalTm;

        // 取和当前时间最近的时间
        if (Math.abs(currentDt.getTime() - preTm.getTime()) > Math.abs(currentDt.getTime() - nextTm.getTime())) {
            finalTm = nextTm;
        } else {
            finalTm = preTm;
        }

        if (Math.abs(currentDt.getTime() - finalTm.getTime()) > (Math.abs(currentDt.getTime() - nowTm.getTime()))) {
            finalTm = nowTm;
        }
        return finalTm;
    }

    /**
     * 0200日期格式，根据当前系统时间判断 ，返回最近的yyyy/MM/dd HH:mm:SS.SSS格式的日期
     *
     * @param date 日期
     * @param time 时间
     * @return Date(返回值说明)
     * @author ljxu 2014-12-15
     */
    public static Date chgHostMothDay2CommonTime(String date, String time) {
        Date nowDate = chgHostMothDay2Common(date);
        String hour = time.substring(0, 2);
        String min = time.substring(2, 4);
        String sec = "00";
        String mills = "000";
        StringBuffer nowTm = new StringBuffer();
        nowTm.append(hour + ":");
        nowTm.append(min + ":");
        nowTm.append(sec + "." + mills);
        String nowTmStr = getYearToDayWithSlash(nowDate) + " " + nowTm.toString();
        return parseDateTimeWithSlash(nowTmStr);
    }

    /**
     * yyyy/MM/dd格式字符转换到时间
     *
     * @param date 日期
     * @return String(返回值说明)
     * @author soarin 2013-6-5
     */
    public static String getYearToDayWithSlash(Date date) {
        if (null == date) {
            return "";
        }
        return format(date, YEAR_TO_DAY_SLASH);
    }

    /**
     * 将yyyy/MM/dd转换成日期格式
     *
     * @param date 日期
     * @return (设定参数)
     * @author ljxu 2014-12-14
     */
    public static Date parseDateWithSlash(String date) {
        return parseDate(date, YEAR_TO_DAY_SLASH);
    }

    /**
     * 将yyyy/MM/dd HH:mm:ss.SSS转换成日期格式
     *
     * @param date 日期
     * @return Date(返回值说明)
     */
    public static Date parseDateTimeWithSlash(String date) {
        return parseDate(date, YEAR_TO_DAY_TIME_SLASH);
    }

    /**
     * 输入11NOV0200日期格式，根据当前系统时间判断 ，返回最近的yyyy/MM/dd
     * HH:mm:SS.SSS格式的日期
     *
     * @param date 日期
     * @return Date(返回值说明)
     */
    public static Date chgMothDay2CommonTime(String date) {
        String currentMothDay = date.substring(0, 5);
        String currentTime = date.substring(5);
        String hour = currentTime.substring(0, 2);
        String min = currentTime.substring(2, 4);
        String sec = "00";
        String mills = "000";
        StringBuffer nowTm = new StringBuffer();
        nowTm.append(hour + ":");
        nowTm.append(min + ":");
        nowTm.append(sec + "." + mills);
        Date nowDate = chgHostMothDay2Common(currentMothDay);
        String nowTmStr = getYearToDayWithSlash(nowDate) + " " + nowTm;

        return parseDateTimeWithSlash(nowTmStr);
    }

    /**
     * 输入110200日期格式，根据当前系统时间判断 ，返回最近的yyyy/MM/dd HH:mm:SS.SSS格式的日期
     *
     * @param date 日期
     * @return Date(返回值说明)
     */
    public static Date parseAloneDTime2CommonDTime(String date) {
        String currentDate = date.substring(0, 2);
        String currentTime = date.substring(2);
        String hour = currentTime.substring(0, 2);
        String min = currentTime.substring(2, 4);
        String sec = "00";
        String mills = "000";
        StringBuffer aloneTm = new StringBuffer();
        aloneTm.append(hour + ":");
        aloneTm.append(min + ":");
        aloneTm.append(sec + "." + mills);
        Date aloneDate = parseAloneDate2CommonDate(currentDate);
        String nowTmStr = getYearToDayWithSlash(aloneDate) + " " + aloneTm;

        return parseDateTimeWithSlash(nowTmStr);
    }

    /**
     * 输入11日格式，根据当前系统时间判断，返回最近的yyyy/MM/dd格式的日期，当日期相距一样时，取前者，如当天为2012/2/29，输入30，得到2012/1/30而不是2012/3/30
     *
     * @param date rqi
     * @return Date(返回值说明)
     */
    public static Date parseAloneDate2CommonDate(String date) {
        Calendar finalDate;
        int d = Integer.valueOf(date);
        Calendar currentDate = Calendar.getInstance();

        if (currentDate.get(Calendar.DATE) == d) {
            finalDate = currentDate;
        } else {
            Calendar basicDate = (Calendar) currentDate.clone();
            basicDate.set(Calendar.DATE, d);
            Calendar nextDateOfBasic = (Calendar) currentDate.clone();
            nextDateOfBasic.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH) + 1, d);
            Calendar prevDateOfBasic = (Calendar) currentDate.clone();
            prevDateOfBasic.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH) - 1, d);

            if (nextDateOfBasic.get(Calendar.DATE) == d && prevDateOfBasic.get(Calendar.DATE) == d) {
                if (Math.abs(currentDate.getTimeInMillis() - prevDateOfBasic.getTimeInMillis()) > Math.abs(currentDate
                        .getTimeInMillis() - nextDateOfBasic.getTimeInMillis())) {
                    finalDate = nextDateOfBasic;
                } else {
                    finalDate = prevDateOfBasic;
                }
                if (basicDate.get(Calendar.DATE) == d
                        && (Math.abs(currentDate.getTimeInMillis() - finalDate.getTimeInMillis()) > Math
                        .abs(currentDate.getTimeInMillis() - basicDate.getTimeInMillis()))) {
                    finalDate = basicDate;
                }
            } else if (nextDateOfBasic.get(Calendar.DATE) == d) {
                if (basicDate.get(Calendar.DATE) != d
                        || (Math.abs(currentDate.getTimeInMillis() - basicDate.getTimeInMillis()) > Math
                        .abs(currentDate.getTimeInMillis() - nextDateOfBasic.getTimeInMillis()))) {
                    finalDate = nextDateOfBasic;
                } else {
                    finalDate = basicDate;
                }
            } else {
                if (basicDate.get(Calendar.DATE) != d
                        || (Math.abs(currentDate.getTimeInMillis() - basicDate.getTimeInMillis()) > Math
                        .abs(currentDate.getTimeInMillis() - prevDateOfBasic.getTimeInMillis()))) {
                    finalDate = prevDateOfBasic;
                } else {
                    finalDate = basicDate;
                }
            }
        }

        return finalDate.getTime();
    }

    /**
     * 将给定的日dd，如02 转换成离当前系统时间最近的ddMMM，如 02JAN；当前后时间相等时取较前的时间
     *
     * @param day 固定日期格式dd
     * @return String(返回值说明)
     */
    public static String transDay2DayMonth(String day) {
        if (StringUtils.isBlank(day)) {
            return null;
        }

        Date date = parseAloneDate2CommonDate(day);
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        int month = DateUtil.getMonth(date);
        return day + months[month - 1];
    }

    /**
     * 将给定的日期字符串(四种形式：dd/ddMMM/ddMMMyy/ddMMMyyyy，如05或者04MAY，或者04MAY15
     * ，或者04MAY2015) 转换成离当前日期最近的yyyyMMdd格式日期字符串
     *
     * @param date 待转换日期字符串
     * @return String yyyyMMdd格式日期字符串
     * @author fucy 2015-5-5
     */
    public static String transDate2YearMonthDay(String date) {
        if (StringUtil.isBlank(date)) {
            logger.error("待转换日期字符串错误：" + date);
            return "";
        }

        Date finalDate = null;
        if (date.length() == 2 && date.matches("\\d{2}")) {
            // dd格式
            finalDate = parseAloneDate2CommonDate(date);
        } else if (date.length() == 5 && date.matches("\\d{2}[A-Z]{3}")) {
            // ddMMM格式
            finalDate = chgHostMothDay2Common(date);
        } else if (date.length() == 7 && date.matches("\\d{2}[A-Z]{3}\\d{2}")) {
            // ddMMMyy格式
            String year = String.valueOf(getYear(new Date())).substring(0, 2) + date.substring(5);
            return year + getMonth(date.substring(2, 5)) + date.substring(0, 2);
        } else if (date.length() == 9 && date.matches("\\d{2}[A-Z]{3}\\d{4}")) {
            // ddMMMyyyy格式
            return date.substring(5) + getMonth(date.substring(2, 5)) + date.substring(0, 2);
        }
        return getYearToDayUnLine(finalDate);
    }


    /**
     * 将时间字符串转为date 如果传入的格式字符串为 yyyy-MM-dd HH:mm 则自动补全为 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @param second
     * @return
     */
    public static Date getDateToSec(String dateStr, String second) {
        //如果是只到分就自动补全
        if (dateStr != null && StringUtil.isNotBlankTrim(second) &&
                dateStr.length() == DateUtil.YEAR_TO_MINUTE.length()) {
            dateStr = dateStr + ":" + second;
        }
        return parseDate(dateStr, DateUtil.YEAR_TO_SEC);
    }

    /**
     * yyyy-MM-dd ->yyyy-MM-dd 00:00:00  ||  yyyy-MM-dd ->yyyy-MM-dd 23:59:59
     *
     * @param dateStr yyyy-MM-dd
     * @param type    0->yyyy-MM-dd 00:00:00   1->yyyy-MM-dd 23:59:59
     * @return
     */
    public static Date getDateToSec(String dateStr, Integer type) {
        if (null != dateStr && DateUtil.YEAR_TO_DAY.length() == dateStr.length()) {
            if (type == 0) {
                dateStr = dateStr + " 00:00:00";
                return parseDate(dateStr, DateUtil.YEAR_TO_SEC);
            }
            dateStr = dateStr + " 23:59:59";
            return parseDate(dateStr, DateUtil.YEAR_TO_SEC);
        }
        return null;
    }


    /**
     * 时间设置为23:59:59
     *
     * @param date
     * @return
     */
    public static Date setDayEnd(Date date) {
        date = DateUtils.setHours(date, 23);
        date = DateUtils.setMinutes(date, 59);
        date = DateUtils.setSeconds(date, 59);
        date = DateUtils.setMilliseconds(date, 0);
        return date;
    }

    /**
     * 时间设置为0:0:0
     *
     * @param date
     * @return
     */
    public static Date setDayBegin(Date date) {
        date = DateUtils.setHours(date, 0);
        date = DateUtils.setMinutes(date, 0);
        date = DateUtils.setSeconds(date, 0);
        date = DateUtils.setMilliseconds(date, 0);
        return date;
    }

    /**
     * 当月1号0:0:0
     *
     * @param date
     * @return
     */
    public static Date setMonthBegin(Date date) {
        date = DateUtils.setDays(date, 1);
        return setDayBegin(date);
    }

    /**
     * 当月最后一天
     *
     * @param date
     * @return
     */
    public static Date setMonthEnd(Date date) {
        date = DateUtils.addMonths(date, 1);
        date = setMonthBegin(date);
        return DateUtils.addSeconds(date, -1);
    }

    /**
     * 获取当年年初时间
     */
    public static Date getThisYearStart() {
        int year = getYear(new Date());
        String dateStr = year + "-01" + "-01";
        return parseDate(dateStr);
    }

    /**
     * 获取去年年初时间
     */
    public static Date getOtherYearStart(int number) {
        int year = getYear(new Date()) - number;
        String dateStr = year + "-01" + "-01";
        return parseDate(dateStr);
    }

    /**
     * 获取当年年尾时间
     */
    public static Date getThisYearEnd() {
        int year = getYear(new Date());
        String dateStr = year + "-12" + "-31";
        Date date = parseDate(dateStr);
        return setDayEnd(date);
    }

    /**
     * 获取几周前的时间
     */
    public static Date getWeek(Integer number) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -number);
        return c.getTime();
    }

    /**
     * 获取2周前的时间
     */
    public static Date get2Week() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        return c.getTime();
    }

    /**
     * 获取4周前的时间
     */
    public static Date get4Week() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        return c.getTime();
    }

    /**
     * 获取8周前的时间
     */
    public static Date get8Week() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        return c.getTime();
    }

    /**
     * 获取24周前的时间
     */
    public static Date get24Week() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        return c.getTime();
    }

    /**
     * 获取半年前的时间
     */
    public static Date getHalfYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -6);
        return c.getTime();
    }

    /**
     * 获取一年前的时间
     */
    public static Date getOneYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        return c.getTime();
    }


    private static Calendar getMondayFirst() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        return cal;
    }

    /**
     * 获取一周中的周几
     * 按原定义的返回，sunday=1,monday=2...saturday=7
     * @return
     */
    private static Integer getDayWeek() {
        Calendar cal = getMondayFirst();
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        return (dayWeek == 1) ? 8 : dayWeek;
    }

    /**
     * 获取一周中的周几
     * 经过转义，按本土习惯 monday=1...saturday=6,sunday=7
     * @return
     */
    public static Integer getDayWeek(Date date) {
        Calendar cal = getMondayFirst();
        cal.setTime(date);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        return (dayWeek == 1) ? 7 : dayWeek - 1;
    }

    /**
     * 获得当前日期的周一那天的时间
     * @return
     */
    public static Date getMonday() {
        Calendar cal = getMondayFirst();
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - getDayWeek());// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        return cal.getTime();
    }

    /**
     * 获得当前日期的周日那天的时间
     * @return
     */
    public static Date getSunday() {
        return addDate(getMonday(), 6);
    }

    /**
     * 仅比较时间大小
     *
     * @param dt1
     * @param dt2
     * @return
     */
    /*public static int compareTime(Date dt1, Date dt2) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String ds1 = format.format(dt1);
        String ds2 = format.format(dt2);
        dt1 = format.parse(ds1);
        dt2 = format.parse(ds2);
        return dt1.compareTo(dt2);
    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = format.parse("2019-04-12 18:36:55");
        Date dt1 = format.parse("2019-04-12 18:36:55");
        Date dt2 = format.parse("2019-04-12 17:36:55");
        isEffectiveDate(new Date(),dt1, dt2);
    }

    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }*/

}
