package common;

/**
 * Created by liuyuanjie on 2018/4/9.
 */
public enum UserTypeEnum {

    MOBILE(0, "手机"),
    NAME(1, "普通");


    private Integer value;
    private String description;

    UserTypeEnum(Integer status, String description) {
        this.value = status;
        this.description = description;

    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static final UserTypeEnum matchByStatus(Integer status) {
        if (status == null) {
            return null;
        }
        for (UserTypeEnum userEnum : UserTypeEnum.values()) {
            if (status.equals(userEnum.value)) {
                return userEnum;
            }
        }
        return null;
    }

    public static final UserTypeEnum matchByDescription(String description) {
        if (description == null) {
            return null;
        }
        for (UserTypeEnum userEnum : UserTypeEnum.values()) {
            if (description.equals(userEnum.description)) {
                return userEnum;
            }
        }
        return null;
    }
}
