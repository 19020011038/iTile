package com.example.itile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itile.Util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NewTaskActivity extends AppCompatActivity {

    private String id;
    private Spinner mspinner_nian;
    private ArrayAdapter<String> adapter_nian;
    private String yyyy1="2020";
    private String MM1="05";
    private String dd1="09";
    private String HH1="23";
    private String mm1="29";

    private String yyyy2="2020";
    private String MM2="05";
    private String dd2="09";
    private String HH2="23";
    private String mm2="59";
    private String description;
    private String starttime;
    private String endtime;

    private TextView finish;

    private Spinner mspinner_yue;
    private ArrayAdapter<String> adapter_yue;
    private Spinner mspinner_ri;
    private ArrayAdapter<String> adapter_ri;
    private Spinner mspinner_shi1;
    private ArrayAdapter<String> adapter_shi1;
    private Spinner mspinner_fen1;
    private ArrayAdapter<String> adapter_fen1;
    private Spinner mspinner_shi2;
    private ArrayAdapter<String> adapter_shi2;
    private Spinner mspinner_fen2;
    private ArrayAdapter<String> adapter_fen2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_task);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        finish = (TextView) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                starttime = yyyy1 + "-" + MM1 + "-" + dd1 + " " + HH1 + "-" + mm1;
                endtime = yyyy2 + "-" + MM2 + "-" + dd2 + " " + HH2 + "-" + mm2;

            }
        });
    }
}

