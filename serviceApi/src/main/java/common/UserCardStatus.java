package common;

public enum UserCardStatus {
    NORMAL("正常"),
    CONSUMED("已核销"),
    EXPIRE("已过期"),
    GIFTING("转赠中"),
    GIFT_TIMEOUT("转赠超时"),
    DELETE("已删除"),
    UNAVAILABLE("已失效");

    private String description;

    UserCardStatus(String description) {
        this.description = description;
    }

    public static UserCardStatus matchByStatus(String userCardStatus) {
        for (UserCardStatus item : UserCardStatus.values()) {
            if (item.name().equals(userCardStatus))
                return item;
        }
        return null;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
