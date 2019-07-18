package dto;


import base.BaseDTO;

import java.util.Date;

/**
 * Created by liuyuanjie on 2018/4/8.
 */
public class MemberAccountDTO  extends BaseDTO {

    private Long id;

    /**
     * 账套id
     */
    private Long businessUnitId;

    /**
     * 会员
     */
    private Long userId;

    /**
     * 登录名
     */
    private String accountName;

    /**
     * 密码需加密
     */
    private String password;

    /**
     * 账号类型
     */
    private Byte accountType;

    /**
     * 微信账号所属的appid
     */
    private String appid;
    /**
     * 唯一账号
     */
    private String unionid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 地址
     */
    private String address;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 性别
     */
    private String gender;

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

    public Long getUserId() {
        return userId;
    }

    public MemberAccountDTO setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public MemberAccountDTO setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MemberAccountDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public Byte getAccountType() {
        return accountType;
    }

    public MemberAccountDTO setAccountType(Byte accountType) {
        this.accountType = accountType;
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

    public String getUnionid() {
        return unionid;
    }

    public MemberAccountDTO setUnionid(String unionid) {
        this.unionid = unionid;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public MemberAccountDTO setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public MemberAccountDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public MemberAccountDTO setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public MemberAccountDTO setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getAppid() {
        return appid;
    }

    public MemberAccountDTO setAppid(String appid) {
        this.appid = appid;
        return this;
    }
}
