package com.saif.todoapp;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Todo.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static final String DB_NAME = "person_db";
    private static AppDataBase instance;

    public abstract TodoDAO todoDAO();

    public static synchronized AppDataBase getInstance(Context context)
    {
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
       return instance;

    }

}

