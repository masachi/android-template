package ga.cv3sarato.android.entity.file;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class FileUploadEntity {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("upload_url")
    private String uploadUrl;

    @SerializedName("upload_method")
    private String uploadMethod;

    @SerializedName("upload_params")
    private HashMap<String, String> uploadParams;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("created_at")
    private long createdAt;

    @SerializedName("download_url")
    private String downloadUrl;

    @SerializedName("view_url")
    private String viewUrl;

    @SerializedName("cloud_file_name")
    private String fileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getUploadMethod() {
        return uploadMethod;
    }

    public void setUploadMethod(String uploadMethod) {
        this.uploadMethod = uploadMethod;
    }

    public HashMap<String, String> getUploadParams() {
        return uploadParams;
    }

    public void setUploadParams(HashMap<String, String> uploadParams) {
        this.uploadParams = uploadParams;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public class UploadParams {

        @SerializedName("OSSAccessKeyId")
        private String OSSAccessKeyId;

        @SerializedName("signature")
        private String signature;

        @SerializedName("key")
        private String key;

        @SerializedName("policy")
        private String policy;

        public String getOSSAccessKeyId() {
            return OSSAccessKeyId;
        }

        public void setOSSAccessKeyId(String OSSAccessKeyId) {
            this.OSSAccessKeyId = OSSAccessKeyId;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }
    }
}
