package git.darul.tokonyadia.constant;

import lombok.Getter;

@Getter
public enum CartStatus {
    ACTIVE("Aktif"),
    INACTIVE("Nonaktif");

    private String description;

    CartStatus(String description) {
        this.description = description;
    }

    public static CartStatus fromDescription(String description) {
        for (CartStatus cartStatus : CartStatus.values()){
            if (cartStatus.description.equals(description)){
                return cartStatus;
            }
        }
        return null;
    }
}
