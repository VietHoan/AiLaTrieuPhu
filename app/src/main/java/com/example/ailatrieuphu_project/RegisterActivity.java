package com.example.ailatrieuphu_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ailatrieuphu_project.Adapter.DatabaseAccessPlayer;
import com.example.ailatrieuphu_project.Model.Player;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private EditText txt_inputID;
    private Button btn_confirmRegister;
    private ArrayList<Player> playerArrayList;
    private boolean hasPlayer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        playerArrayList = new ArrayList<>();

        txt_inputID = findViewById(R.id.txt_register);
        btn_confirmRegister = findViewById(R.id.btn_confirmRegister);
        btn_confirmRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (txt_inputID.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please write an ID you want to regist",Toast.LENGTH_LONG).show();
                }
                else {
                    int idInput = Integer.valueOf(txt_inputID.getText().toString());
                    playerArrayList = DatabaseAccessPlayer.getInstance(getApplicationContext()).getIdPlayer();
                    for (int i = 0; i < playerArrayList.size(); i++) {
                        if (playerArrayList.get(i).getId() == idInput){
                            hasPlayer = true;
                            break;
                        }
                    }
                    if (hasPlayer){
                        showDialog("User ID: " + idInput + "already exist","Quit","Register again");
                    }
                    else {
                        DatabaseAccessPlayer.getInstance(getApplicationContext()).insertPlayer(idInput);
                        showDialog("Registed","","Ok");
                    }
                }
            }
        });
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
                    if (textPositive.equals("Register again")){
                        hasPlayer = false;
                        dialog.cancel();
                    }
                    if (textPositive.equals("Ok")){
                        finish();
                    }
                }
            });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }
}