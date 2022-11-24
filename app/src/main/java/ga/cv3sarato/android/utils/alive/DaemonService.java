package ga.cv3sarato.android.utils.alive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;



/**
 * Created by masachi on 2017/11/6.
 */

public class DaemonService  extends Service {
    private static final String TAG = "DaemonService";
    public static final int NOTICE_ID = 100;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 如果Service被终止
        // 当资源允许情况下，重启service
        return START_NOT_STICKY;
    }
}
