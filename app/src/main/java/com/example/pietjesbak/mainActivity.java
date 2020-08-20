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
    TextView name1, name2, rollsText, score1, score2, tally1, tally2;
    CheckBox check1, check2, check3;
    ImageView dice1, dice2, dice3;

    int rollsAmount = 3;
    int score = 0, total1, total2, total3, totalScore, scorePlayer1, scorePlayer2;
    int tallyPlayer1 = 7, tallyPlayer2 = 7;
    int value1, value2, value3;
    Boolean activePlayer1 = true;
    Boolean zand = false;
    Boolean apen = false;
    Boolean soixneuf;

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
        rollsText = findViewById(R.id.rollsText);
        stoef = findViewById(R.id.stoef);
        dice1 = findViewById(R.id.dice1);
        dice2 = findViewById(R.id.dice2);
        dice3 = findViewById(R.id.dice3);
        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        tally1 = findViewById(R.id.tally1);
        tally2 = findViewById(R.id.tally2);

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
                } else if(rollsAmount == 3){
                    firstRoll();
                } else if(rollsAmount == 2){
                    secondRoll();
                } else if(rollsAmount == 1){
                    thirdRoll();
                } else {
                //if player rolled 3 times
                    // switch players
                    if(activePlayer1 == true){
                        rollsAmount = 3;
                        activePlayer1 = false;
                        name1.setTextColor(getResources().getColor(R.color.white));
                        name2.setTextColor(getResources().getColor(R.color.blue));
                        rollsText.setText(rollsAmount + " rolls left");
                        resetDice();
                    } else {
                        rollsAmount = 3;
                        activePlayer1 = true;
                        name1.setTextColor(getResources().getColor(R.color.blue));
                        name2.setTextColor(getResources().getColor(R.color.white));
                        rollsText.setText(rollsAmount + " rolls left");
                        resetDice();
                        clearScore();
                        //when round ends, compare the scores and determine winner
                        checkScores();
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
                        rollsText.setText(rollsAmount + " rolls left");
                        activePlayer1 = false;
                    } else {
                        name1.setTextColor(getResources().getColor(R.color.blue));
                        name2.setTextColor(getResources().getColor(R.color.white));
                        rollsAmount = stoefRolls;
                        rollsText.setText(rollsAmount + " rolls left");
                        activePlayer1 = true;
                        clearScore();
                    }
                }
            }
        });
    }

    public void firstRoll(){
        rollsAmount -= 1;
        rollsText.setText(rollsAmount + " rolls left");
        // call rollDice() function
        rollDice();
        calcScore();
        total1 = score;
        totalScore = total1;
        writeScore();
    }

    public void secondRoll(){
        rollsAmount -= 1;
        rollsText.setText(rollsAmount + " rolls left");
        // call rollDice() function
        rollDice();
        calcScore();
        total2 = score;
        totalScore = total1 + total2;
        writeScore();
    }

    public void thirdRoll(){
        rollsAmount -= 1;
        rollsText.setText(rollsAmount + " rolls left");
        // call rollDice() function
        rollDice();
        calcScore();
        total3 = score;
        totalScore = total1 + total2 + total3;
        writeScore();
    }

    public void writeScore(){
        if(activePlayer1 == true){
            score1.setText(totalScore + " points");
            scorePlayer1 = totalScore;
            Log.d("score", "Player1: " + scorePlayer1 + " points");
        } else {
            score2.setText(totalScore + " points");
            scorePlayer2 = totalScore;
            Log.d("score", "Player2: " + scorePlayer2 + " points");
        }
    }

    public void clearScore(){
        score1.setText("0 points");
        score2.setText("0 points");
        zand = false;
        apen = false;
        soixneuf = false;
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
                Log.d("score", String.valueOf(value1));
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
                Log.d("score", String.valueOf(value2));
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
                Log.d("score", String.valueOf(value3));
            } else {
            }
        }
    }

    public void calcScore(){
        score = 0;
        // calculate the score
        switch(value1){
            case 100:
                score += 100;
                break;
            case 2:
                score += 2;
                break;
            case 3:
                score += 3;
                break;
            case 4:
                score += 4;
                break;
            case 5:
                score += 5;
                break;
            case 60:
                score += 60;
                break;
        }

        switch(value2){
            case 100:
                score += 100;
                break;
            case 2:
                score += 2;
                break;
            case 3:
                score += 3;
                break;
            case 4:
                score += 4;
                break;
            case 5:
                score += 5;
                break;
            case 60:
                score += 60;
                break;
        }

        switch(value3){
            case 100:
                score += 100;
                break;
            case 2:
                score += 2;
                break;
            case 3:
                score += 3;
                break;
            case 4:
                score += 4;
                break;
            case 5:
                score += 5;
                break;
            case 60:
                score += 60;
                break;
        }

        if(value1 == value2 && value2 == value3){
            if(score == 300){
                apen = true;
            } else {
                zand = true;
            }
        } else if (score == 69){
            soixneuf = true;
        }
    }

    public void resetDice(){
        dice1.setImageResource(R.drawable.dice1);
        dice2.setImageResource(R.drawable.dice1);
        dice3.setImageResource(R.drawable.dice1);
    }

    public void checkScores(){
        if(scorePlayer1 > scorePlayer2){
            if(apen){
                if(tallyPlayer1 < 7) {
                    tallyPlayer1 = 0;
                    tally1.setText(tallyPlayer1 + " lines");
                } else {
                    tallyPlayer2 = 0;
                    tally2.setText(tallyPlayer2 + " lines");
                }
            } else if(soixneuf){
                tallyPlayer1 -= 3;
                tally1.setText(tallyPlayer1 + " lines");
            } else if(zand){
                tallyPlayer1 -= 2;
                tally1.setText(tallyPlayer1 + " lines");
            } else {
                tallyPlayer1 -= 1;
                tally1.setText(tallyPlayer1 + " lines");
            }

            Toast.makeText(getApplicationContext(), "Player one wins this round. Tally updated.", Toast.LENGTH_SHORT).show();
        } else {
            if(apen){
                if(tallyPlayer2 < 7){
                    tallyPlayer2 = 0;
                    tally2.setText(tallyPlayer2 + " lines");
                } else {
                    tallyPlayer1 = 0;
                    tally1.setText(tallyPlayer1 + " lines");
                }
            }else if(soixneuf){
                tallyPlayer2 -= 3;
                tally2.setText(tallyPlayer2 + " lines");
            }else if(zand){
                tallyPlayer2 -= 2;
                tally2.setText(tallyPlayer2 + " lines");
            }else {
                tallyPlayer2 -= 1;
                tally2.setText(tallyPlayer2 + " lines");
            }
            Log.d("score", tallyPlayer1 + " " + tallyPlayer2);
            Log.d("score", apen + " "+ zand + " " +soixneuf);
            Toast.makeText(getApplicationContext(), "Player two wins this round. Tally updated.", Toast.LENGTH_SHORT).show();
        }
    }

    public static int randomValue(){
        Random r = new Random();
        return r.nextInt(6)+1;
    }
}
