package com.example.fit5046_lab5_groupe;


import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Date;
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

        //long time =new Date().getTime();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String time = sdf.format(Calendar.getInstance().getTimeInMillis());
        Log.i("Info", "TimeStamp:"+ time + "\nWriting something\nupLoadWorker called");

        return Result.success();
    }

}

