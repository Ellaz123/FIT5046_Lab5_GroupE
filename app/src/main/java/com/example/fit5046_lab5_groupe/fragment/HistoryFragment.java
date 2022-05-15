package com.example.fit5046_lab5_groupe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fit5046_lab5_groupe.Calender;
import com.example.fit5046_lab5_groupe.databinding.HistoryFragmentBinding;
import com.example.fit5046_lab5_groupe.databinding.HomeFragmentBinding;

public class HistoryFragment extends Fragment {

    private HistoryFragmentBinding histBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        histBinding = HistoryFragmentBinding.inflate(inflater, container, false);
        View view = histBinding.getRoot();
        histBinding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Calender.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
