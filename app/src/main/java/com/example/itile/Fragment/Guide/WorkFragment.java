package com.example.itile.Fragment.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.itile.AddressActivity;
import com.example.itile.FormActivity;
import com.example.itile.R;
import com.example.itile.TaskActivity;

public class WorkFragment extends Fragment {

    private ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_work, container, false);


        return root;

    }

    @Override
    public void onResume() {
        super.onResume();

        imageView = getActivity().findViewById(R.id.tongxunlu);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), AddressActivity.class); //从前者跳到后者，特别注意的是，在fragment中，用getActivity()来获取当前的activity
                getActivity().startActivity(intent1);
            }
        });
    }


}
