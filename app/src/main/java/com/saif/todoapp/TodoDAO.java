package com.saif.todoapp;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDAO {


    @Insert
    public void insertTask(Todo todo);
    @Update
    public void updateTask(Todo todo);
    @Delete
    public void deleteTask(Todo todo);
    @Query("SELECT * FROM todo")
    List<Todo> getTodoList();


}
