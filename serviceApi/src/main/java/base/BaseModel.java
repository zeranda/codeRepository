package base;

import java.util.Date;

/**
 * Created by lifeng on 2018/01/10
 */
public abstract class BaseModel {

    public BaseModel() {
    }

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract Long getOwnerUserId();

    public abstract void setOwnerUserId(Long ownerUserId);

    public abstract String getCreatedBy();

    public abstract void setCreatedBy(String createdBy);

    public abstract Date getGmtCreated();

    public abstract void setGmtCreated(Date gmtCreated);

    public abstract String getModifiedBy();

    public abstract void setModifiedBy(String modifiedBy);

    public abstract Date getGmtModified();

    public abstract void setGmtModified(Date gmtModified);

    public Long getBusinessUnitId() {
        return 0L;
    }

    public void setBusinessUnitId(Long businessUnitId) {

    }

    public void applyNew(Long ownerUserId, String createdBy, Date createdTime) {
        this.setOwnerUserId(ownerUserId);
        this.setCreatedBy(createdBy);
        this.setGmtCreated(createdTime);
        this.setModifiedBy(createdBy);
        this.setGmtModified(createdTime);
    }

    public void applyUpdate(String modifiedBy, Date modifiedTime) {
        this.setModifiedBy(modifiedBy);
        this.setGmtModified(modifiedTime);
    }
}