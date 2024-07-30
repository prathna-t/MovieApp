package com.example.movielibrary.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Create database layout
@Database(entities = {Movie.class, LoginTable.class}, version = 1)  // version= database structure has been change, update
public abstract class MovieDatabase extends RoomDatabase {  // pretend other to create instance

    public static final String CUSTOMER_DATABASE_NAME = "customer_database";  // database name

    public abstract MovieDao movieDao();
    public abstract LoginDao loginDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile MovieDatabase INSTANCE;  // volatile=instance can be access by multiple treat, at the sametime
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {  // if instance is null
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, CUSTOMER_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

}
