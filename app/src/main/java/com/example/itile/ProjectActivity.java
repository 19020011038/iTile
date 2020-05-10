package com.example.itile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProjectActivity extends AppCompatActivity {

    private String id;
    private RelativeLayout relativeLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_project);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");


        relativeLayout = findViewById(R.id.all_task);

        relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProjectActivity.this,TaskInProjectActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);


            }
        });


    }
}
