package entity;



public class OmsWeixinMenuRespDTO {

    private Long id;

    /**
     * 上级ID
     */
    private Long parentId;

    /**
     * 标题
     */
    private String title;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 跳转URL
     */
    private String url;

    /**
     * 菜单类型
     * @see
     */
    private Integer type;

    /**
     * 动作，1-view
     */
    private Integer action;

    /**
     * 小程序id
     */
    private String maAppid;

    /**
     * 小程序路径
     */
    private String maPath;

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
     * 获取上级ID
     *
     * @return parent_id - 上级ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置上级ID
     *
     * @param parentId 上级ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取排序
     *
     * @return sort_order - 排序
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * 设置排序
     *
     * @param sortOrder 排序
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 获取跳转URL
     *
     * @return url - 跳转URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置跳转URL
     *
     * @param url 跳转URL
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取跳转类型，1小程序，2URL
     *
     * @return type - 跳转类型，1小程序，2URL
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置跳转类型，1小程序，2URL
     *
     * @param type 跳转类型，1小程序，2URL
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取动作，1-view
     *
     * @return action - 动作，1-view
     */
    public Integer getAction() {
        return action;
    }

    /**
     * 设置动作，1-view
     *
     * @param action 动作，1-view
     */
    public void setAction(Integer action) {
        this.action = action;
    }

    /**
     * 获取小程序id
     *
     * @return ma_appid - 小程序id
     */
    public String getMaAppid() {
        return maAppid;
    }

    /**
     * 设置小程序id
     *
     * @param maAppid 小程序id
     */
    public void setMaAppid(String maAppid) {
        this.maAppid = maAppid == null ? null : maAppid.trim();
    }

    /**
     * 获取小程序路径
     *
     * @return ma_path - 小程序路径
     */
    public String getMaPath() {
        return maPath;
    }

    /**
     * 设置小程序路径
     *
     * @param maPath 小程序路径
     */
    public void setMaPath(String maPath) {
        this.maPath = maPath == null ? null : maPath.trim();
    }
}