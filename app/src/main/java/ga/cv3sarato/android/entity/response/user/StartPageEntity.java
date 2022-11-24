package ga.cv3sarato.android.entity.response.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StartPageEntity {

    @SerializedName("start_pages")
    private List<StartPageItem> startPages;

    public List<StartPageItem> getStartPages() {
        return startPages;
    }

    public void setStartPages(List<StartPageItem> startPages) {
        this.startPages = startPages;
    }

    public class StartPageItem {

        @SerializedName("type")
        private String type;

        @SerializedName("duration")
        private int duration;

        @SerializedName("url")
        private String url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public boolean equals(Object obj) {
            System.out.println(obj);
            return obj.equals(this.getType());
        }
    }
}
