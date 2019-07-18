package entity;

public class OmsWeixinReplyRespDTO {
    private Long id;

    /**
     * 事件类型
     *
     * @see com.huimei.hmc.oms.common.pojo.enums.WxMpEventType
     */
    private Integer event;

    /**
     * 事件类型名称
     *
     * @see com.huimei.hmc.oms.common.pojo.enums.WxMpEventType
     */
    private String eventName;

    /**
     * 关键字，事件类型=2必填
     */
    private String keyword;

    /**
     * 有效开始时间
     */
    private String beginAt;

    /**
     * 有效结束时间
     */
    private String endAt;

    /**
     * 消息类型，1文本，2图文
     * @see com.huimei.hmc.oms.common.pojo.enums.WxMpXmlMsgType
     */
    private Integer msgType;

    /**
     * 消息内容，JSON格式 类型1时，仅为content，2时title,description,url,picurl
     */
    private MsgInfo msgInfo;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取事件类型，0取消关注，1关注，2关键字，3扫码下单，连接wifi
     *
     * @return event - 事件类型，0取消关注，1关注，2关键字，3扫码下单，连接wifi
     */
    public Integer getEvent() {
        return event;
    }

    /**
     * 设置事件类型，0取消关注，1关注，2关键字，3扫码下单，连接wifi
     *
     * @param event 事件类型，0取消关注，1关注，2关键字，3扫码下单，连接wifi
     */
    public void setEvent(Integer event) {
        this.event = event;
    }

    /**
     * 获取关键字，事件类型=2必填
     *
     * @return keyword - 关键字，事件类型=2必填
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 设置关键字，事件类型=2必填
     *
     * @param keyword 关键字，事件类型=2必填
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    /**
     * 获取有效开始时间
     *
     * @return begin_at - 有效开始时间
     */
    public String getBeginAt() {
        return beginAt;
    }

    /**
     * 设置有效开始时间
     *
     * @param beginAt 有效开始时间
     */
    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    /**
     * 获取有效结束时间
     *
     * @return end_at - 有效结束时间
     */
    public String getEndAt() {
        return endAt;
    }

    /**
     * 设置有效结束时间
     *
     * @param endAt 有效结束时间
     */
    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    /**
     * 获取消息类型，1文本，2图文
     *
     * @return msg_type - 消息类型，1文本，2图文
     */
    public Integer getMsgType() {
        return msgType;
    }

    /**
     * 设置消息类型，1文本，2图文
     *
     * @param msgType 消息类型，1文本，2图文
     */
    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    /**
     * 获取消息内容，JSON格式 类型1时，仅为content，2时title,description,url,picurl
     *
     * @return msg_info - 消息内容，JSON格式 类型1时，仅为content，2时title,description,url,picurl
     */
    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    /**
     * 设置消息内容，JSON格式 类型1时，仅为content，2时title,description,url,picurl
     *
     * @param msgInfo 消息内容，JSON格式 类型1时，仅为content，2时title,description,url,picurl
     */
    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}