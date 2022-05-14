package com.example.fit5046_lab5_groupe.database;

import android.content.*;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fit5046_lab5_groupe.dao.OrderDAO;
import com.example.fit5046_lab5_groupe.dao.UserDAO;
import com.example.fit5046_lab5_groupe.entity.Order;
import com.example.fit5046_lab5_groupe.entity.User;

import java.util.concurrent.*;

@androidx.room.Database(entities = {User.class, Order.class}, version = 3, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract OrderDAO orderDao();
    public abstract UserDAO userDao();

    private static Database INSTANCE;

    private static final int NUMBER_OF_THREADS = 3;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized Database getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "Database").fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
