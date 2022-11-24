package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class QREntity {

    public QREntity() {
    }

    public QREntity(String qrcode, String refreshToken) {
        this.qrcode = qrcode;
        this.refreshToken = refreshToken;
    }

    @SerializedName("qrcode")
    private String qrcode;

    @SerializedName("refresh_token")
    private String refreshToken;

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
