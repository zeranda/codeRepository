package com.handler;

import com.alibaba.fastjson.JSON;
import com.builder.TextBuilder;
import common.AccountTypeEnum;
import common.CardType;
import common.UserCardStatus;
import common.UserTypeEnum;
import commonUtils.ListUtil;
import commonUtils.StringUtil;
import dto.MemberAccountDTO;
import dto.MemberUserDTO;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.card.WxMpCardResult;
import me.chanjar.weixin.mp.bean.membercard.MemberCardUserInfo;
import me.chanjar.weixin.mp.bean.membercard.NameValues;
import me.chanjar.weixin.mp.bean.membercard.WxMpMemberCardActivatedMessage;
import me.chanjar.weixin.mp.bean.membercard.WxMpMemberCardUserInfoResult;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import model.OmsWeixinCard;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 卡券事件：用户领取卡券事件处理器
 */
@Component
public class UserGetCardHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        logger.info(String.format("用户领取卡券事件，事件消息：{%s}", wxMessage.toString()));

        Long businessUnitId = (Long) context.get("businessUnitId");
        Assert.notNull(businessUnitId, "账套ID为空");
        //TODO
        OmsWeixinCard card = null;
        Assert.notNull(card, "找不到对应的卡券");

        CardType cardType = CardType.matchByName(card.getType());
        Assert.notNull(cardType, String.format("找不到卡券类型{%s}", card.getType()));

        Long userId = this.getUserId(wxMessage, wxMpService, businessUnitId);

        try {
            switch (cardType) {
                case MEMBER_CARD:
                    return getMemberCard(userId, wxMessage, wxMpService, businessUnitId);
                case GENERAL_COUPON:
                    return getCouponCard(userId, wxMessage, wxMpService, businessUnitId);
                default:
                    throw new IllegalArgumentException("找不到对应的卡券类型");
            }
        } catch (Exception e) {
            logger.error(String.format("微信用户领取卡券事件处理异常，事件消息{%s}，异常信息{%s}",
                    wxMessage.toString(), e.getMessage()));
        }

        return new TextBuilder().build("error", wxMessage, wxMpService);
    }

    /**
     * 获得用户ID
     *
     * @param wxMessage
     * @param wxMpService
     * @param businessUnitId
     * @return
     * @throws WxErrorException
     */
    private Long getUserId(WxMpXmlMessage wxMessage, WxMpService wxMpService, Long businessUnitId) throws WxErrorException {

        String openId = wxMessage.getFromUser();
        String unionId = StringUtil.isNotBlankTrim(wxMessage.getUnionId()) ? wxMessage.getUnionId() : openId;
        // TODO
        MemberAccountDTO account = null;

        if (Objects.isNull(account)) {
            WxMpUser userInfo = wxMpService.getUserService().userInfo(openId, "zh_CN");
            if (userInfo == null) {
                logger.error(String.format("用户获得会员卡事件处理异常，获取用户微信信息失败：{%s}", wxMessage.toString()));
                return null;
            }
            //TODO
            List<MemberAccountDTO> accountList = null;

            Long userId = null;
            if (ListUtil.isEmpty(accountList)) {
                userId = this.addWeixinMember(unionId, userInfo, businessUnitId);

            } else {
                userId = accountList.get(0).getUserId();
            }

            if (userId == null) {
                logger.error(String.format("用户获得会员卡事件处理异常，创建会员异常，领取卡券事件消息：{%s}", wxMessage.toString()));
                return null;
            }

            this.addWeixinAccount(userId, openId, unionId, userInfo, wxMpService.getWxMpConfigStorage().getAppId(), businessUnitId);
            //TODO
            account = null;
            if (account == null) {
                logger.error(String.format("用户获得会员卡事件处理异常，会员账号异常，领取卡券事件消息：{%s}", wxMessage.toString()));
                return null;
            }
        }

        return account.getUserId();
    }

    /**
     * 处理用户领取会员卡事件
     *
     * @param wxMessage
     * @param wxMpService
     * @param businessUnitId
     * @return
     * @throws WxErrorException
     */
    private WxMpXmlOutMessage getMemberCard(Long userId, WxMpXmlMessage wxMessage,
                                            WxMpService wxMpService,
                                            Long businessUnitId) throws WxErrorException {

        WxMpMemberCardUserInfoResult cardUserInfo = wxMpService.getMemberCardService().getUserInfo(wxMessage.getCardId(), wxMessage.getUserCardCode());
        if (cardUserInfo == null) {
            logger.error(String.format("用户获得会员卡事件处理异常，获取卡券信息异常，领取卡券事件消息：{%s}", wxMessage.toString()));
            return null;
        }

        MemberCardUserInfo cardInfo = cardUserInfo.getUserInfo();
        if (cardInfo == null) {
            logger.error(String.format("用户获得会员卡事件处理异常，获取卡券信息异常，领取卡券事件消息：{%s}", wxMessage.toString()));
            return null;
        }

        NameValues[] fieldList = cardInfo.getCommonFieldList();
        if (fieldList == null) {
            logger.error(String.format("用户获得会员卡事件处理异常，获取卡券信息异常，领取卡券事件消息：{%s}", wxMessage.toString()));
            return null;
        }

        String phone = "";
        for (NameValues nameValues : fieldList) {
            if (nameValues.getName().equals("USER_FORM_INFO_FLAG_MOBILE")) {
                phone = nameValues.getValue();
                break;
            }
        }

        if (!this.bindPhone(userId, phone, wxMpService.getWxMpConfigStorage().getAppId(), businessUnitId)) {
            logger.error(String.format("用户获得会员卡事件处理异常，绑定手机账号失败，领取卡券事件消息：{%s}, 卡券信息：{%s}",
                    wxMessage.toString(), JSON.toJSONString(cardUserInfo)));
        }

        //cardService.storeUserCard(wxMessage.getCardId(), wxMessage.getUserCardCode(), userId, businessUnitId);
        //TODO
        MemberUserDTO user = null;

        WxMpMemberCardActivatedMessage activatedMessage = new WxMpMemberCardActivatedMessage();
        activatedMessage.setCardId(wxMessage.getCardId());
        activatedMessage.setCode(wxMessage.getUserCardCode());
        activatedMessage.setInitCustomFieldValue2(user.getLelvelName());
        activatedMessage.setInitCustomFieldValue3(String.valueOf(user.getExperiencePoint()));
        wxMpService.getMemberCardService().activateMemberCard(activatedMessage);

        return new TextBuilder().build("success", wxMessage, wxMpService);
    }

    /**
     * 处理用户领取优惠券卡券事件
     *
     * @param userId
     * @param wxMessage
     * @param wxMpService
     * @param businessUnitId
     * @return
     */
    private WxMpXmlOutMessage getCouponCard(Long userId, WxMpXmlMessage wxMessage,
                                            WxMpService wxMpService,
                                            Long businessUnitId) throws WxErrorException {
        //TODO
        WxMpCardResult cardCodeResult = null;
        Assert.notNull(cardCodeResult, "查询卡券CODE状态失败");

        UserCardStatus status = UserCardStatus.matchByStatus(cardCodeResult.getUserCardStatus());
        Assert.notNull(status, "卡券状态异常");

        if (!status.equals(UserCardStatus.NORMAL)) {
            return new TextBuilder().build("success", wxMessage, wxMpService);
        }

        try {
//            couponService.receiveCouponByCode(wxMessage.getUserCardCode(), userId, FrontCodeEnum.MSTORE.getName(),
//                    businessUnitId, SystemSettings.Constants.OPERATOR_ID, SystemSettings.Constants.OPERATOR);
        } catch (Exception e) {
            logger.error(String.format("用户获得会员卡事件处理异常，领取优惠券失败，异常信息{%s}，用户ID{%s}，事件信息{%s}",
                    e.getMessage(), userId, wxMessage.toString()));
        }

        return new TextBuilder().build("success", wxMessage, wxMpService);
    }


    /**
     * 绑定手机号
     *
     * @param userId
     * @param phone
     * @param appId
     * @param businessUnitId
     * @return
     */
    private Boolean bindPhone(Long userId, String phone, String appId, Long businessUnitId) {
        if (StringUtil.isBlankTrim(phone)) {
            return false;
        }
        //TODO
        MemberAccountDTO phoneAccount = null;

        if (phoneAccount != null) {
            return false;
        }

        this.addPhoneAccount(userId, phone, appId, businessUnitId);

        return true;
    }

    /**
     * 根据微信号创建会员数据
     *
     * @param unionId
     * @param userInfo
     * @param businessUnitId
     * @return
     */
    private Long addWeixinMember(String unionId, WxMpUser userInfo, Long businessUnitId) {
        String nickname = StringUtil.isNotBlankTrim(userInfo.getNickname()) ? userInfo.getNickname() : unionId;
        String avatar = StringUtil.isNotBlankTrim(userInfo.getHeadImgUrl()) ? userInfo.getHeadImgUrl() : "";

        MemberUserDTO userDTO = new MemberUserDTO();
        userDTO.setName(unionId)
                .setType(UserTypeEnum.NAME.getValue())
                .setPlatformCode("OMS")
                .setNickName(nickname)
                .setSex(true)
                .setAvatarUrl(avatar)
                .setBusinessUnitId(businessUnitId);
        //TODO
        return null;
    }

    /**
     * 创建用户微信账号
     *
     * @param userId
     * @param openId
     * @param unionId
     * @param userInfo
     * @param appid
     * @param businessUnitId
     */
    private void addWeixinAccount(Long userId, String openId, String unionId, WxMpUser userInfo, String appid, Long businessUnitId) {
        String nickname = StringUtil.isNotBlankTrim(userInfo.getNickname()) ? userInfo.getNickname() : unionId;
        String avatar = StringUtil.isNotBlankTrim(userInfo.getHeadImgUrl()) ? userInfo.getHeadImgUrl() : "";

        MemberAccountDTO accountDTO = new MemberAccountDTO();
        accountDTO.setUserId(userId)
                .setAccountName(openId)
                .setUnionid(unionId)
                .setNickname(nickname)
                .setAccountType(AccountTypeEnum.WECHAT.getValue())
                .setAvatar(avatar)
                .setAppid(appid)
                .setBusinessUnitId(businessUnitId);

        //accountService.addByWeiXin(accountDTO, businessUnitId, accountDTO.getUserId(), accountDTO.getNickname());
    }

    /**
     * 创建手机账号
     *
     * @param userId
     * @param phone
     * @param appId
     * @param businessUnitId
     */
    private void addPhoneAccount(Long userId, String phone, String appId, Long businessUnitId) {
        String password = "123456";
        //userService.thirdBindPhone(userId, phone, password, AccountTypeEnum.WECHAT, appId, businessUnitId, userId, phone);
    }
}