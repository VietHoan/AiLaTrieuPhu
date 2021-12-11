package com.example.ailatrieuphu_project.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ailatrieuphu_project.Model.QuestionData;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Question.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<QuestionData> getData(){
        ArrayList<QuestionData> dataArrayList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * From Question",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            QuestionData questionData = new QuestionData(cursor.getString(1),cursor.getInt(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5),
                    cursor.getString(6),cursor.getString(7),cursor.getInt(8));
            dataArrayList.add(questionData);
            cursor.moveToNext();
        }
        cursor.close();
        return dataArrayList;
    }
}
