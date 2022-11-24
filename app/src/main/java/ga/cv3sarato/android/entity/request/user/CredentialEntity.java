package ga.cv3sarato.android.entity.request.user;

import com.google.gson.annotations.SerializedName;

public class CredentialEntity {

    public CredentialEntity(String credentialType, String credential) {
        this.credentialType = credentialType;
        this.credential = credential;
    }

    public CredentialEntity() {
    }

    @SerializedName("credential_type")
    private String credentialType;

    @SerializedName("credential")
    private String credential;

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}
