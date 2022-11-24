package ga.cv3sarato.android.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseActivity;
import ga.cv3sarato.android.base.BaseToolbarActivity;

import java.util.Locale;

import skin.support.SkinCompatManager;

@Route(value = "gtedx://second")
public class SecondActivity extends BaseToolbarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected void init() {

//        setBarColor("#0000ff");

        Button btn = (Button) findViewById(R.id.testBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //语言选择
                setLanguage(new Locale("en"));
                PendingIntent intent = PendingIntent.getActivity(
                        getBaseContext(),
                        0,
                        new Intent(getIntent()),
                        getIntent().getFlags());
                AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500, intent);
                System.exit(2);

                //主题选择
//                SkinCompatManager.getInstance().loadSkin("test", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
            }
        });
    }
}
