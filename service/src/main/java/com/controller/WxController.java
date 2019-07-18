package com.controller;

import api.IWxCommonService;
import base.JsonResult;
import com.alibaba.fastjson.JSON;
import dto.AccessTokenDTO;
import entity.QingYunKeResultDTO;
import entity.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.CheckUtil;
import utils.HttpClientUtil;
import utils.XmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx/mp")
public class WxController {
    @Value("${spring.profiles}")
    private String env;
    private static Map<String, String> mapTest = new HashMap<>();

    @Autowired
    private IWxCommonService wxCommonService;

    /**
     * 基本配置验证
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping("")
    public String getDispatcherServlet(String signature, String timestamp, String nonce, String echostr) {
        boolean flag = CheckUtil.checkSignature(signature, timestamp, nonce);
        if (!flag) {
            return null;
        }
        return echostr;
    }

    @PostMapping("")
    public void replyMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(env + "=====");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> mapResult = XmlUtils.parseXml(request);
        if (mapResult == null) {
            return;
        }
        String msgType = mapResult.get("MsgType");
        String toUserName = mapResult.get("ToUserName");
        String fromUserName = mapResult.get("FromUserName");
        String content = mapResult.get("Content");
        PrintWriter out = response.getWriter();

        //发送消息模板
        wxCommonService.sendMessageTemplate(fromUserName);

        switch (msgType) {
            case "text":
                String reply;
                String data;
                if (content.contains("水果")) {
                    reply = "这里没有水果";
                } else if (content.contains("你好")) {
                    reply = "你也很好啊";
                } else {
                    data = HttpClientUtil.doGet("http://api.qingyunke.com/api.php?key=free&appid=0&msg=" + content);
                    QingYunKeResultDTO dto = new QingYunKeResultDTO();
                    QingYunKeResultDTO qingYunKeResultDTO = JSON.parseObject(data, QingYunKeResultDTO.class);
                    reply = "嘿嘿";
                    if (data != null || qingYunKeResultDTO.getResult() == 0) {
                        reply = qingYunKeResultDTO.getContent();
                    }

                }
                String textMessage = fillMessage(toUserName, fromUserName, reply);
                out.print(textMessage);
                break;
            default:
                out.print("你写的很有意思!");
                break;
        }
        out.close();
    }

    private String fillMessage(String toUserName, String fromUserName, String reply) {
        TextMessage message = new TextMessage();
        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(System.currentTimeMillis());
        message.setMsgType("text");
        message.setContent(reply);
        return XmlUtils.messageToXml(message);
    }

    @GetMapping("/test")
    public void test() {
        if (mapTest.get("test") != null) {
            System.out.println("静态变量没有被回收");
        } else {
            mapTest.put("test", "test1");
            System.out.println("第一次存放静态变量");
        }

    }

    public static void main(String[] args) throws Exception {
        Class c = TextMessage.class;
        //获取简单类名
        System.out.println(c.getSimpleName());
        //获取完整类名
        System.out.println(c.getName());
        //无参构造创建实例
        TextMessage textMessage = (TextMessage) c.newInstance();
        Constructor constructor = c.getDeclaredConstructor();
        constructor.setAccessible(true);
        TextMessage message = (TextMessage) constructor.newInstance();
        Method setContent = c.getDeclaredMethod("setContent", String.class);
        setContent.setAccessible(true);
        setContent.invoke(message, "你好");
        Method getContent = c.getDeclaredMethod("getContent");
        getContent.setAccessible(true);
        String invoke = (String) getContent.invoke(message);
        System.out.println(invoke);

    }
}
