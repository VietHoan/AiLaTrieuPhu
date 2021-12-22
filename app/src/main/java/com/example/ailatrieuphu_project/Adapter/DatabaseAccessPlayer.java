package com.example.ailatrieuphu_project.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ailatrieuphu_project.Model.Player;

import java.util.ArrayList;

public class DatabaseAccessPlayer {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccessPlayer instance;

    private DatabaseAccessPlayer(Context context){
        this.openHelper = new DataBaseOpenHelper(context,"Player.db");
    }

    public static DatabaseAccessPlayer getInstance(Context context){
        if (instance==null) instance = new DatabaseAccessPlayer(context);
        return instance;
    }

    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    public void close(){
        if (database!=null) database.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void insertPlayer(int id){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        database.insert("Player",null,contentValues);
    }

    public void insertPlayer(int id,int score){
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("Score",score);
        database.insert("Player",null,contentValues);
    }

    public ArrayList<Player> getIdPlayer(){
        ArrayList<Player> playerArrayList = new ArrayList<>();
        open();
        Cursor cursor = database.rawQuery("Select ID From Player",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Player player = new Player(cursor.getInt(0));
            playerArrayList.add(player);
            cursor.moveToNext();
        }
        return playerArrayList;
    }

    public ArrayList<Integer> getScore(int id){
        ArrayList<Integer> scoreList = new ArrayList();
        int score = 0;
        open();
        Cursor cursor = database.rawQuery("Select Distinct Score From Player Where ID = " + id +
                                                " Order by Score DESC Limit 5",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            score = cursor.getInt(0);
            scoreList.add(score);
            cursor.moveToNext();
        }
        return scoreList;
    }
}
