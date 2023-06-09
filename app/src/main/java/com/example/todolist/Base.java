package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Base extends SQLiteOpenHelper {



    public Base(@Nullable Context context) {
        super(context, "gestionne", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE task(id INTEGER PRIMARY KEY,textTask TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS task");
        onCreate(db);
    }

    public  void insetTask(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c= new ContentValues();
        c.put("textTask",text);
        db.insert("task",null,c);
        db.close();
    }

    public Cursor getAllTask(){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM task",null);


        return c;
    }

    public void deletTask(int id){
        SQLiteDatabase db= this.getWritableDatabase();

        db.delete("task","id=?",new String[]{String.valueOf(id)});

        db.close();

    }

    public void modifierTask(String text,int idTask){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put("textTask",text);

        db.update("task",c,"id=?",new String[]{String.valueOf(idTask)});
        db.close();

    }

}
