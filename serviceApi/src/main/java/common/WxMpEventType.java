package common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信公众号事件推送类型
 * me.chanjar.weixin.common.api.WxConsts.EventType
 *
 * @Author Tanning
 * @Datetime 2019-04-23 10:55
 */
@AllArgsConstructor
@Getter
public enum WxMpEventType {
    /**
     * 关键字, 微信接口中没有"关键字"事件，这里只定义内部用
     */
    KEYWORD(0, "keyword", "关键字"),

    /**
     * 取消关注事件推送
     */
    UNSUBSCRIBE(1, "unsubscribe", "取消关注"),

    /**
     * 关注事件推送
     */
    SUBSCRIBE(2, "subscribe", "关注"),

    /**
     * 用户已关注时的扫描带参数二维码事件
     */
    SCAN(3, "SCAN", "扫码"),

    /**
     * wifi连网成功事件推送 (微信枚举类中没有定义该事件，官网接口文档中有)
     */
    WIFI_CONNECTED(4, "WifiConnected", "连接WIFI");


    /**** 以下事件暂时未用到，id全部定义为999，需要用到的时候再打开对应事件的注释并更改id ****/

//    /**
//     * 上报地理位置事
//     */
//    LOCATION(999, "LOCATION"),
//
//    /**
//     * 点击菜单事件
//     */
//    CLICK(999, "CLICK"),
//
//    /**
//     * 点击菜单跳转链接时的事件推送
//     */
//    VIEW(999, "VIEW"),
//
//    /**
//     * 消息群发结果事件推送
//     */
//    MASS_SEND_JOB_FINISH(999, "MASSSENDJOBFINISH"),
//
//    /**
//     * 扫码推事件的事件推送
//     */
//    SCANCODE_PUSH(999, "scancode_push"),
//
//    /**
//     * 扫码推事件且弹出“消息接收中”提示框的事件推送.
//     */
//    SCANCODE_WAITMSG(999, "scancode_waitmsg"),
//
//    /**
//     * 弹出系统拍照发图的事件推送.
//     */
//    PIC_SYSPHOTO(999, "pic_sysphoto"),
//
//    /**
//     * 弹出拍照或者相册发图的事件推送.
//     */
//    PIC_PHOTO_OR_ALBUM(999, "pic_photo_or_album"),
//
//    /**
//     * 弹出微信相册发图器的事件推送.
//     */
//    PIC_WEIXIN(999, "pic_weixin"),
//
//    /**
//     * 弹出地理位置选择器的事件推送.
//     */
//    LOCATION_SELECT(999, "location_select"),
//
//    /**
//     * 模版消息发送结果的事件推送.
//     */
//    TEMPLATE_SEND_JOB_FINISH(999, "TEMPLATESENDJOBFINISH"),
//
//    /**
//     * 微信小店 订单付款通知.
//     */
//    MERCHANT_ORDER(999, "merchant_order"),
//
//    /**
//     * 卡券事件：卡券通过审核
//     */
//    CARD_PASS_CHECK(999, "card_pass_check"),
//
//    /**
//     * 卡券事件：卡券未通过审核
//     */
//    CARD_NOT_PASS_CHECK(999, "card_not_pass_check"),
//
//    /**
//     * 卡券事件：用户领取卡券
//     */
//    CARD_USER_GET_CARD(999, "user_get_card"),
//
//    /**
//     * 卡券事件：用户转赠卡券
//     */
//    CARD_USER_GIFTING_CARD(999, "user_gifting_card"),
//
//    /**
//     * 卡券事件：用户核销卡券
//     */
//    CARD_USER_CONSUME_CARD(99, "user_consume_card"),
//
//    /**
//     * 卡券事件：用户通过卡券的微信买单完成时推送
//     */
//    CARD_USER_PAY_FROM_PAY_CELL(999, "user_pay_from_pay_cell"),
//
//    /**
//     * 卡券事件：用户提交会员卡开卡信息
//     */
//    CARD_SUBMIT_MEMBERCARD_USER_INFO(999, "submit_membercard_user_info"),
//
//    /**
//     * 卡券事件：用户打开查看卡券
//     */
//    CARD_USER_VIEW_CARD(999, "user_view_card"),
//
//    /**
//     * 卡券事件：用户删除卡券
//     */
//    CARD_USER_DEL_CARD(999, "user_del_card"),
//
//    /**
//     * 卡券事件：用户在卡券里点击查看公众号进入会话时（需要用户已经关注公众号）
//     */
//    CARD_USER_ENTER_SESSION_FROM_CARD(999, "user_enter_session_from_card"),
//
//    /**
//     * 卡券事件：当用户的会员卡积分余额发生变动时
//     */
//    CARD_UPDATE_MEMBER_CARD(999, "update_member_card"),
//
//    /**
//     * 卡券事件：当某个card_id的初始库存数大于200且当前库存小于等于100时，用户尝试领券会触发发送事件给商户，事件每隔12h发送一次
//     */
//    CARD_SKU_REMIND(999, "card_sku_remind"),
//
//    /**
//     * 卡券事件：当商户朋友的券券点发生变动时
//     */
//    CARD_PAY_ORDER(999, "card_pay_order"),
//
//    /**
//     * 小程序审核事件：审核通过
//     */
//    WEAPP_AUDIT_SUCCESS(999, "weapp_audit_success"),
//
//    /**
//     * 小程序审核事件：审核不通过
//     */
//    WEAPP_AUDIT_FAIL(999, "weapp_audit_fail");

    private int id;
    private String name;
    private String remark;

    public static WxMpEventType getById(int id) {
        for (WxMpEventType wxMpEventType : values()) {
            if (wxMpEventType.getId() == id) {
                return wxMpEventType;
            }
        }
        String errorMsg = String.format("id(%d) is not match", id);
        throw new IllegalArgumentException(errorMsg);
    }

    public static WxMpEventType getByName(String name) {
        for (WxMpEventType wxMpEventType : values()) {
            if (wxMpEventType.getName().equalsIgnoreCase(name)) {
                return wxMpEventType;
            }
        }
        String errorMsg = String.format("name(%s) is not match", name);
        throw new IllegalArgumentException(errorMsg);
    }

    public static WxMpEventType match(int value) {
        for (WxMpEventType wxMpEventType : WxMpEventType.values()) {
            if (wxMpEventType.getId() == value) {
                return wxMpEventType;
            }
        }
        return null;
    }
}
