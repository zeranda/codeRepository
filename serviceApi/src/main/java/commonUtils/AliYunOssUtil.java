package commonUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import dto.AliYunOssDTO;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * 阿里OSS存储
 *
 * @author huangjinhong
 */
public class AliYunOssUtil {

    private static String endPoint;
    private static String bucket;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static OSSClient ossClient;
    private static String prefix;

    public AliYunOssUtil(String endPoint, String bucket, String accessKeyId, String accessKeySecret, String prefix) {
        AliYunOssUtil.endPoint = endPoint;
        AliYunOssUtil.bucket = bucket;
        AliYunOssUtil.accessKeyId = accessKeyId;
        AliYunOssUtil.accessKeySecret = accessKeySecret;
        AliYunOssUtil.prefix = prefix;
    }

    public void init() {
        destroy();
        ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
    }

    public void destroy() {
        if (ossClient != null) {
            ossClient.shutdown();
            ossClient = null;
        }
    }

    private String getHost() {
        return "http://" + bucket + "." + endPoint;
    }

    /**
     * 获取网络前缀url
     *
     * @return 网络前缀url
     */
    public static String getUrl(String customPrefix, String fileName) {
        return prefix + "/" + customPrefix + "/" + fileName;
    }

    /**
     * 上传网络流（这只是示例，根据业务自行修改）
     *
     * @param url url
     */
    public static void uploadNetStream(String url) {
        try {
            // 切割文件
            String[] splitArr = url.split("/");
            String rebuildFileName = splitArr[splitArr.length - 1];
            InputStream inputStream = new URL(url).openStream();
            // 上传文件
            ossClient.putObject(bucket, rename(rebuildFileName), inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件流（这只是示例，根据业务自行修改）
     *
     * @param filePath 文件完整路径
     */
    public static void uploadFileStream(String filePath) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            ossClient.putObject(bucket, rename(filePath), inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取文件失败");
        }
    }

    /**
     * 上传文件（这只是示例，根据业务自行修改）
     *
     * @param filePath 文件完整路径
     */
    public static void uploadFile(String filePath) {
        ossClient.putObject(bucket, rename(filePath), new File(filePath));
    }

    /**
     * 上传文件流
     *
     * @param inputStream  输出流
     * @param customPrefix 自定义前缀(例如：dir01、dri01/dir02)
     * @param fileName     文件原来名称
     * @param rename       是否重命名为随机名称
     */
    public static String uploadFile(InputStream inputStream, String customPrefix, String fileName, boolean rename) {
        if (rename) {
            fileName = rename(fileName);
        }
        String path = customPrefix + "/" + fileName;
        ossClient.putObject(bucket, path, inputStream);
        return prefix + "/" + path;
    }

    /**
     * 上传文件流
     *
     * @param inputStream  输出流
     * @param customPrefix 自定义前缀(例如：dir01、dri01/dir02)
     * @param suffix       文件后缀名
     */
    public static String uploadFile(InputStream inputStream, String customPrefix, String suffix) {
        String path = customPrefix + "/" + random(suffix);
        ossClient.putObject(bucket, path, inputStream);
        return prefix + "/" + path;
    }

    /**
     * 根据图片地址删除对象
     *
     * @param imageSrc 图片路径
     */
    public static void deleteObjectBySrc(String imageSrc) {
        String path = imageSrc.replaceAll(prefix + "/", "").trim();
        ossClient.deleteObject(bucket, path);
    }

    /**
     * 删除文件
     */
    public static void deleteObject(String key) {
        ossClient.deleteObject(bucket, key);
    }

    /**
     * 删除文件
     *
     * @param customPrefix 自定义前缀
     * @param fileName     文件名称
     */
    public static void deleteFile(String customPrefix, String fileName) {
        String path = customPrefix + "/" + fileName;
        ossClient.deleteObject(bucket, path);
    }

    /**
     * 判断文件是否存在
     *
     * @param customPrefix 自定义前缀
     * @param fileName     文件名称
     * @return true 是，false 不是
     */
    public static boolean isExist(String customPrefix, String fileName) {
        try {
            String path = customPrefix + "/" + fileName;
            ossClient.getObject(bucket, path);
            return true;
        } catch (OSSException exp) {
            return false;
        }
    }

    /**
     * 生成签名，用于前端传递文件。
     *
     * @param fileName   文件名
     * @param expireTime 超时时间
     * @param dir        上传目录
     * @return 阿里云DTO
     */
    public AliYunOssDTO buildSignatureJson(String fileName, Long expireTime, String dir) {
        AliYunOssDTO signatureDTO = new AliYunOssDTO();
        try {
            Date expiration = null;
            if (expireTime != null) {
                long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
                expiration = new Date(expireEndTime);
            }
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            if (StringUtils.isNotBlank(dir)) {
                policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            }
            String postPolicy = ossClient.generatePostPolicy(
                    expiration == null ? null : DateUtils.addHours(expiration, 8),
                    policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            signatureDTO = new AliYunOssDTO(accessKeyId, encodedPolicy, postSignature, dir + random(fileName), getHost(),
                    expiration == null ? 0 : expiration.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signatureDTO;
    }

    /**
     * 下载文件到本地（这只是示例，根据业务自行修改）
     *
     * @param key      文件key
     * @param filePath 保存文件路径
     */
    public static void getObject(String key, String filePath) {
        ossClient.getObject(new GetObjectRequest(bucket, key), new File(filePath));
    }

    /**
     * 列出所有对象（这只是示例，根据业务自行修改）
     */
    public static void listObjects() {
        ObjectListing objectListing = ossClient.listObjects(bucket);
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            System.out.println("\t" + s.getKey());
        }
    }

    /**
     * 给文件生成随机名称
     *
     * @param srcName 文件名称
     * @return 生成文件名称
     */
    private static String rename(String srcName) {
        String[] splitArr = srcName.split("\\.");
        if (splitArr.length == 0 || splitArr.length == 1) {
            throw new RuntimeException("上传的文件名异常");
        }
        return random(splitArr[splitArr.length - 1]);
    }

    /**
     * 给文件生成随机名称
     *
     * @param suffix 后缀名
     * @return 文件名称
     */
    private static String random(String suffix) {
        StringBuilder sb = new StringBuilder();
        String currentDateStr = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        sb.append(currentDateStr).append("_").append(RandomStringUtils.randomAlphanumeric(8)).append(".").append(suffix);
        return sb.toString();
    }
}
