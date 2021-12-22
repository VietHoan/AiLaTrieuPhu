package com.example.ailatrieuphu_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.ailatrieuphu_project.Adapter.DatabaseAccess;
import com.example.ailatrieuphu_project.Adapter.DatabaseAccessPlayer;
import com.example.ailatrieuphu_project.Model.QuestionData;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[] btn = new Button[4];
    private int[] btn_id = {R.id.btn_caseA,R.id.btn_caseB,R.id.btn_caseC,R.id.btn_caseD};
    private static int trueCase;
    private String[] answer = new String[4];
    private QuestionData questionData = new QuestionData();
    private int level = 1;
    private static String[] total = {"0","200.000", "400.000","600.000","1.000.000","2.000.000","3.000.000",
            "6.000.000","10.000.000","14.000.000","22.000.000","30.000.000","40.000.000","60.000.000",
            "85.000.000","150.000.000"};

    private TextView txtQuestion;
    private TextView txtTotal;
    private TextView txtNumberQuestion;
    private TextView txtCountDownTimer;

    private Button btn_50_50Help;
    private boolean hasUse50_50 = false;
    private Button btn_PassQuestion;
    private boolean hasUesPassQuestion = false;
    private Button btn_ChangeQuestion;
    private boolean hasUesChangeQuestion = false;

    private String idPlayer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Intent intent = getIntent();
        idPlayer = intent.getStringExtra("ID");
        System.out.println("This " + idPlayer);
        initUI();
        showQuestion(level);
        showTotal(level);
        countDownTimer = setCountDownTimer();
        countDownTimer.start();

    }

    public void initUI(){
        txtTotal = findViewById(R.id.txt_total);
        txtQuestion = findViewById(R.id.txt_question);
        txtNumberQuestion = findViewById(R.id.txt_numberQuestion);
        txtCountDownTimer = findViewById(R.id.txt_countDownTimer);

        btn_50_50Help = findViewById(R.id.btn_50_50Help);
        btn_ChangeQuestion = findViewById(R.id.btn_changeQuestionHelp);
        btn_PassQuestion = findViewById(R.id.btn_passHelp);

        btn_50_50Help.setOnClickListener(this);
        btn_ChangeQuestion.setOnClickListener(this);
        btn_PassQuestion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int position;
        switch (v.getId()){
            case R.id.btn_caseA:
                position = 1;
                selectedBtn(position);
                break;
            case R.id.btn_caseB:
                position = 2;
                selectedBtn(position);
                break;
            case R.id.btn_caseC:
                position = 3;
                selectedBtn(position);
                break;
            case R.id.btn_caseD:
                position = 4;
                selectedBtn(position);
                break;
            case R.id.btn_50_50Help:
                if (hasUse50_50){
                    showDialog("This help has been used before, please choose another helping","ok","");
                }
                else {
                    int trueCase = questionData.getTrueCase() -1;
                    Random random = new Random();
                    int wrongCase1;
                    int wrongCase2;
                    do {
                        wrongCase1 = random.nextInt(4);
                        wrongCase2 = random.nextInt(4);
                    }while (!(wrongCase1!= trueCase && wrongCase2 != trueCase && wrongCase1!=wrongCase2));
                    System.out.println("True case: " + trueCase);
                    System.out.println("w1: " + wrongCase1);
                    System.out.println("w2: " + wrongCase2);
                    btn[wrongCase1].setText("");
                    btn[wrongCase2].setText("");
                    hasUse50_50 = true;
                }
                break;
            case R.id.btn_changeQuestionHelp:
                if (hasUesChangeQuestion){
                    showDialog("This help has been used before, please choose another helping","ok","");
                }
                else {
                    showQuestion(level);
                    hasUesChangeQuestion = true;
                }
                break;
            case R.id.btn_passHelp:
                if (hasUesPassQuestion){
                    showDialog("This help has been used before, please choose another helping","ok","");
                }
                else {
                    showAskAudienceHelp();
                    hasUesPassQuestion = true;
                }
                break;
        }
    }

    public void selectedBtn(int position){
        if (position == trueCase){
            level++;
            hasWon(level);
            System.out.println("---------" + level);
            showQuestion(level);
            showTotal(level);
            showCountDownTimer();
        }
        else {
            insertPlayer();
            countDownTimer.cancel();
            showDialog("Wrong answer, please choose:","Quit","New game");
        }
    }

    public void setAnswerCase(String[] answer){
        for (int i = 0; i < btn.length; i++) {
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setText(answer[i]);
            btn[i].setOnClickListener(this);
        }
    }

    public void showQuestion(int level){
        System.out.println("//lv lay cau hoi " + level);
        questionData = DatabaseAccess.getInstance(getApplicationContext()).getQuestion(level);
        System.out.println("Đáp án " + questionData.getTrueCase());
        showNumberQuestion(level);
        txtQuestion.setText(questionData.getQuestion());
        answer[0] = "A. " + questionData.getCaseA();
        answer[1] = "B. " + questionData.getCaseB();
        answer[2] = "C. " + questionData.getCaseC();
        answer[3] = "D. " + questionData.getCaseD();
        trueCase = questionData.getTrueCase();
        setAnswerCase(answer);
    }

    public void showTotal(int level){
        txtTotal.setText("Phần thưởng: " + total[level-1]);
    }

    public void showNumberQuestion(int level){
        txtNumberQuestion.setText("Câu " + level);
    }

    public void showDialog(String message, String textNegative, String textPositive){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setMessage(message);
        if(!textNegative.equals("")){
            builder.setNegativeButton(textNegative, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (textNegative.equals("Quit")){
                        finish();
                    }
                    dialog.cancel();
                }
            });
        }
        if (!textPositive.equals("")){
            builder.setPositiveButton(textPositive, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (textPositive.equals("New game")){
                        level = 1;
                        showQuestion(level);
                        showTotal(level);
                        setNewGame();
                        showCountDownTimer();
                    }
                    if (textPositive.equals("Continue")){
                        dialog.cancel();
                    }
                }
            });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setNewGame(){
        hasUse50_50 = false;
        hasUesChangeQuestion = false;
        hasUesPassQuestion = false;
    }

    public void hasWon(int level){
        if (level == 16){
            insertPlayer();
            showDialog("Congratulations, you have won 150.000.000 VNĐ","Quit","New game");
        }
    }

    public CountDownTimer setCountDownTimer(){
        long duration = TimeUnit.SECONDS.toMillis(30);
        CountDownTimer countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String sDuration = String.format(Locale.ENGLISH,"%02d",
                        (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                txtCountDownTimer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                showDialog("Timeout, please choose:","Quit","New game");
            }
        };
        return countDownTimer;
    }

    public void showCountDownTimer(){
        countDownTimer.cancel();
        countDownTimer = setCountDownTimer();
        countDownTimer.start();
    }

    public void showAskAudienceHelp(){
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_askaudience_help);
        int[] percentAnswer = {R.id.percentA,R.id.percentB,R.id.percentC,R.id.percentD};
        TextView[] textViews = new TextView[4];
        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = dialog.findViewById(percentAnswer[i]);
        }
        int trueCase = questionData.getTrueCase() -1;
        Random random = new Random();
        int wrongCase1;
        int wrongCase2;
        int wrongCase3;
        do {
            wrongCase1 = random.nextInt(4);
            wrongCase2 = random.nextInt(4);
            wrongCase3 = random.nextInt(4);
        }while (!(wrongCase1!= trueCase && wrongCase2 != trueCase && wrongCase3!=trueCase &&
                wrongCase1!=wrongCase2 && wrongCase2!=wrongCase3 && wrongCase3!=wrongCase1));
        int percentTrue;
        int percentFalse1;
        int percentFalse2;
        int percentFalse3;
        if (level>=1 && level <=5){
            percentTrue = random.nextInt(30) + 70;
            percentFalse1 = random.nextInt(5);
            percentFalse2 = random.nextInt(6);
            percentFalse3 = 100-percentTrue - percentFalse1 - percentFalse2;
            textViews[trueCase].setText(String.valueOf(percentTrue)+" %");
            textViews[trueCase].setHeight(percentTrue*10);

            textViews[wrongCase1].setText(String.valueOf(percentFalse1)+" %");
            textViews[wrongCase1].setHeight(percentFalse1*10);

            textViews[wrongCase2].setText(String.valueOf(percentFalse2)+" %");
            textViews[wrongCase2].setHeight(percentFalse2*10);

            textViews[wrongCase3].setText(String.valueOf(percentFalse3)+" %");
            textViews[wrongCase3].setHeight(percentFalse3*10);
        }
        if (level>5 && level <=8){
            percentTrue = random.nextInt(10) + 30;
            percentFalse1 = random.nextInt(5) + 20;
            percentFalse2 = random.nextInt(10) + 5;
            percentFalse3 = 100-percentTrue - percentFalse1 - percentFalse2;
            textViews[trueCase].setText(String.valueOf(percentTrue)+" %");
            textViews[trueCase].setHeight(percentTrue*10);

            textViews[wrongCase1].setText(String.valueOf(percentFalse1)+" %");
            textViews[wrongCase1].setHeight(percentFalse1*10);

            textViews[wrongCase2].setText(String.valueOf(percentFalse2)+" %");
            textViews[wrongCase2].setHeight(percentFalse2*10);

            textViews[wrongCase3].setText(String.valueOf(percentFalse3)+" %");
            textViews[wrongCase3].setHeight(percentFalse3*10);
        }
        if (level>8 && level <= 15){
            percentTrue = random.nextInt(5) + 20;
            percentFalse1 = random.nextInt(5) + 20;
            percentFalse2 = random.nextInt(5) + 20;
            percentFalse3 = 100 - percentTrue - percentFalse1 - percentFalse2;
            textViews[trueCase].setText(String.valueOf(percentTrue)+" %");
            textViews[trueCase].setHeight(percentTrue*10);

            textViews[wrongCase1].setText(String.valueOf(percentFalse1)+" %");
            textViews[wrongCase1].setHeight(percentFalse1*10);

            textViews[wrongCase2].setText(String.valueOf(percentFalse2)+" %");
            textViews[wrongCase2].setHeight(percentFalse2*10);

            textViews[wrongCase3].setText(String.valueOf(percentFalse3)+" %");
            textViews[wrongCase3].setHeight(percentFalse3*10);
        }

        Button btn_thanks = dialog.findViewById(R.id.btn_thanks);
        btn_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void insertPlayer(){
        int id = Integer.valueOf(idPlayer);
        String scoreString = total[level-1].replace('.',' ').replaceAll(" ","");
        int score = Integer.valueOf(scoreString);
        DatabaseAccessPlayer.getInstance(getApplicationContext()).insertPlayer(id,score);
    }

    @Override
    public void onBackPressed() {
        showDialog("Do you want to quit? This game wont save your score!!","Quit", "Continue");
    }
}