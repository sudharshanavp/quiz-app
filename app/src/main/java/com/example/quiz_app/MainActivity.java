package com.example.quiz_app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button button0, button1, button2, button3, playagain, gobutton;
    TextView question, accuracy, time, score;
    ArrayList<Integer> answers = new ArrayList<>();
    String[] operators = {"*", "/", "+", "-", "%"};
    int chooseLoc, a, b, operator, points = 0, numOfQues = 0;
    Random random = new Random();
    CountDownTimer countDownTimer;
    ConstraintLayout gameLayout;

    //    private Map actionButtons = new HashMap<Button, String>();
    private List<Button> actionButtons = new ArrayList(10);

    public void goButton(View view) {
        gobutton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        newQuestion();
        timer();
    }

    public void playAgain(View view) {
        points = 0;
        numOfQues = 0;
        score.setText("0/0");
        time.setText("30s");
        setBtnClickables(true);
        playagain.setVisibility(View.INVISIBLE);
        accuracy.setVisibility(View.INVISIBLE);

        newQuestion();
        timer();
    }

    public void timer() {
        countDownTimer = new CountDownTimer(25100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                accuracy.setText("Done");
                playagain.setVisibility(View.VISIBLE);
                setBtnClickables(false);
            }
        }.start();

    }

    public void chooseAnswer(View view) {
        accuracy.setVisibility(View.VISIBLE);
        if (Integer.toString(chooseLoc).equals(view.getTag().toString())) {
            accuracy.setText("Correct");
            points++;

        } else {
            accuracy.setText("Wrong :(");
        }
        numOfQues++;
        score.setText(points + "/" + numOfQues);
        newQuestion();
    }

    public void newQuestion() {
        a = (int) Math.floor(Math.random() * (20 - 1 + 1) + 1);
        b = (int) Math.floor(Math.random() * (20 - 2 + 1) + 2);
        operator = random.nextInt(5);

        question.setText(a + " " + operators[operator] + " " + b);
        chooseLoc = random.nextInt(4);
        answers.clear();

        int result, res;
        switch (operators[operator]) {
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "%":
                result = a % b;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operators[operator]);
        }
        res = result;
        if (result < 0) res = -result;
        for (int i = 0; i < 4; i++) {
            if (i == chooseLoc) {
                answers.add(result);
            } else {
                int wrongAns;
                wrongAns = random.nextInt(res + 10);
                while (result == wrongAns || answers.contains(wrongAns)) {
                    wrongAns = random.nextInt(res + 10);
                }
                if (result < 0) {
                    answers.add(-wrongAns);
                } else {
                    answers.add(wrongAns);
                }
            }
        }

        setBtnOptions();
    }

    private void setBtnOptions() {
        for (int i = 0; i < actionButtons.size(); i++) {
            actionButtons.get(i).setText(Integer.toString(answers.get(i)));
        }
    }

    private void setBtnClickables(boolean value){

        for (int i = 0; i < actionButtons.size(); i++) {
            actionButtons.get(i).setClickable(value);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLayout = findViewById(R.id.gamelayout);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button8);
        actionButtons.add(button0);
        actionButtons.add(button1);
        actionButtons.add(button2);
        actionButtons.add(button3);
        gobutton = findViewById(R.id.gobutton);
        playagain = findViewById(R.id.playagain);

        question = findViewById(R.id.question);
        accuracy = findViewById(R.id.accuracy);
        time = findViewById(R.id.time);
        score = findViewById(R.id.score);
        accuracy.setVisibility(View.INVISIBLE);

    }

}
