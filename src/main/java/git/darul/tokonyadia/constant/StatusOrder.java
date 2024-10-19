package git.darul.tokonyadia.constant;

import lombok.Getter;

@Getter
public enum StatusOrder {
    CANCEL("Gagal"),
    PENDING("Pembayaran"),
    PROCESS("Proses"),
    DELIVERY("Pengiriman"),
    COMPLETED("Selesai");

    private String description;

    StatusOrder(String description) {
        this.description = description;
    }

    public static StatusOrder fromDescription(String description) {
        for (StatusOrder statusOrder : StatusOrder.values()){
            if (statusOrder.description.equals(description)){
                return statusOrder;
            }
        }
        return null;
    }
}
