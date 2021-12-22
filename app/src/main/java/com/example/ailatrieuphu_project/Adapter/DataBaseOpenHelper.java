package com.example.ailatrieuphu_project.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseOpenHelper extends SQLiteAssetHelper {

    public DataBaseOpenHelper(Context context, String DB_NAME) {
        super(context,DB_NAME,null,1);
    }
}
