package com.handler;

import com.alibaba.fastjson.JSON;
import com.builder.NewsBuilder;
import com.builder.TextBuilder;
import common.WxMpEventType;
import common.WxMpXmlMsgType;
import commonUtils.DateUtil;
import entity.MsgInfo;
import entity.OmsWeixinReplyQueryParam;
import entity.OmsWeixinReplyRespDTO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 微信公众号wifi连接事件处理器
 *
 * @Author Tanning
 * @Datetime 2019-04-24 10:12
 */
@Slf4j
@Component
public class WifiConnectedHandler extends AbstractHandler {


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        log.info("WIFI连接用户 openid: {} ", wxMessage.getFromUser());
        Long businessUnitId = (Long) context.get("businessUnitId");
        OmsWeixinReplyQueryParam queryParam = new OmsWeixinReplyQueryParam();
        queryParam.setPage(0);
        queryParam.setPageSize(1);
        queryParam.setBusinessUnitId(businessUnitId);
        queryParam.setEvent(WxMpEventType.WIFI_CONNECTED.getId());
        //TODO 添加查询
        List<OmsWeixinReplyRespDTO> omsWeixinReplyRespDTOs = null;
        if (CollectionUtils.isEmpty(omsWeixinReplyRespDTOs)) {
            log.warn("账套id：{} 未找到对应的公众号，无法自动回复", businessUnitId);
            return null;
        }
        OmsWeixinReplyRespDTO omsWeixinReplyRespDTO = omsWeixinReplyRespDTOs.get(0);
        Date beginDate = DateUtil.parseDate(omsWeixinReplyRespDTO.getBeginAt(), DateUtil.YEAR_TO_SEC);
        Date endAt = DateUtil.parseDate(omsWeixinReplyRespDTO.getEndAt(), DateUtil.YEAR_TO_SEC);
        Date now = new Date();
        if (now.before(beginDate) || now.after(endAt)) {
            log.info("账套id：{} WIFI连接事件不在有效时间内，无法自动回复", businessUnitId);
            return null;
        }
        Integer msgType = omsWeixinReplyRespDTO.getMsgType();
        MsgInfo msgInfo = omsWeixinReplyRespDTO.getMsgInfo();
        if (WxConsts.XmlMsgType.TEXT.equals(WxMpXmlMsgType.getById(msgType).getName())) {
            return new TextBuilder().build(msgInfo.getContent(), wxMessage, wxMpService);
        }
        if (WxConsts.XmlMsgType.NEWS.equals(WxMpXmlMsgType.getById(msgType).getName())) {
            String newsContent = JSON.toJSONString(msgInfo);
            return new NewsBuilder().build(newsContent, wxMessage, wxMpService);
        }
        log.warn("不支持的消息回复类型");
        return null;
    }
}
