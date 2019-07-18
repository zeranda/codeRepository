package base;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;

/**
 * Created by lifeng on 2018/01/10
 */
public class BaseDTO {

    public BaseDTO() {

    }

    public Long getId() {
        throw new NotImplementedException();
    }

    public void setId(Long id) {
        throw new NotImplementedException();
    }

    public Long getOwnerUserId() {
        throw new NotImplementedException();
    }

    public void setOwnerUserId(Long ownerUserId) {
        throw new NotImplementedException();
    }

    public String getCreatedBy() {
        throw new NotImplementedException();
    }

    public void setCreatedBy(String createdBy) {
        throw new NotImplementedException();
    }

    public Date getGmtCreated() {
        throw new NotImplementedException();
    }

    public void setGmtCreated(Date gmtCreated) {
        throw new NotImplementedException();
    }

    public String getModifiedBy() {
        throw new NotImplementedException();
    }

    public void setModifiedBy(String modifiedBy) {
        throw new NotImplementedException();
    }

    public Date getGmtModified() {
        throw new NotImplementedException();
    }

    public void setGmtModified(Date gmtModified) {
        throw new NotImplementedException();
    }

    public Long getBusinessUnitId() {
        return 0L;
    }

    public void setBusinessUnitId(Long businessUnitId) {

    }
}