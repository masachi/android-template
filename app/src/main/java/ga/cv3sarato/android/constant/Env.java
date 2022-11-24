package ga.cv3sarato.android.constant;

import ga.cv3sarato.android.BuildConfig;

public class Env {
    public static final String DOMAIN = BuildConfig.SERVER_URL;

    public static String SERVER_URL = DOMAIN + "/pareto/";
    public static String MAIL_URL = DOMAIN + "/mail2/";
    public static String FILE_URL = DOMAIN + "/cloud-file/";
    public static String IAM_BACKEND_URL = DOMAIN + "/iam-backend/";
    public static String IAM_URL = DOMAIN + "/iam/";
}
