package com.service;

import api.IWxCommonService;
import base.JsonResult;
import com.alibaba.fastjson.JSON;
import commonUtils.HttpUtil;
import dto.AccessTokenDTO;
import exception.BizException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WxCommonServiceImpl implements IWxCommonService {
    @Autowired
    private WxMpService wxService;
    @Value("${wechat.mp.appId}")
    private String appId;
    @Value("${wechat.mp.secret}")
    private String secret;

    @Override
    public AccessTokenDTO getAccessToken() {
        String accessTokenUrl = WxMpService.GET_ACCESS_TOKEN_URL;
        accessTokenUrl = String.format(accessTokenUrl, appId, secret);
        try {
            String data = HttpUtil.doGet(accessTokenUrl, new HashMap<>());
            AccessTokenDTO accessTokenDTO = JSON.parseObject(data, AccessTokenDTO.class);
            System.out.println(accessTokenDTO.getAccess_token() + "=====");
            return accessTokenDTO;
        } catch (IOException e) {
            throw new BizException("获取AccessToken失败");
        }
    }

    @Override
    public void sendMessageTemplate(String touser) {
        try {
            //框架会根据yml配置自动获取access_token
            WxMpTemplateMsgService templateMsgService = wxService.getTemplateMsgService();
            WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
            //参数是用户的openId(也就是微信发过来的fromUsername)
            templateMessage.setToUser(touser);
            //填写微信公众平台设置的模板id
            templateMessage.setTemplateId("HbUmjM05gX4Tjr-fzZsqnkHmAc4OC2nEBhB3qkw02PA");
            List<WxMpTemplateData> dataList = new ArrayList<>();
            //name-value填写模板消息中的变量
            WxMpTemplateData data1 = new WxMpTemplateData("one", "周骥");
            WxMpTemplateData data2 = new WxMpTemplateData("two", "999");
            dataList.add(data1);
            dataList.add(data2);
            templateMessage.setData(dataList);
            //采用框架发送模板消息
            templateMsgService.sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            throw new BizException("发送消息失败");
        }
    }


}
