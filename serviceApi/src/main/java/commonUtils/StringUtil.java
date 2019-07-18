package commonUtils;

import exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * String工具类，判断是否空和不为空
 *
 * @author huangjinhong
 */
public class StringUtil {

    private StringUtil() {
        // 工具类，私有化构造器
    }

    /**
     * 判断字符串是否为空
     *
     * @param string 字符串
     * @return true 空，false 不为空
     */
    public static boolean isBlank(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * 判断多参数字符串是否为空
     *
     * @param strings 字符串
     * @return true 空，false 不为空
     */
    public static boolean isBlank(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String string : strings) {
            if (isNotBlank(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多参数字符串是否为空
     *
     * @param strings 字符串
     * @return true 空，false 不为空
     */
    public static boolean isBlank(List<String> strings) {
        if (strings == null) {
            return false;
        }
        for (String string : strings) {
            if (isNotBlank(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为不为空
     *
     * @param string 字符串
     * @return true 不为空，false 空
     */
    public static boolean isNotBlank(String string) {
        return string != null && !string.isEmpty();
    }

    /**
     * 判断字符串去除前后空格后是否不为空
     *
     * @param string 字符串
     * @return true 不为空，false 空
     * @author doye
     */
    public static boolean isNotBlankTrim(String string) {
        return string != null && !string.trim().isEmpty();
    }

    /**
     * 判断字符串去除前后空格后是否为空
     *
     * @param string 字符串
     * @return true 不为空，false 空
     * @author doye
     */
    public static boolean isBlankTrim(String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * 判断多字符串是否为不为空
     *
     * @param strings 字符串
     * @return true 不为空，false 空
     */
    public static boolean isNotBlank(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String string : strings) {
            if (isBlank(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多字符串是否为不为空（list）
     *
     * @param strings 字符串
     * @return true 不为空，false 空
     */
    public static boolean isNotBlank(List<String> strings) {
        if (strings == null) {
            return false;
        }
        for (String string : strings) {
            if (isBlank(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * ids(1,2,3)转list([1, 2, 3])
     *
     * @param ids ids字符串(1,2,3)
     * @return list集合([1, 2, 3])
     */
    public static List<Long> ids2list(String ids) {
        ids = StringUtils.trimToEmpty(ids);
        if (ids.isEmpty()) return Collections.emptyList();
        return Arrays.stream(ids.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
    }

    /**
     * urls(url,url,url)转list([url, url, url])
     *
     * @param urls urls字符串(url,url,url)
     * @return list集合([url, url, url])
     */
    public static List<String> str2list(String urls) {
        urls = StringUtils.trimToEmpty(urls);
        if (urls.isEmpty()) return Collections.emptyList();
        return Arrays.stream(urls.split(",")).map(String::trim).collect(Collectors.toList());
    }

    /**
     * list转str
     *
     * @param urls list集合([url, url, url])
     * @return urls字符串(url, url, url)
     */
    public static String list2str(List<String> urls) {
        return StringUtils.join(urls.toArray(), ",");
    }

    /**
     * urls(url,url,url)转list([url, url, url])
     *
     * @param urls urls字符串(url,url,url)
     * @return list集合([url, url, url])
     */
    public static List<String> str2list(String urls, String regex) {
        if ("".equals(urls.trim())) {
            return Collections.emptyList();
        }
        return Arrays.stream(urls.split(regex)).map(String::trim).collect(Collectors.toList());
    }

    /**
     * list([1, 2, 3])转ids(1,2,3)
     *
     * @param list list集合([1, 2, 3])
     * @return ids字符串(1, 2, 3)
     */
    public static String list2ids(List<Long> list) {
        return StringUtils.join(list.toArray(), ",");
    }

    /**
     * set([1, 2, 3])转ids(1,2,3)
     *
     * @param list set集合([1, 2, 3])
     * @return ids字符串(1, 2, 3)
     */
    public static String list2ids(Set<Long> list) {
        return StringUtils.join(list.toArray(), ",");
    }

    /**
     * 将字符串散列成整型
     *
     * @param str 字符串
     * @return 数字
     */
    public static long BKDRHash(String str) {
        long seed = 131;
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash * seed) + str.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception 异常
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        Map<String, String> data = new HashMap<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
        org.w3c.dom.Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx = 0; idx < nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }
        try {
            stream.close();
        } catch (Exception ex) {
            throw new BizException("微信通知失败");
        }
        return data;
    }

    /**
     * 格式化异常信息，隐藏掉IP那一部分
     * @param message
     * @return
     */
    public static String formatExceptionMessage(String message) {
        int strIndex = message.indexOf("]");
        return -1 == strIndex ? message : message.substring(++strIndex, message.length());
    }
}
