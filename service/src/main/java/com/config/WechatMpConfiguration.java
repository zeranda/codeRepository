package com.config;

import com.handler.*;
import common.WxMpEventType;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * wechat mp configuration
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Configuration
@ConditionalOnClass(WxMpService.class)
@EnableConfigurationProperties(WechatMpProperties.class)
public class WechatMpConfiguration {
	@Autowired
	protected LogHandler logHandler;
	@Autowired
	protected NullHandler nullHandler;
	@Autowired
	protected KfSessionHandler kfSessionHandler;
	@Autowired
	protected StoreCheckNotifyHandler storeCheckNotifyHandler;
	@Autowired
	private WechatMpProperties properties;
	@Autowired
	private LocationHandler locationHandler;
	@Autowired
	private MenuHandler menuHandler;
	@Autowired
	private MsgHandler msgHandler;
	@Autowired
	private UnsubscribeHandler unsubscribeHandler;
	@Autowired
	private SubscribeHandler subscribeHandler;
	@Autowired
	private ScanHandler scanHandler;
	@Autowired
	private WifiConnectedHandler wifiConnectedHandler;
	@Autowired
	private UserGetCardHandler userGetCardHandler;

	@Bean
	@ConditionalOnMissingBean
	public WxMpConfigStorage configStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(this.properties.getAppId());
		configStorage.setSecret(this.properties.getSecret());
		configStorage.setToken(this.properties.getToken());
		configStorage.setAesKey(this.properties.getAesKey());
		return configStorage;
	}

	@Bean
	@ConditionalOnMissingBean
	public WxMpService wxMpService(WxMpConfigStorage configStorage) {
		// WxMpService wxMpService = new
		// me.chanjar.weixin.mp.api.impl.okhttp.WxMpServiceImpl();
		// WxMpService wxMpService = new
		// me.chanjar.weixin.mp.api.impl.jodd.WxMpServiceImpl();
		// WxMpService wxMpService = new
		// me.chanjar.weixin.mp.api.impl.apache.WxMpServiceImpl();
		WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(configStorage);
		return wxMpService;
	}

	@Bean
	public WxMpMessageRouter router(WxMpService wxMpService) {
		final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

		// 记录所有事件的日志 （异步执行）
		newRouter.rule().handler(this.logHandler).next();


		// 接收客服会话管理事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
				.handler(this.kfSessionHandler).end();
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
				.handler(this.kfSessionHandler).end();
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
				.handler(this.kfSessionHandler).end();

		// 门店审核事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventConstants.POI_CHECK_NOTIFY)
				.handler(this.storeCheckNotifyHandler)
				.end();

		// 自定义菜单事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.MenuButtonType.CLICK).handler(this.menuHandler).end();

		// 点击菜单跳转URL事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.MenuButtonType.VIEW).handler(this.nullHandler).end();

		// 扫码带参数事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.SUBSCRIBE)
				.eventKeyRegex("qrscene\\w+")
				.handler(this.scanHandler)
				.end();

		// 关注事件（扫码不带参数）
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.SUBSCRIBE).handler(this.subscribeHandler)
				.end();

		// 取消关注事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.UNSUBSCRIBE).handler(this.unsubscribeHandler)
				.end();

		// 上报地理位置事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.LOCATION).handler(this.locationHandler).end();

		// 接收地理位置消息
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION)
				.handler(this.locationHandler).end();

		// 扫码事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.SCAN).handler(scanHandler).end();

		// wifi连接事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxMpEventType.WIFI_CONNECTED.getName()).handler(wifiConnectedHandler).end();

		//用户领取卡券事件
		newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
				.event(WxConsts.EventType.CARD_USER_GET_CARD).handler(userGetCardHandler).end();


		// 默认
		newRouter.rule().async(false).handler(this.msgHandler).end();

		return newRouter;
	}

	protected MenuHandler getMenuHandler() {
		return this.menuHandler;
	}

	protected SubscribeHandler getSubscribeHandler() {
		return this.subscribeHandler;
	}

	protected UnsubscribeHandler getUnsubscribeHandler() {
		return this.unsubscribeHandler;
	}

	protected AbstractHandler getLocationHandler() {
		return this.locationHandler;
	}

	protected MsgHandler getMsgHandler() {
		return this.msgHandler;
	}

	protected AbstractHandler getScanHandler() {
		return null;
	}

}
