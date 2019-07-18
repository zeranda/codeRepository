package common;

/**
 * created by Jim
 */
public enum AccountTypeEnum {

    COMMON((byte) 0, "普通账号名"),
    PHONE((byte) 1, "手机"),
    WECHAT((byte) 2, "微信"),
    QQ((byte) 3, "QQ"),
    MINIAPP((byte) 4, "小程序"),
    TAOBAO((byte) 5, "淘宝");

    private Byte value;
    private String description;

    AccountTypeEnum(Byte status, String description) {
        this.value = status;
        this.description = description;
    }

    public Byte getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static AccountTypeEnum matchByStatus(Byte status) {
        if (status == null) {
            return null;
        }
        for (AccountTypeEnum accountTypeEnum : AccountTypeEnum.values()) {
            if (status.equals(accountTypeEnum.value)) {
                return accountTypeEnum;
            }
        }
        return null;
    }

    public static AccountTypeEnum matchByDescription(String description) {
        if (description == null) {
            return null;
        }
        for (AccountTypeEnum accountTypeEnum : AccountTypeEnum.values()) {
            if (description.equals(accountTypeEnum.description)) {
                return accountTypeEnum;
            }
        }
        return null;
    }
}
