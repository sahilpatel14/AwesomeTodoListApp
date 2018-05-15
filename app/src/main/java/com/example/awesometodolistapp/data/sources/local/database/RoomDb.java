package com.example.awesometodolistapp.data.sources.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.awesometodolistapp.data.sources.local.database.dao.TaskDao;
import com.example.awesometodolistapp.data.sources.local.database.entities.TaskEntity;

/**
 * Created by sahil-mac on 12/05/18.
 */

/**
 * A Singleton object that opens Db connection and
 * provides our Dao objects to query data.
 */
@Database(entities = {TaskEntity.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    private static RoomDb INSTANCE;
    private static final String DB_NAME = "task_database";

    static RoomDb getInstance(final Context context) {
        if (INSTANCE == null) {

            //  Making it thread safe
            synchronized (RoomDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDb.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //  Our dao object to access task data.
    public abstract TaskDao taskDao();
}
