package git.darul.tokonyadia.constant;

import lombok.Getter;

@Getter
public enum CategoryStatus {
    ACTIVE("Aktif"),
    INACTIVE("Nonaktif");

    private String description;

    CategoryStatus(String description) {
        this.description = description;
    }

    public static CategoryStatus fromDescription(String description) {
        for (CategoryStatus category : CategoryStatus.values()) {
            if (category.description.equals(description)) {
                return category;
            }
        }
        return null;
    }
}
