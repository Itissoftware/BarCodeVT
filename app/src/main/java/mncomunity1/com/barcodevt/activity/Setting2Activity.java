package mncomunity1.com.barcodevt.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mncomunity1.com.barcodevt.R;

public class Setting2Activity extends AppCompatActivity {


    Button btn_accept;
    EditText td_ip;
    String type;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "ip_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btn_accept = (Button) findViewById(R.id.btn_accept);
        td_ip = (EditText) findViewById(R.id.td_ip);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);


        td_ip.setText(sharedpreferences.getString("ip", ""));

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ip", td_ip.getText().toString());
//                editor.putBoolean("isCheckIp", true);
                editor.commit();
                Toast.makeText(getApplicationContext(), "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), LoingActivity.class);
                startActivity(i);
                finish();
            }

        });

    }
}
