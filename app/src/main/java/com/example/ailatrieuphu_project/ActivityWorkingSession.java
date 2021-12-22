package com.example.ailatrieuphu_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ailatrieuphu_project.Adapter.DatabaseAccess;
import com.example.ailatrieuphu_project.Adapter.DatabaseAccessPlayer;
import com.example.ailatrieuphu_project.Model.QuestionData;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ActivityWorkingSession extends AppCompatActivity implements View.OnClickListener {
    private TextView txtWelcomeID;
    private Button btnPlayGame;
    private Button btnShowHighScore;
    private String ID;
    private ArrayList<Integer> listScore = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_session);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtWelcomeID = findViewById(R.id.txt_welcomeID);
        btnPlayGame = findViewById(R.id.btn_playGame);
        btnShowHighScore = findViewById(R.id.btn_highScore);
        btnPlayGame.setOnClickListener(this);
        btnShowHighScore.setOnClickListener(this);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        System.out.println(ID);
        txtWelcomeID.setText("Welcome player " + ID);

        listScore = DatabaseAccessPlayer.getInstance(this).getScore(Integer.valueOf(ID));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_playGame:
                Intent intent = new Intent(ActivityWorkingSession.this,PlayGameActivity.class);
                intent.putExtra("ID",ID);
                startActivity(intent);
                break;
            case R.id.btn_highScore:
                Dialog dialog = new Dialog(this);
                dialog.setCancelable(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_highscore);
                TextView[] textViews = new TextView[4];
                int[] textviewHighScore = {R.id.txt_score1,R.id.txt_score2,R.id.txt_score3,R.id.txt_score4};
                for (int i = 0; i < textViews.length; i++) {
                    textViews[i] = dialog.findViewById(textviewHighScore[i]);
                }
                for (int i = 0; i < listScore.size()-1; i++) {
                    textViews[i].setText(String.valueOf(listScore.get(i)));
                }
                Button btnOkHighScore = dialog.findViewById(R.id.btn_okHighScore);
                btnOkHighScore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}