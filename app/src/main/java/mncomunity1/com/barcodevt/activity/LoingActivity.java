package mncomunity1.com.barcodevt.activity;

import android.app.Dialog;
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
import mncomunity1.com.barcodevt.api.APIService;
import mncomunity1.com.barcodevt.model.Login;
import mncomunity1.com.barcodevt.service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoingActivity extends AppCompatActivity {


    Button loginBtn;
    Dialog dialogs;

    EditText td_userid;
    EditText td_pwd;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    SharedPreferences sharedpreferences1;
    public static final String mypreference1 = "ip_address";

    String url ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        td_userid = (EditText) findViewById(R.id.td_userid);
        td_pwd = (EditText) findViewById(R.id.td_pwd);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        sharedpreferences1 = getSharedPreferences(mypreference1, Context.MODE_PRIVATE);
        url = "http://"+sharedpreferences1.getString("ip","");
        if (sharedpreferences.getBoolean("isLogin", false) == true) {
            Intent i = new Intent(getApplicationContext(), Main.class);
            startActivity(i);
            finish();
        } else {

        }

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        dialogs = new Dialog(LoingActivity.this, R.style.FullHeightDialog);
        dialogs.setContentView(R.layout.dailog_loading);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogs.show();
                loginByServer(td_userid.getText().toString(), td_pwd.getText().toString());
            }
        });

    }


    private void loginByServer(String userId, String password) {

        APIService service = ApiClient.getClient(url).create(APIService.class);

        Call<Login> userCall = service.login(userId, password);

        userCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getSuccess().equals("1")) {
                    dialogs.dismiss();
                    String userId = response.body().getUserid();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("userId", userId);
                    editor.commit();

                    Intent i = new Intent(getApplicationContext(), Main.class);
                    i.putExtra("userid", userId);
                    startActivity(i);
                    finish();
                }else{
                    dialogs.dismiss();
                    Toast.makeText(getApplicationContext(),"รหัสผู้ใช้หรือรหัสผ่านไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

}
