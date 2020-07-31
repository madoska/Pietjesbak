package com.example.pietjesbak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Random;


public class mainActivity extends Activity {

    Button quit, roll, stoef;
    TextView name1, name2, rollsLeft, score1, score2;
    CheckBox check1, check2, check3;
    ImageView dice1, dice2, dice3;

    int rollsAmount = 3;
    int score = 0;
    int value1, value2, value3;
    Boolean activePlayer1 = true;


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
        dice1 = findViewById(R.id.dice1);
        dice2 = findViewById(R.id.dice2);
        dice3 = findViewById(R.id.dice3);
        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);

        // set names of player textviews to values of bundles with keys "player1" and "player2"
        name1.setText(getIntent().getExtras().getString("player1"));
        name1.setTextColor(getResources().getColor(R.color.blue));
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
                    // call rollDice() function
                    rollDice();
                    calcScore();
                }

                //if player rolled 3 times
                if(rollsAmount < 0){
                    // switch players
                    if(activePlayer1 == true){
                        rollsAmount = 3;
                        activePlayer1 = false;
                        name1.setTextColor(getResources().getColor(R.color.white));
                        name2.setTextColor(getResources().getColor(R.color.blue));
                        rollsLeft.setText(rollsAmount + " rolls left");
                        resetDice();
                    } else {
                        rollsAmount = 3;
                        activePlayer1 = true;
                        name1.setTextColor(getResources().getColor(R.color.blue));
                        name2.setTextColor(getResources().getColor(R.color.white));
                        rollsLeft.setText(rollsAmount + " rolls left");
                        resetDice();
                    }
                }
            }
        });

        // if player chooses to stoef
        stoef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if player2 taps stoef return error
                if(!activePlayer1){
                    Toast.makeText(getApplicationContext(),"Sorry, only player 1 can STOEF :(",Toast.LENGTH_SHORT).show();
                } else if(rollsAmount == 3){
                    // if player 1 taps stoef before rolling at least once
                    Toast.makeText(getApplicationContext(),"You must roll at least once before you can STOEF!",Toast.LENGTH_SHORT).show();
                } else {
                    // reset checkboxes
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);

                    // how many times can player2 throw? (total - rolls left)
                    int stoefRolls = 3 - rollsAmount;

                    if(activePlayer1 == true){
                        name1.setTextColor(getResources().getColor(R.color.white));
                        name2.setTextColor(getResources().getColor(R.color.blue));
                        rollsAmount = stoefRolls;
                        rollsLeft.setText(rollsAmount + " rolls left");
                        activePlayer1 = false;
                    } else {
                        name1.setTextColor(getResources().getColor(R.color.blue));
                        name2.setTextColor(getResources().getColor(R.color.white));
                        rollsAmount = stoefRolls;
                        rollsLeft.setText(rollsAmount + " rolls left");
                        activePlayer1 = true;
                    }
                }
            }
        });
    }

    public void rollDice(){
        for(int i = 0; i <= 3; i++){
            //check if first dice is rolled (box = unchecked)
            if(i==1 && !check1.isChecked()){
                value1 = randomValue();

                // display correct dice
                switch(value1){
                    case 1:
                        dice1.setImageResource(R.drawable.dice1);
                        value1 = 100;
                        break;

                    case 2:
                        dice1.setImageResource(R.drawable.dice2);
                        break;

                    case 3:
                        dice1.setImageResource(R.drawable.dice3);
                        break;

                    case 4:
                        dice1.setImageResource(R.drawable.dice4);
                        break;

                    case 5:
                        dice1.setImageResource(R.drawable.dice5);
                        break;

                    case 6:
                        dice1.setImageResource(R.drawable.dice6);
                        value1 = 60;
                        break;
                }
                Log.d("log", String.valueOf(value1));
            } else {
            }

            if(i == 2 && !check2.isChecked()){
                value2 = randomValue();

                switch(value2){
                    case 1:
                        dice2.setImageResource(R.drawable.dice1);
                        value2 = 100;
                        break;

                    case 2:
                        dice2.setImageResource(R.drawable.dice2);
                        break;

                    case 3:
                        dice2.setImageResource(R.drawable.dice3);
                        break;

                    case 4:
                        dice2.setImageResource(R.drawable.dice4);
                        break;

                    case 5:
                        dice2.setImageResource(R.drawable.dice5);
                        break;

                    case 6:
                        dice2.setImageResource(R.drawable.dice6);
                        value2 = 60;
                        break;
                }
                Log.d("log", String.valueOf(value2));
            } else {
            }

            if(i == 3 && !check3.isChecked()){
                value3 = randomValue();

                switch(value3){
                    case 1:
                        dice3.setImageResource(R.drawable.dice1);
                        value3 = 100;
                        break;

                    case 2:
                        dice3.setImageResource(R.drawable.dice2);
                        break;

                    case 3:
                        dice3.setImageResource(R.drawable.dice3);
                        break;

                    case 4:
                        dice3.setImageResource(R.drawable.dice4);
                        break;

                    case 5:
                        dice3.setImageResource(R.drawable.dice5);
                        break;

                    case 6:
                        dice3.setImageResource(R.drawable.dice6);
                        value3 = 60;
                        break;
                }
                Log.d("log", String.valueOf(value3));
            } else {
            }
        }
    }

    public void calcScore(){

    }

    public void resetDice(){
        dice1.setImageResource(R.drawable.dice1);
        dice2.setImageResource(R.drawable.dice1);
        dice3.setImageResource(R.drawable.dice1);
    }

    public static int randomValue(){
        Random r = new Random();
        return r.nextInt(6)+1;
    }
}
