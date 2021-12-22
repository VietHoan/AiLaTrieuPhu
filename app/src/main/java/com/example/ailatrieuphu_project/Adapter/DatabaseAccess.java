package com.example.ailatrieuphu_project.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ailatrieuphu_project.Model.QuestionData;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context){
        this.openHelper = new DataBaseOpenHelper(context,"Question.db");
    }

    public static DatabaseAccess getInstance(Context context){
        if (instance==null) instance = new DatabaseAccess(context);
        return instance;
    }

    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    public void close(){
        if (database!=null)
            this.database.close();
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }

    public QuestionData getQuestion(int level){
        QuestionData questionData = new QuestionData();
        if (level<=15){
            open();
            if (level<=5){
                level = 1;
            }
            Cursor cursor = database.rawQuery("Select * From Question where level = " + level + " ORDER BY RANDOM()",null);
            cursor.moveToFirst();
            questionData.setQuestion(cursor.getString(0));
            questionData.setId(cursor.getInt(1));
            questionData.setLevel(cursor.getString(2));
            questionData.setCaseA(cursor.getString(3));
            questionData.setCaseB(cursor.getString(4));
            questionData.setCaseC(cursor.getString(5));
            questionData.setCaseD(cursor.getString(6));
            questionData.setTrueCase(cursor.getInt(7));
            cursor.moveToNext();
            cursor.close();

        }
        return questionData;
    }
}
