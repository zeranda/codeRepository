package common;

public enum  CardType {
    MEMBER_CARD("会员卡"),
    GROUPON("团购券"),
    CASH("代金券"),
    DISCOUNT("折扣券"),
    GIFT("兑换券"),
    GENERAL_COUPON("优惠券");

    private String description;

    CardType(String description) {
        this.description = description;
    }

    public static CardType matchByName(String name) {
        for (CardType item : CardType.values()) {
            if (item.name().equals(name))
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
