
package com.saif.todoapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.View;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import com.saif.todoapp.ui.ToDoAdapter;
import com.saif.todoapp.ui.TodoListener;


import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TodoListener  {


    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;

    private List<Todo> taskList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        taskList = new ArrayList<>();

        RecyclerView tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new ToDoAdapter(this,this);
        tasksRecyclerView.setAdapter(tasksAdapter);
        FloatingActionButton fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        getListTask();

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")

    public void handleDialogClose(DialogInterface dialog){


        getListTask();
        tasksAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItem(Todo todo) {

        new Thread(() -> {
            AppDataBase.getInstance(this).todoDAO().deleteTask(todo);

        }).start();

    }

    @Override
    public void editItem(Todo todo) {


        //Todo item = taskList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",todo.getId());
        bundle.putString("task", todo.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(),AddNewTask.TAG);

    }

    @Override
    public void completeItem(Todo todo) {
        new Thread(() -> {
            AppDataBase.getInstance(this).todoDAO().updateTask(todo);

        }).start();
    }

    public void getListTask(){

        new Thread(() -> {
            taskList = AppDataBase.getInstance(this).todoDAO().getTodoList();
            runOnUiThread(() -> {
                Collections.reverse(taskList);
                tasksAdapter.setTasks(taskList);

            });
        }).start();
    }


}