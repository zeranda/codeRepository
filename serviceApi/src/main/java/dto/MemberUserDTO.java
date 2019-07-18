package dto;


import base.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liuyuanjie on 2018/4/4.
 */
public class MemberUserDTO extends BaseDTO {

    private Long id;

    /**
     * 账套id
     */
    private Long businessUnitId;

    /**
     * 会员名称或手机号码
     *
     */
    private String name;

    /**
     * 0-手机 1-名称 同一个账套类型 手机的要唯一, 普通的 平台+name要唯一
     */
    private Integer type;

    /**
     * 注册平台id
     */
    private Long platformId;

    /**
     * 平台编码
     */
    private String platformCode;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 绑定的店铺
     */
    private Long bindingId;

    /**
     * 二级绑定关系
     */
    private Long secondBindingId;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 会员等级id
     */
    private Long level;

    /**
     * 会员等级名
     */
    private String lelvelName;

    /**
     * 会员积分
     */
    private Integer score;

    /**
     * 会员经验值
     */
    private Integer experiencePoint;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobileNo;

    /**
     * 消费总金额
     */
    private BigDecimal buyTotal;

    /**
     * 消费总次数
     */
    private Integer buyTime;

    /**
     * 是否黑名单会员
     */
    private Boolean isBlack;

    /**
     * 是否被冻结
     */
    private Boolean isFrozen;

    /**
     * 是否被注销
     */
    private Boolean isCancelled;

    /**
     * 连续登陆失败次数
     */
    private Integer failCount;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 注册时间
     */
    private Date registerDate;


    /**
     * 创建人Id
     */
    private Long ownerUserId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改人
     */
    private String modifiedBy;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 渠道类型:0线下,1线上
     */
    private Integer channelType;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 会员信息描述
     */
    private String description;

    /**
     * 代言人状态
     */
    private Integer spokesmanStatus;

    private String spokesmanStatusDesc;

    /**
     * 待审核 待确认
     */
    private Boolean isProcessing;

    private Boolean isManyInvitation;

    /**
     * 绑定店铺编码
     */
    private String bindingShopCode;

    /**
     * 最后绑定店铺的时间
     */
    private Date bindingShopTime;

    public Date getBindingShopTime() {
        return bindingShopTime;
    }

    public void setBindingShopTime(Date bindingShopTime) {
        this.bindingShopTime = bindingShopTime;
    }

    public String getBindingShopCode() {
        return bindingShopCode;
    }

    public void setBindingShopCode(String bindingShopCode) {
        this.bindingShopCode = bindingShopCode;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getBusinessUnitId() {
        return businessUnitId;
    }

    @Override
    public void setBusinessUnitId(Long businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public String getName() {
        return name;
    }

    public MemberUserDTO setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    public Integer getType() {
        return type;
    }

    public MemberUserDTO setType(Integer type) {
        this.type = type;
        return this;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public MemberUserDTO setPlatformId(Long platformId) {
        this.platformId = platformId;
        return this;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public MemberUserDTO setPlatformCode(String platformCode) {
        this.platformCode = platformCode == null ? null : platformCode.trim();
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public MemberUserDTO setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public MemberUserDTO setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public Long getBindingId() {
        return bindingId;
    }

    public MemberUserDTO setBindingId(Long bindingId) {
        this.bindingId = bindingId;
        return this;
    }

    public Long getSecondBindingId() {
        return secondBindingId;
    }

    public MemberUserDTO setSecondBindingId(Long secondBindingId) {
        this.secondBindingId = secondBindingId;
        return this;
    }

    public Boolean getSex() {
        return sex;
    }

    public MemberUserDTO setSex(Boolean sex) {
        this.sex = sex;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public MemberUserDTO setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getLelvelName() {
        return lelvelName;
    }

    public MemberUserDTO setLelvelName(String lelvelName) {
        this.lelvelName = lelvelName;
        return this;
    }

    public Long getLevel() {
        return level;
    }

    public MemberUserDTO setLevel(Long level) {
        this.level = level;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public MemberUserDTO setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Integer getExperiencePoint() {
        return experiencePoint;
    }

    public MemberUserDTO setExperiencePoint(Integer experiencePoint) {
        this.experiencePoint = experiencePoint;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MemberUserDTO setEmail(String email) {
        this.email = email == null ? null : email.trim();
        return this;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public MemberUserDTO setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
        return this;
    }

    public BigDecimal getBuyTotal() {
        return buyTotal;
    }

    public MemberUserDTO setBuyTotal(BigDecimal buyTotal) {
        this.buyTotal = buyTotal;
        return this;
    }

    public Integer getBuyTime() {
        return buyTime;
    }

    public MemberUserDTO setBuyTime(Integer buyTime) {
        this.buyTime = buyTime;
        return this;
    }

    public Boolean getIsBlack() {
        return isBlack;
    }

    public MemberUserDTO setIsBlack(Boolean isBlack) {
        this.isBlack = isBlack;
        return this;
    }

    public Boolean getIsFrozen() {
        return isFrozen;
    }

    public MemberUserDTO setIsFrozen(Boolean isFrozen) {
        this.isFrozen = isFrozen;
        return this;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public MemberUserDTO setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
        return this;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public MemberUserDTO setFailCount(Integer failCount) {
        this.failCount = failCount;
        return this;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public MemberUserDTO setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        return this;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public MemberUserDTO setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
        return this;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public MemberUserDTO setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    @Override
    public Long getOwnerUserId() {
        return ownerUserId;
    }

    @Override
    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getGmtCreated() {
        return gmtCreated;
    }

    @Override
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Override
    public String getModifiedBy() {
        return modifiedBy;
    }

    @Override
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public Date getGmtModified() {
        return gmtModified;
    }

    @Override
    public void setGmtModified(Date gmtModified) {


        this.gmtModified = gmtModified;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public MemberUserDTO setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSpokesmanStatus() {
        return spokesmanStatus;
    }

    public MemberUserDTO setSpokesmanStatus(Integer spokesmanStatus) {
        this.spokesmanStatus = spokesmanStatus;
        return this;
    }

    public String getSpokesmanStatusDesc() {
        return spokesmanStatusDesc;
    }

    public MemberUserDTO setSpokesmanStatusDesc(String spokesmanStatusDesc) {
        this.spokesmanStatusDesc = spokesmanStatusDesc;
        return this;
    }

    public Boolean getProcessing() {
        return isProcessing;
    }

    public MemberUserDTO setProcessing(Boolean processing) {
        isProcessing = processing;
        return this;
    }

    public Boolean getManyInvitation() {
        return isManyInvitation;
    }

    public MemberUserDTO setManyInvitation(Boolean manyInvitation) {
        isManyInvitation = manyInvitation;
        return this;
    }
}
