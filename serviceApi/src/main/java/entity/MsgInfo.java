package entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * 微信公众号回复消息内容
 * 消息内容，JSON格式保存 msgType类型1时，仅为content，2时title,description,url,picurl
 *
 * @Author Tanning
 * @Datetime 2019-04-22 15:31
 */
@EqualsAndHashCode(callSuper = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MsgInfo {
    /**
     * 消息文本
     */
    String content;
    /**
     * 图文标题
     */
    String title;
    /**
     * 图文备注
     */
    String description;
    /**
     * 图文链接
     */
    String url;
    /**
     * 图文图片
     */
    String picurl;
}
