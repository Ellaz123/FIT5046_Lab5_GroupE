package com.example.fit5046_lab5_groupe;


import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.fit5046_lab5_groupe.database.Database;
import com.example.fit5046_lab5_groupe.entity.User;
import com.example.fit5046_lab5_groupe.viewmodel.UserViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;
import java.util.Locale;


public class UploadWorker extends Worker {

    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Result doWork() {
        final Context applicationContext = getApplicationContext();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String time = sdf.format(Calendar.getInstance().getTimeInMillis());
        Database db = Database.getInstance(applicationContext);
        Log.i("Info", "TimeStamp:" + time + "\nupLoadWorker called");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<User> users = db.userDao().getAllInList();
                if (users == null){
                    Log.e("Error", "No user in database");
                }
                else{
                for (User user : users) {
                    String email = user.email.replace(".", "");
                    String address = user.address;
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://test-487f4-default-rtdb.asia-southeast1.firebasedatabase.app");
                    DatabaseReference mDatabase = database.getReference();
                    mDatabase.child("users").child(email).child("address").setValue(address);
                }
                }
            }
            return Result.success();
        }
}

