package git.darul.tokonyadia.constant;

import lombok.Getter;

@Getter
public enum ShippingMethod {
    SAME_DAY("Same day"),
    NEXT_DAY("Next day"),
    STANDARD("Standar");

    private String description;

    ShippingMethod(String description) {
        this.description = description;
    }

    public static ShippingMethod fromDescription(String description) {
        for (ShippingMethod orderMethod : ShippingMethod.values()){
            if (orderMethod.description.equals(description)){
                return orderMethod;
            }
        }
        return null;
    }

}
