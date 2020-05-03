package com.example.itile.Fragment.Task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.itile.R;

public class UpcomingFragment extends Fragment {
    public static UpcomingFragment newInstance(int index) {
        UpcomingFragment fragment = new UpcomingFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt(ARG_SECTION_NUMBER, index);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task_upcoming, container, false);

//        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerView);
//        list.clear();

        return root;
    }
}
