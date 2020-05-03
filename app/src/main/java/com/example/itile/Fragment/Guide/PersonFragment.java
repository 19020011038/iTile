package com.example.itile.Fragment.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.itile.FormActivity;
import com.example.itile.R;
import com.example.itile.SettingActivity;
import com.example.itile.TaskActivity;

public class PersonFragment extends Fragment {

    private ImageView setting;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_person, container, false);


        return root;

    }

    @Override
    public void onResume() {
        super.onResume();

        setting = getActivity().findViewById(R.id.setting);


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingActivity.class); //从前者跳到后者，特别注意的是，在fragment中，用getActivity()来获取当前的activity
                getActivity().startActivity(intent);
            }
        });

    }

}
