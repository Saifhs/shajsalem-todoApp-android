package com.saif.todoapp.ui;




import com.saif.todoapp.Todo;

public interface TodoListener {


    public void deleteItem(Todo todo);
    public void editItem(Todo todo);
    public void completeItem(Todo todo);



}
