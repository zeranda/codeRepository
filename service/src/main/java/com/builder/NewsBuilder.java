package com.builder;

import com.alibaba.fastjson.JSON;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;

/**
 * @Author Tanning
 * @Datetime 2019-04-23 19:19
 */
public class NewsBuilder extends AbstractBuilder {

    /**
     * 目前只支持一条图文，content只有一条图文信息
     */
    @Override
    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WxMpService wxMpService) {
        return WxMpXmlOutMessage.NEWS()
                .addArticle(JSON.parseObject(content, WxMpXmlOutNewsMessage.Item.class))
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
    }
}
