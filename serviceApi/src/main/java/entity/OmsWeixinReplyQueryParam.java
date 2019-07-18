package entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @Author Tanning
 * @Datetime 2019-04-24 00:31
 */
@EqualsAndHashCode(callSuper = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OmsWeixinReplyQueryParam {
    /**
     * 主键id
     */
    Long id;
    /**
     * 应用编码
     */
    String frontCode;
    /**
     * 事件类型，0取消关注，1关注，2关键字，3扫码下单，连接wifi
     */
    Integer event;
    /**
     * 关键字，事件类型=2必填
     */
    String keyword;
    /**
     * 账套ID
     */
    Long businessUnitId;
    /**
     * 分页索引，默认0
     */
    int page;
    /**
     * 分页大小
     */
    Integer pageSize;
}
