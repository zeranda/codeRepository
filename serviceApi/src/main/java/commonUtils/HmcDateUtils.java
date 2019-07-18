package commonUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author: Leo
 * @Description: 全局导航类
 * @Date: 2019/4/10 14:25
 **/
public class HmcDateUtils {

    public static Date now() {
        return new Date();
    }

    public static Date today() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date tomorrow() {
        return Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date yesterday() {
        return Date.from(LocalDate.now().plusDays(-1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime DateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date LocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate DateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date DateToStartDate(Date date) {
        return LocalDateToDate(DateToLocalDate(date));
    }

    public static Date DateToLastDate(Date date) {
        return LocalDateTimeToDate(DateToLocalDate(date).atTime(23, 59, 59));
    }
}
