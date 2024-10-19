package git.darul.tokonyadia.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentStatus {

    SETTLEMENT("settlement"),
    PENDING("pending"),
    DENY("deny"),
    CANCEL("cancel"),
    EXPIRE("expire");


    private final String description;

    public static PaymentStatus fromDescription(String description) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.description.equalsIgnoreCase(description)) {
                return paymentStatus;
            }
        }
        return null;
    }

}