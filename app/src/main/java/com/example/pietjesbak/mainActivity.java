package com.example.pietjesbak;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;


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

    }
}
