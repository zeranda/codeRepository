package dto;

/**
 * Created by huangyuheng on 2018/2/3.
 */
public class AliYunOssDTO {

    /**
     * 指的用户请求的accessid。注意仅知道accessid, 对数据不会有影响。
     */
    private String accessid;

    /**
     * 指的是用户表单上传的策略policy，是经过base64编码过的字符串。
     */
    private String policy;

    /**
     * 是对上述第三个变量policy签名后的字符串。
     */
    private String signature;

    /**
     * 上传key
     */
    private String key;

    /**
     * 指的是用户要往哪个域名发往上传请求。
     */
    private String host;

    /**
     * 指的是当前上传策略失效时间
     */
    private Long expire;

    public AliYunOssDTO() {
    }

    public AliYunOssDTO(String accessid, String policy, String signature, String key, String host, Long expire) {
        this.accessid = accessid;
        this.policy = policy;
        this.signature = signature;
        this.key = key;
        this.host = host;
        this.expire = expire;
    }

    public String getAccessid() {
        return accessid;
    }

    public void setAccessid(String accessid) {
        this.accessid = accessid;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
