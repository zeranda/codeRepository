package commonUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zenghebi
 * @Description:
 * @Date: Created in 10:23 2018/11/1
 */
public class AliSendSmsUtil {
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String product;
    private static String domain;
    private static IAcsClient acsClient;

    public AliSendSmsUtil(String accessKeyId, String accessKeySecret, String product, String domain) {
        AliSendSmsUtil.accessKeyId = accessKeyId;
        AliSendSmsUtil.accessKeySecret = accessKeySecret;
        AliSendSmsUtil.product = product;
        AliSendSmsUtil.domain = domain;
    }

    public void init() throws ClientException {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        acsClient = new DefaultAcsClient(profile);
    }

    /**
     * @param cellphone     短信接收者电话
     * @param signName      签名
     * @param templateCode  模板编码
     * @param templateParam //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
     * @return com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse
     * @Author zenghebi
     * @Modify
     * @Description 发送短信
     * @date 2018/11/1
     */
    public static SendSmsResponse sendSms(String cellphone, String signName, String templateCode, String templateParam) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");


        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(cellphone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

    public static QuerySendDetailsResponse querySendDetails(String cellphone, String bizId) throws ClientException {

        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");


        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setPhoneNumber(cellphone);
        request.setBizId(bizId);
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        request.setPageSize(10L);
        request.setCurrentPage(1L);
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
        return querySendDetailsResponse;
    }

}
