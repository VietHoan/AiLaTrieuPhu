package com.example.ailatrieuphu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ailatrieuphu_project.Adapter.DatabaseAccess;
import com.example.ailatrieuphu_project.Model.QuestionData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<QuestionData> dataArrayList;
    private TextView textView;
    private DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.txt_test);
        databaseAccess = new DatabaseAccess(getApplicationContext());
        dataArrayList = new ArrayList<>();
        dataArrayList = databaseAccess.getInstance(getApplicationContext()).getData();
        textView.setText(dataArrayList.get(0).getId());
    }
}