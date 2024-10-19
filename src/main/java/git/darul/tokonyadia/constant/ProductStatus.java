package git.darul.tokonyadia.constant;

import lombok.Getter;

@Getter
public enum ProductStatus {
    AVAILABLE("Tersedia"),
    DELETE("Terhapus");

    private String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public static ProductStatus fromDescription(String description) {
        for (ProductStatus productStatus : ProductStatus.values()) {
            if (productStatus.description.equals(description)) {
                return productStatus;
            }
        }
        return null;
    }
}
