package ga.cv3sarato.android.entity.request.common;

import com.google.gson.annotations.SerializedName;

public class RefreshTokenEntity {

    @SerializedName("refresh_token")
    private String refreshToken;

    public RefreshTokenEntity(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
