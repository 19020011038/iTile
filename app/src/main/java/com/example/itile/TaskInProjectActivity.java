package com.example.itile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskInProjectActivity extends AppCompatActivity {

    private String id;
    private TextView new_task;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_task_in_project);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        new_task = findViewById(R.id.new_task);

        new_task.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TaskInProjectActivity.this,NewTaskActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);


            }
        });






    }
}

