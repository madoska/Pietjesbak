package com.example.pietjesbak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;


public class mainActivity extends Activity {

    Button quit, roll, stoef;
    TextView name1, name2, rollsLeft, score1, score2;
    int value1, value2, value3;
    CheckBox check1, check2, check3;
    ImageView dice1, dice2, dice3;

    int rollsAmount = 3;
    int score = 0;
    Boolean turnPlayer1 = true, activePlayer1 = true, activePlayer2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quit = findViewById(R.id.quit);
        roll = findViewById(R.id.roll);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        rollsLeft = findViewById(R.id.rollsLeft);
        stoef = findViewById(R.id.stoef);
        dice1=findViewById(R.id.dice1);
        dice2=findViewById(R.id.dice2);
        dice3=findViewById(R.id.dice3);
        score1=findViewById(R.id.score1);
        score2=findViewById(R.id.score2);

        // set names of player textviews to values of bundles with keys "player1" and "player2"
        name1.setText(getIntent().getExtras().getString("player1"));
        name2.setText(getIntent().getExtras().getString("player2"));

        // when quit is pressed --
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create new Intent object to return to login
                Intent quit = new Intent(mainActivity.this, login.class);
                startActivity(quit);
            }
        });

        // when tapping "roll"
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for the first roll, check that all dices have been selected
                if(rollsAmount == 3 && (check1.isChecked() || check2.isChecked() || check3.isChecked())){
                    Toast.makeText(getApplicationContext(), "You must roll all dices in the first roll. Please uncheck all boxes.", Toast.LENGTH_SHORT).show();
                } else {
                    // lower rollsAmount by 1 and adjust textview accordingly
                    rollsAmount -= 1;
                    rollsLeft.setText(rollsAmount + " rolls left");
                }
            }
        });
    }
}
