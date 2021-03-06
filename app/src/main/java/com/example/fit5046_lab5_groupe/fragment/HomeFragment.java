package com.example.fit5046_lab5_groupe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.fit5046_lab5_groupe.Items;
import com.example.fit5046_lab5_groupe.Login;
import com.example.fit5046_lab5_groupe.RetrofitClient;
import com.example.fit5046_lab5_groupe.RetrofitInterface;
import com.example.fit5046_lab5_groupe.SearchResponse;
import com.example.fit5046_lab5_groupe.databinding.HomeFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private HomeFragmentBinding homeBinding;
    private static final String API_KEY = "AIzaSyCa_mz8x6hlWTvsWSzGRF9s9-HGVszpwX0";
    private static final String SEARCH_ID_cx = "d3126328946095b61";
    private String keyword;
    private RetrofitInterface retrofitInterface;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();
        homeBinding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeFragment.this.getActivity(), Login.class));
            }
        });


        retrofitInterface = RetrofitClient.getRetrofitService();
        homeBinding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword=homeBinding.editText.getText().toString();
                Call<SearchResponse> callAsync =
                        retrofitInterface.customSearch(API_KEY,SEARCH_ID_cx, keyword);
                callAsync.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call,
                                           Response<SearchResponse> response) {
                        if (response.isSuccessful()) {
                            List<Items> list = response.body().items;
                            if (list == null) {
                                Toast.makeText(getActivity(), "Nothing found in search engine",
                                        Toast.LENGTH_SHORT);
                            }
                            else {
                                String result = list.get(0).getSnippet();
                                homeBinding.resultTextView.setText(result);
                            }
                        }
                        else {
                            Log.i("Error ","Response failed");
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t){
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT);
                    }
                });
            }
        });
        //
        return view;

    }

}