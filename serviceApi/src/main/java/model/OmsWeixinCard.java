package model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "oms_weixin_card")
public class OmsWeixinCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 卡ID
     */
    @Column(name = "card_id")
    private String cardId;

    /**
     * 卡券类型
     */
    private String type;

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
     * 获取卡ID
     *
     * @return card_id - 卡ID
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置卡ID
     *
     * @param cardId 卡ID
     */
    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    /**
     * 获取卡券类型
     *
     * @return type - 卡券类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置卡券类型
     *
     * @param type 卡券类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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