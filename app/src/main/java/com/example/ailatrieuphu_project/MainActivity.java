package com.example.ailatrieuphu_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ailatrieuphu_project.Adapter.DatabaseAccessPlayer;
import com.example.ailatrieuphu_project.Model.Player;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txt_loginInput;
    private Button btnLogIn;
    private Button btnRegister;
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private int idPlayer;
    private boolean hasPlayer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txt_loginInput = findViewById(R.id.txt_login);
        btnLogIn = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        playerArrayList = DatabaseAccessPlayer.getInstance(getApplicationContext()).getIdPlayer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                if (txt_loginInput.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please write your ID here",Toast.LENGTH_SHORT).show();
                }
                else {
                    idPlayer = Integer.valueOf(txt_loginInput.getText().toString());
                    for (int i = 0; i < playerArrayList.size(); i++) {
                        if (playerArrayList.get(i).getId()==idPlayer){
                            hasPlayer = true;
                            break;
                        }
                    }
                    if (hasPlayer){
                        Intent intent = new Intent(MainActivity.this, ActivityWorkingSession.class);
                        intent.putExtra("ID",String.valueOf(idPlayer));
                        startActivity(intent);
                        hasPlayer = false;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"This ID doesnt exist, please try agian",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    public ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public void setPlayerArrayList(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
    }
}