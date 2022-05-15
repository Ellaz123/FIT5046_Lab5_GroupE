package com.example.fit5046_lab5_groupe.fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.fit5046_lab5_groupe.R;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.fit5046_lab5_groupe.UploadWorker;
import com.example.fit5046_lab5_groupe.dao.UserDAO;
import com.example.fit5046_lab5_groupe.databinding.MapFragmentBinding;
import com.example.fit5046_lab5_groupe.viewmodel.UserViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.annotation.*;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MapFragment extends Fragment {
    private MapView mapView;
    private MapFragmentBinding mapBinding;
    private UserViewModel userViewModel;


    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapBinding = MapFragmentBinding.inflate(inflater, container, false);
        View view = mapBinding.getRoot();


        //Get current user email and search user address from room database
        //Move camera to address location and place a marker
        mapBinding.RoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = MapFragment.this.
                        getActivity().getIntent().getStringExtra("CurrentUserEmail");
                email = email.replace(".", "");
                userViewModel = ViewModelProvider.AndroidViewModelFactory.
                        getInstance(MapFragment.this.getActivity().
                                getApplication()).create(UserViewModel.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    userViewModel.findByEmailFuture(email).thenApply(user -> {
                        String address = user.address;
                        mapBinding.textView4.setText("This is your address:" + address);
                        LatLng latLng =
                                getLocationFromAddress(MapFragment.this.getActivity(), address);
                        final Point point = Point.fromLngLat(latLng.longitude, latLng.latitude);
                        mapView = mapBinding.mapView;
                        CameraOptions cameraPosition = new CameraOptions.Builder()
                                .zoom(13.0)
                                .center(point)
                                .build();

                        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
                        mapView.getMapboxMap().setCamera(cameraPosition);
                        addAnnotationToMap(address);
                        return user;});}
            }
        });


        mapBinding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
                WorkManager.getInstance(MapFragment.this.getActivity()).enqueue(uploadWorkRequest);
            }
        });


        //Get current user email and search user address from firebase database
        //Move camera to address location and place a marker
        mapBinding.FireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.
                        getInstance("https://test-487f4-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference mDatabase = database.getReference();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String firebaseEmail = user.getEmail().replace(".","");
                mDatabase.child("users").child(firebaseEmail).child("address").get().
                        addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    toastMsg("Error getting data");
                                }
                                else {
                                    String address = String.valueOf(task.getResult().getValue());
                                    mapBinding.textView4.setText("This is your address:" + address);
                                    // Example address "900 Dandenong Rd, Caulfield East"
                                    LatLng latLng = getLocationFromAddress(MapFragment.this.getActivity(), address);
                                    final Point point = Point.fromLngLat(latLng.longitude, latLng.latitude);
                                    mapView = mapBinding.mapView;
                                    CameraOptions cameraPosition = new CameraOptions.Builder()
                                            .zoom(13.0)
                                            .center(point)
                                            .build();

                                    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
                                    mapView.getMapboxMap().setCamera(cameraPosition);
                                    addAnnotationToMap(address);
                                }
                            }
                        });
            }
        });



        return view;
    }

    //Add point annotation on the map, and place a marker on the point.
    private void addAnnotationToMap(String address) {
        mapView = mapBinding.mapView;
        Bitmap bitmap = bitmapFromDrawableRes(MapFragment.this.getActivity(), R.drawable.red_marker);

        LatLng latLng = getLocationFromAddress(MapFragment.this.getActivity(), address);
        AnnotationPlugin annotationApi = mapView.getPlugin(Plugin.MAPBOX_ANNOTATION_PLUGIN_ID);
        AnnotationManager pointAnnotationManager = annotationApi.
                createAnnotationManager(AnnotationType.PointAnnotation,null);
        PointAnnotationOptions pointAnnotationOptions = new
                PointAnnotationOptions().withPoint(Point.
                fromLngLat(latLng.longitude, latLng.latitude)).withIconImage(bitmap);
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    private Bitmap bitmapFromDrawableRes(Context context, @DrawableRes int resourceId) {
        return convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId));
    }

    // Convert drawable to Bitmap
    private Bitmap convertDrawableToBitmap(Drawable sourceDrawable) {
        if (sourceDrawable == null) {
            return null;
        }
        if (sourceDrawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) sourceDrawable).getBitmap();
        }
        else {
            Drawable.ConstantState constantState = sourceDrawable.getConstantState();
            if (constantState == null) {
                return null;
            }
            else {
                Drawable drawable = constantState.newDrawable().mutate();
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getWidth());
                drawable.draw(canvas);
                return bitmap;
            }
        }
    }

    // Using Geocoder to get the lat and lng from address
    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList;
        LatLng latLng = null;
        try {
            addressList = geocoder.getFromLocationName(strAddress, 1);
            if (strAddress!=null) {
                Address location = addressList.get(0);
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return latLng;
    }

    public void toastMsg(String message){
        Toast.makeText(this.getActivity(),message, Toast.LENGTH_SHORT).show();
    }

}
