package com.example.fit5046_lab5_groupe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.fit5046_lab5_groupe.databinding.HomeFragmentBinding;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

public class HomeFragment extends Fragment {
    private MapView mapView;
    private HomeFragmentBinding homeBinding;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        homeBinding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();
        final Point point = Point.fromLngLat(145.045837, -37.876823 );
        mapView = homeBinding.mapView;
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .zoom(13.0)
                .center(point)
                .build();
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        mapView.getMapboxMap().setCamera(cameraPosition);
        return view;
    }
}