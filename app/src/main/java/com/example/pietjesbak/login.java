package com.example.pietjesbak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = findViewById(R.id.btnLogin);
        //when "Let's play" button is clicked --
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText player1 = findViewById(R.id.player1);
                EditText player2 = findViewById(R.id.player2);

                // get the values from input fields
                String name1 = player1.getText().toString();
                String name2 = player2.getText().toString();

                // check if values are empty & return error
                if(TextUtils.isEmpty(name1)){
                    player1.setError("What's your name, Player 1?");
                } else if(TextUtils.isEmpty(name2)){
                    player2.setError("Oh no! You forgot to enter your name, Player 2 :(");
                    // if not empty, then--
                } else {
                    // create and initialize intent object directed to main activity
                    Intent i = new Intent(login.this, mainActivity.class);

                    // pass player names to main activity
                    i.putExtra("player1", name1);
                    i.putExtra("player2", name2);

                    Log.d("log", name1 + " " + name2);

                    startActivity(i);
                }
            }
        });
    }
}
