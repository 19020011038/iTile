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
    private RelativeLayout member;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_project);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");


        relativeLayout = findViewById(R.id.all_task);
        member = findViewById(R.id.member);

        relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ProjectActivity.this,TaskInProjectActivity.class);
                intent1.putExtra("id",id);
                startActivity(intent1);


            }
        });

        member.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProjectActivity.this,SeeProjectMemberActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}
