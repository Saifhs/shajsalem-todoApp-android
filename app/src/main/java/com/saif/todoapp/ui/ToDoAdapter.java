package com.saif.todoapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.saif.todoapp.Todo;

import com.saif.todoapp.MainActivity;
import com.saif.todoapp.R;


import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<Todo> todoList;
    private MainActivity activity;

    private TodoListener listener;

    public ToDoAdapter(TodoListener listener, MainActivity activity){
        this.listener= listener;
        this.activity= activity;
    }

    public ToDoAdapter(TodoListener listener) {
        this.listener= listener;
    }


    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(@NonNull final  ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //db.openDatabase();
        final Todo item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setClickable(false);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onTaskCompleted(position , !toBoolean(item.getStatus()));
            }
        });


    }

    @Override
    public int getItemCount() {
        if (todoList == null) {
            return 0;
        }
        return todoList.size();
    }

    private boolean toBoolean(int n){
             return n!=0;
        }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<Todo> todoList){
        this.todoList=todoList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        Todo item = todoList.get(position);
        todoList.remove(position);
        notifyItemRemoved(position);
        listener.deleteItem(item);
    }

        public void editItem(int position){
        Todo item = todoList.get(position);
        listener.editItem(item);
    }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            CheckBox task;

            ViewHolder(View view){
                super(view);
                task = view.findViewById(R.id.todoCheckBox);
            }
        }
        private void onTaskCompleted(int position, boolean checked){
           Todo item = todoList.get(position);
            if (checked) {
                item.setStatus(1);
            }else{
                item.setStatus(0);
            }
           notifyItemChanged(position);
           listener.completeItem(item);
        }
}
