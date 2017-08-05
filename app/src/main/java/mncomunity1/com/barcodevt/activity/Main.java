package mncomunity1.com.barcodevt.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import mncomunity1.com.barcodevt.R;

public class Main extends AppCompatActivity {

    ImageView img_machine;
    ImageView ic_save;
    ImageView ic_plan;
    ImageView ic_setting;
    TextView txt_user_login;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    SharedPreferences sharedpreferences1;
    public static final String mypreference1 = "ip_address";

    TextView txt_logout;
    String urlSave;
    String urlPlan;
    String IP;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        sharedpreferences1 = getSharedPreferences(mypreference1, Context.MODE_PRIVATE);

        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);
        IP = sharedpreferences1.getString("ip", "");
        userId = sharedpreferences.getString("userId", "");

        urlSave = "http://"+IP+"/pmii_mobile_vt/repair_order.php";
        urlPlan = "http://"+IP+"/PMII_mobile_vt/overview.php?state=";

        txt_logout = (TextView) findViewById(R.id.txt_logout);
        ic_save = (ImageView) findViewById(R.id.ic_save);
        ic_plan = (ImageView) findViewById(R.id.ic_plan);
        img_machine = (ImageView) findViewById(R.id.img_machine);
        ic_setting = (ImageView) findViewById(R.id.ic_setting);
        txt_user_login = (TextView) findViewById(R.id.txt_);
        if(userId != null){
            txt_user_login.setText(userId);
        }

        txt_logout.setPaintFlags(txt_logout.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        img_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(), MoveActivity.class);
                startActivity(i);


            }
        });

        ic_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(getApplicationContext(),WebActivity.class);
                i.putExtra("url",urlSave);
                i.putExtra("userId",userId);
                startActivity(i);


            }
        });

        ic_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(getApplicationContext(),WebActivity.class);
                i.putExtra("url",urlPlan);
                i.putExtra("userId",userId);
                startActivity(i);

            }
        });

        ic_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Setting2Activity.class);
                startActivity(i);
            }
        });

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                editor1.clear();
                editor1.commit();

                Intent i = new Intent(getApplicationContext(), LoingActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

   


}
