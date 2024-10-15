package git.darul.tokonyadia.constant;

import lombok.Getter;

@Getter
public enum UserType {

    ROLE_BUYER("Buyer"),
    ROLE_SELLER("Seller");

    private String description;

    UserType(String description) {
        this.description = description;
    }

    public static UserType fromDescription(String description) {
        for (UserType userType : UserType.values()) {
            if (userType.description.equals(description)) {
                return userType;
            }
        }
        return null;
    }
}
