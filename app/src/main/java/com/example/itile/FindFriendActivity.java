package com.example.itile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class FindFriendActivity extends AppCompatActivity {

    private RelativeLayout findfriend_back;
    private RelativeLayout findfriend_tel;
    private RelativeLayout findfriend_nickname;
    private RelativeLayout findfriend_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_find_friend);

        findfriend_back = findViewById(R.id.findfriend_back);
        findfriend_tel = findViewById(R.id.findfriend_tel);
        findfriend_nickname = findViewById(R.id.findfriend_nickname);
        findfriend_email = findViewById(R.id.findfriend_email);

        findfriend_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findfriend_tel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FindFriendActivity.this, AddFriendByTelActivity.class);
                startActivity(intent1);
            }
        });

        findfriend_nickname.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(FindFriendActivity.this, AddFriendByNicknameActivity.class);
                startActivity(intent2);
            }
        });

        findfriend_email.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(FindFriendActivity.this, AddFriendByEmailActivity.class);
                startActivity(intent3);
            }
        });
    }
}
