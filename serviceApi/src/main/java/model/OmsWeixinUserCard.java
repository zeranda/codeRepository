package model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "oms_weixin_user_card")
public class OmsWeixinUserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 卡券ID
     */
    @Column(name = "card_id")
    private String cardId;

    /**
     * 卡券CODE
     */
    private String code;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 创建人ID
     */
    @Column(name = "owner_user_id")
    private Long ownerUserId;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "gmt_created")
    private Date gmtCreated;

    /**
     * 修改人
     */
    @Column(name = "modified_by")
    private String modifiedBy;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 账套ID
     */
    @Column(name = "business_unit_id")
    private Long businessUnitId;

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
     * 获取卡券ID
     *
     * @return card_id - 卡券ID
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置卡券ID
     *
     * @param cardId 卡券ID
     */
    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    /**
     * 获取卡券CODE
     *
     * @return code - 卡券CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置卡券CODE
     *
     * @param code 卡券CODE
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取会员ID
     *
     * @return member_id - 会员ID
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置会员ID
     *
     * @param memberId 会员ID
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取创建人ID
     *
     * @return owner_user_id - 创建人ID
     */
    public Long getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * 设置创建人ID
     *
     * @param ownerUserId 创建人ID
     */
    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * 获取创建人
     *
     * @return created_by - 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建人
     *
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * 获取创建时间
     *
     * @return gmt_created - 创建时间
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreated 创建时间
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * 获取修改人
     *
     * @return modified_by - 修改人
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * 设置修改人
     *
     * @param modifiedBy 修改人
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }

    /**
     * 获取修改时间
     *
     * @return gmt_modified - 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置修改时间
     *
     * @param gmtModified 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取账套ID
     *
     * @return business_unit_id - 账套ID
     */
    public Long getBusinessUnitId() {
        return businessUnitId;
    }

    /**
     * 设置账套ID
     *
     * @param businessUnitId 账套ID
     */
    public void setBusinessUnitId(Long businessUnitId) {
        this.businessUnitId = businessUnitId;
    }
}