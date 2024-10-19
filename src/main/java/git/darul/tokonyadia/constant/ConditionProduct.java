package git.darul.tokonyadia.constant;

import lombok.Getter;

@Getter
public enum ConditionProduct {
    NEW("Baru"),
    SECOND("Bekas");
    private String description;

    ConditionProduct(String description) {
        this.description = description;
    }

    public static ConditionProduct fromDescription(String description) {
        for (ConditionProduct product : ConditionProduct.values()) {
            if (product.description.equalsIgnoreCase(description)) {
                return product;
            }
        }
        return null;
    }

}
