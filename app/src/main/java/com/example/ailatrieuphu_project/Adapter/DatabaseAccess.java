package com.example.ailatrieuphu_project.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ailatrieuphu_project.Model.QuestionData;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess databaseAccess;//instance

    public DatabaseAccess(Context context){
        this.sqLiteOpenHelper = new DatabaseOpenHelper(context);
    }

    public DatabaseAccess getInstance(Context context){
        if (databaseAccess==null){
            databaseAccess = new DatabaseAccess(context);
        }
        return databaseAccess;
    }

    public void open(){
        this.database = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        if (database!=null){
            this.database.close();
        }
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }

    public ArrayList<QuestionData> getData(){
        ArrayList<QuestionData> dataArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * From 'Question'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            QuestionData questionData = new QuestionData(cursor.getString(1),cursor.getInt(2),
                                            cursor.getString(3),cursor.getString(4),cursor.getString(5),
                                            cursor.getString(6),cursor.getString(7),cursor.getInt(8));
            dataArrayList.add(questionData);
            cursor.moveToNext();
        }
        return dataArrayList;
    }
}
