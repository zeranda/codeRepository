package common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信公众号微信推送过来的消息的类型，和发送给微信xml格式消息的消息类型
 * me.chanjar.weixin.common.api.WxConsts.XmlMsgType
 *
 * @Author Tanning
 * @Datetime 2019-04-23 19:05
 */
@AllArgsConstructor
@Getter
public enum WxMpXmlMsgType {
    /**
     * 文本消息
     */
    TEXT(1, "text"),
    /**
     * 图文消息
     */
    NEWS(2, "news"),
    /**
     * 图片消息
     */
    IMAGE(3, "image"),
    /**
     * 语音消息
     */
    VOICE(4, "voice"),
    /**
     * 短视频消息
     */
    SHORTVIDEO(5, "shortvideo"),
    /**
     * 视频
     */
    VIDEO(6, "video"),
    /**
     * 音乐消息
     */
    MUSIC(7, "music"),
    /**
     * 地理位置消息
     */
    LOCATION(8, "location"),
    LINK(9, "link"),
    EVENT(10, "event"),
    DEVICE_TEXT(11, "device_text"),
    DEVICE_EVENT(12, "device_event"),
    DEVICE_STATUS(13, "device_status"),
    HARDWARE(14, "hardware"),
    TRANSFER_CUSTOMER_SERVICE(15, "transfer_customer_service");

    private int id;
    private String name;

    public static WxMpXmlMsgType getById(int id) {
        for (WxMpXmlMsgType wxMpXmlMsgType : values()) {
            if (wxMpXmlMsgType.getId() == id) {
                return wxMpXmlMsgType;
            }
        }
        String errorMsg = String.format("id(%d) is not match", id);
        throw new IllegalArgumentException(errorMsg);
    }

    public static WxMpXmlMsgType getByName(String name) {
        for (WxMpXmlMsgType wxMpXmlMsgType : values()) {
            if (wxMpXmlMsgType.getName().equalsIgnoreCase(name)) {
                return wxMpXmlMsgType;
            }
        }
        String errorMsg = String.format("name(%s) is not match", name);
        throw new IllegalArgumentException(errorMsg);
    }
}
