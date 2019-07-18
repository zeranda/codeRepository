package common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信公众号自定义菜单按钮类型
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013
 * me.chanjar.weixin.common.api.WxConsts.MenuButtonType
 *
 * @Author Tanning
 * @Datetime 2019-04-23 10:55
 */
@AllArgsConstructor
@Getter
public enum WxMpMenuButtonType {

    /**
     * 点击推事件.
     */
    CLICK(1, "click"),

    /**
     * 跳转URL.
     */
    VIEW(2, "view"),

    /**
     * 跳转到小程序
     */
    MINIPROGRAM(3, "miniprogram"),

    /**
     * 扫码推事件.
     */
    SCANCODE_PUSH(4, "scancode_push"),

    /**
     * 扫码推事件且弹出“消息接收中”提示框.
     */
    SCANCODE_WAITMSG(5, "scancode_waitmsg"),

    /**
     * 弹出系统拍照发图.
     */
    PIC_SYSPHOTO(6, "pic_sysphoto"),

    /**
     * 弹出拍照或者相册发图.
     */
    PIC_PHOTO_OR_ALBUM(7, "pic_photo_or_album"),

    /**
     * 弹出微信相册发图器.
     */
    PIC_WEIXIN(8, "pic_weixin"),

    /**
     * 弹出地理位置选择器.
     */
    LOCATION_SELECT(9, "location_select"),

    /**
     * 下发消息（除文本消息）.
     */
    MEDIA_ID(10, "media_id"),

    /**
     * 跳转图文消息URL
     */
    VIEW_LIMITED(11, "view_limited");

    private int id;
    private String name;

    public static WxMpMenuButtonType getById(int id) {
        for (WxMpMenuButtonType wxMpMenuButtonType : values()) {
            if (wxMpMenuButtonType.getId() == id) {
                return wxMpMenuButtonType;
            }
        }
        String errorMsg = String.format("id(%d) is not match", id);
        throw new IllegalArgumentException(errorMsg);
    }

    public static WxMpMenuButtonType getByName(String name) {
        for (WxMpMenuButtonType wxMpMenuButtonType : values()) {
            if (wxMpMenuButtonType.getName().equalsIgnoreCase(name)) {
                return wxMpMenuButtonType;
            }
        }
        String errorMsg = String.format("name(%s) is not match", name);
        throw new IllegalArgumentException(errorMsg);
    }

    public static WxMpMenuButtonType match(int value) {
        for (WxMpMenuButtonType wxMpMenuButtonType : WxMpMenuButtonType.values()) {
            if (wxMpMenuButtonType.getId() == value) {
                return wxMpMenuButtonType;
            }
        }
        return null;
    }
}
