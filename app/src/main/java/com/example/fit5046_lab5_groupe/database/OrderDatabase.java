package com.example.fit5046_lab5_groupe.database;

import android.content.*;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fit5046_lab5_groupe.dao.OrderDAO;
import com.example.fit5046_lab5_groupe.entity.Order;

import java.util.concurrent.*;

@Database(entities = {Order.class}, version = 1, exportSchema = false)
public abstract class OrderDatabase extends RoomDatabase {

    public abstract OrderDAO orderDao();

    private static OrderDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 3;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized OrderDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    OrderDatabase.class, "OrderDatabase").fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
