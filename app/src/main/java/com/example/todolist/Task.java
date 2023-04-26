package com.example.todolist;

public class Task {
    private int idTask;
    private  String textTask;

    public Task(int idTask, String textTask) {
        this.idTask = idTask;
        this.textTask = textTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getTextTask() {
        return textTask;
    }

    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }
}
