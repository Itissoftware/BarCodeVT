package mncomunity1.com.barcodevt.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mncomunity1.com.barcodevt.R;
import mncomunity1.com.barcodevt.adapter.CustomAdapter;
import mncomunity1.com.barcodevt.api.APIService;
import mncomunity1.com.barcodevt.model.Machine;
import mncomunity1.com.barcodevt.model.Post;
import mncomunity1.com.barcodevt.service.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoveActivity extends AppCompatActivity {

    Button btn_scaner;
    Button btn_sunmit;
    Button btn_location;
    TextView txt_localtion;
    ArrayList<Machine> ar = new ArrayList<>();
     static Retrofit retrofit = null;
    ListView sp;
    CustomAdapter customAdapter;
    Dialog dialogs;
    String localtion;
    String URL;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "ip_address";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_scaner = (Button) findViewById(R.id.btn_scaner);
        btn_sunmit = (Button) findViewById(R.id.btn_sunmit);
        btn_location = (Button) findViewById(R.id.btn_location);
        txt_localtion = (TextView) findViewById(R.id.txt_localtion);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        URL = "http://"+sharedpreferences.getString("ip", "");

        dialogs = new Dialog(MoveActivity.this, R.style.FullHeightDialog);
        dialogs.setContentView(R.layout.dailog_loading);


        sp = (ListView) findViewById(R.id.ListView01);
        btn_scaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MoveActivity.this, ScannerActivity.class);
                intent.putExtra("type","2");
                startActivityForResult(intent, 2);
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MoveActivity.this, ScannerActivity.class);
                intent.putExtra("type","3");
                startActivityForResult(intent, 3);

            }
        });


        btn_sunmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(localtion != null){
                        dialogs.show();

                        for (Machine hold : customAdapter.getAllData()) {
                            if(hold != null){
                                if (hold.isBox()) {

                                }
                                getSpareByServer(hold.getNameTh(),localtion);
                            }

                        }
                    }else{
                    Toast.makeText(getApplicationContext(),"ยังไม่ได้เลือก สถานที่เก็บ",Toast.LENGTH_SHORT).show();
                    }
            }
        });

        sp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {

                ar.remove(ar.get(position));
                sp.requestLayout();
                customAdapter.notifyDataSetChanged();
                Toast.makeText(MoveActivity.this,"ลบ",Toast.LENGTH_SHORT).show();

                return false;
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2

        if (requestCode == 2) {

            if(data != null){
                String message = data.getStringExtra("MESSAGE");
                Machine m = new Machine();
                m.setNameTh(message);

                ar.add(m);
                if (ar != null) {
                    btn_sunmit.setVisibility(View.VISIBLE);
                    customAdapter = new CustomAdapter(getApplicationContext(), ar);
                    sp.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();
                    sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                            //Item Selected from list
                            customAdapter.setCheckBox(position);

                        }
                    });

                }
            }


        }if (requestCode == 3) {
            if(data != null){
                localtion = data.getStringExtra("LOCALTION");
                txt_localtion.setText(localtion);
                Log.e("LOCALTION",localtion);
            }


        }
    }


    private void getSpareByServer(String cat,String location) {

        APIService service = ApiClient.getClient(URL).create(APIService.class);

        Call<Post> userCall = service.getOrder(cat,location);


        userCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {


                if(response.body().getStatus().equals("1")){
                    dialogs.dismiss();
                    Toast.makeText(getApplicationContext(),"ย้ายเครื่องจักรสำเร็จ",Toast.LENGTH_SHORT).show();
                    ar.clear();
                    txt_localtion.setText("");
                    customAdapter.notifyDataSetChanged();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"อะไหล่: "+response.body().getNo()+" ไม่มีในฐานข้อมูล",Toast.LENGTH_SHORT).show();
                    dialogs.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }


}
