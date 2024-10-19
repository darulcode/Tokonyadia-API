package git.darul.tokonyadia.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {

    MIDTRANS("midtrans"),
    BALANCE("balance");

    private final String description;

    public static PaymentMethod fromDescription(String description) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.description.equalsIgnoreCase(description)) {
                return paymentMethod;
            }
        }
        return null;
    }

}

