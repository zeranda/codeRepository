package commonUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import exception.BizException;
import org.junit.Test;
import javax.net.ssl.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author huangjinhong
 * HTTP工具集
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class HttpUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    private static HostnameVerifier verifier;

    private static SSLSocketFactory socketFactory;



    /**
     * request转字符串
     *
     * @param request req请求
     * @return 字符串
     * @see [类、类#方法、类#成员]
     */
    public static String parseRequest(HttpServletRequest request) {
        String body = "";
        try {
            ServletInputStream inputStream = request.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String info = br.readLine();
                if (info == null) {
                    break;
                }
                if ("".equals(body)) {
                    body = info;
                } else {
                    body += info;
                }
            }
        } catch (IOException e) {
            throw new BizException("回掉异常");
        }
        return body;
    }

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

    static {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new HttpUtil.DefaultTrustManager()}, new SecureRandom());
            ctx.getClientSessionContext().setSessionTimeout(15);
            ctx.getClientSessionContext().setSessionCacheSize(1000);
            socketFactory = ctx.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException("初始化失败: " + e.getMessage());
        }
        verifier = (hostname, session) -> {
            return false;//默认认证不通过，进行证书校验。
        };
    }

    /**
     * 执行HTTP POST请求，可使用代理proxy。
     *
     * @param url            请求地址
     * @param params         请求参数
     * @param charset        字符集，如UTF-8, GBK, GB2312
     * @param connectTimeout 连接超时时间
     * @param readTimeout    请求超时时间
     * @param proxyHost      代理host，传null表示不使用代理
     * @param proxyPort      代理端口，传0表示不使用代理
     * @return 响应字符串
     * @throws IOException IOException
     */
    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout,
                                int readTimeout, String proxyHost, int proxyPort) throws IOException {
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return doPost(url, ctype, content, connectTimeout, readTimeout, proxyHost, proxyPort);
    }

    /**
     * post xml数据请求
     * @param url            请求地址
     * @param xml         请求参数
     * @param charset        字符集，如UTF-8, GBK, GB2312
     * @param connectTimeout 连接超时时间
     * @param readTimeout    请求超时时间
     * @param proxyHost      代理host，传null表示不使用代理
     * @param proxyPort      代理端口，传0表示不使用代理
     * @return 响应字符串
     * @throws IOException IOException
     */
    public static String doPostXml(String url, String xml, String charset, Integer connectTimeout, Integer readTimeout, String proxyHost, Integer proxyPort) throws  IOException {
        String ctype = "text/xml;charset=" + charset;
        byte[] content = {};
        if (xml != null) {
            content = xml.getBytes(charset);
        }
        return doPost(url, ctype, content, connectTimeout, readTimeout, proxyHost, proxyPort);
    }

    /**
     * post xml数据请求 简化
     * @param url            请求地址
     * @param xml            请求参数
     * @return
     * @throws IOException
     */
    public static String doPostXml(String url, String xml) throws IOException {
        return HttpUtil.doPostXml(url, xml, "utf-8", 0, 0, "", 0);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url            请求地址
     * @param ctype          请求类型
     * @param content        请求字节数组
     * @param connectTimeout 连接超时时间
     * @param readTimeout    请求超时时间
     * @param proxyHost      代理host，传null表示不使用代理
     * @param proxyPort      代理端口，传0表示不使用代理
     * @return 响应字符串
     * @throws IOException IOException
     */
    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout,
                                String proxyHost, int proxyPort) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp;
        try {
            conn = null;
            if (!StringUtil.isBlank(proxyHost)) {
                conn = getConnection(new URL(url), METHOD_POST, ctype, proxyHost, proxyPort);
            } else {
                conn = getConnection(new URL(url), METHOD_POST, ctype);
            }
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws IOException IOException
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws IOException IOException
     */
    public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
        HttpURLConnection conn = null;
        String rsp;
        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype);
            rsp = getResponseAsString(conn);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
        return getConnection(url, method, ctype, null);
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype,
                                                   String proxyHost, int proxyPort) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        return getConnection(url, method, ctype, proxy);
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Proxy proxy) throws IOException {
        HttpURLConnection conn;
        if ("https".equals(url.getProtocol())) {
            HttpsURLConnection connHttps;
            if (proxy != null) {
                connHttps = (HttpsURLConnection) url.openConnection(proxy);
            } else {
                connHttps = (HttpsURLConnection) url.openConnection();
            }
            connHttps.setSSLSocketFactory(socketFactory);
            connHttps.setHostnameVerifier(verifier);
            conn = connHttps;
        } else {
            if (proxy != null) {
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
        }
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html,application/json");
        conn.setRequestProperty("User-Agent", "com-pay-api");
        conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (StringUtil.isBlank(query)) {
            return url;
        }
        if (StringUtil.isBlank(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "?" + query;
            }
        } else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }
        }
        return new URL(strUrl);
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;
        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (StringUtil.isNotBlank(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (StringUtil.isBlank(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();

            char[] chars = new char[256];
            int count;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }
            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;
        if (!StringUtil.isBlank(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!StringUtil.isBlank(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }
        return charset;
    }

    /**
     * 使用默认的UTF-8字符集反编码请求参数值。
     *
     * @param value 参数值
     * @return 反编码后的参数值
     */
    public static String decode(String value) {
        return decode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用默认的UTF-8字符集编码请求参数值。
     *
     * @param value 参数值
     * @return 编码后的参数值
     */
    public static String encode(String value) {
        return encode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用指定的字符集反编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 反编码后的参数值
     */
    public static String decode(String value, String charset) {
        String result = null;
        if (!StringUtil.isBlank(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 使用指定的字符集编码请求参数值。
     *
     * @param value   参数值
     * @param charset 字符集
     * @return 编码后的参数值
     */
    public static String encode(String value, String charset) {
        String result = null;
        if (!StringUtil.isBlank(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> map = null;
        if (url != null && url.indexOf('?') != -1) {
            map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
        }
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<>();
        String[] pairs = query.split("&");
        if (pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }
        return result;
    }

    @Test
    public void test() throws IOException {
        String url = "http://api.map.baidu.com/geocoder/v2/";
        Map<String, String> map = new HashMap<>();
        map.put("address", "广州");
        map.put("output", "json");
        map.put("ak", "a0nN6zyDllnGWRUFd0a6jKWI");
        String resp = HttpUtil.doGet(url, map);
        JSONObject location = JSON.parseObject(resp).getJSONObject("result").getJSONObject("location");
        String lng = location.get("lng").toString();
        String lat = location.get("lat").toString();
        System.out.println(lng);
        System.out.println(lat);
    }
}
