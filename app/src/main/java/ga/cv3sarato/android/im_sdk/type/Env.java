package ga.cv3sarato.android.im_sdk.type;

public enum Env {
    Development("DEV"),
    Test("Test"),
    Prd("Production");

    private String tag;
    private Env(String tag) {
        this.tag = tag;
    }

    public static Env getEnvFromTag(String tag) {
        for(Env env : Env.values()){
            if(tag == env.tag) {
                return env;
            }
        }

        return null;
    }
}
