package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pong.custiomView.Ball;
import com.example.pong.custiomView.Paddle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    //Game Layout
    private FrameLayout gameFrame;
    private LinearLayout startLayout;

    //Game Elements
    private Ball ball;
    private Paddle paddleLeft, paddleRight;
    private TextView scoreLeft, scoreRight, gameTitle, authorTitle;
    private View net;

    //Size
    private int frameHeight = 0;
    private int frameWidth = 0;
    private int paddleHeight = 0;
    private int paddleWidth = 0;


    //Position
    private float paddleLeftY, paddleRightY, paddleLeftX, paddleRightX;
    private float ballX, ballY;

    //Speed
    private int paddleLeftSpeed;
    //Score

    //Timer
    private Timer timer;
    private Handler handler = new Handler();

    //Status
    private boolean start_flag = false;
    private boolean action_flag = false;
    private boolean up_flag = false;

    //Sound

    //Start Button
    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ini();

        //Set visibility  --> GONE: cant see it, and doesn't take any space in Layout, INVISIBLE: cant see it but it has space in Layout
        net.setVisibility(View.INVISIBLE);
        ball.setVisibility(View.INVISIBLE);
        paddleRight.setVisibility(View.INVISIBLE);
        paddleLeft.setVisibility(View.INVISIBLE);
        scoreRight.setVisibility(View.INVISIBLE);
        scoreLeft.setVisibility(View.INVISIBLE);
    }

    protected void ini() {
        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        net = findViewById(R.id.net);
        ball = findViewById(R.id.ball);
        paddleLeft = findViewById(R.id.paddleLeft);
        paddleRight = findViewById(R.id.paddleRight);
        scoreLeft = findViewById(R.id.score1pl);
        scoreRight = findViewById(R.id.score2pl);
        gameTitle = findViewById(R.id.gameTitle);
        authorTitle = findViewById(R.id.author);
        startButton = findViewById(R.id.startButton);

    }

    public void startGame(View view) {
        start_flag = true;

        // getting frameHeight and frameWidth
        if (frameHeight == 0) {
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            paddleHeight = paddleLeft.getHeight();
            paddleWidth = paddleLeft.getWidth();
        }

        //initial positions         last - paddleHeight bc of my phone
        paddleLeftY = frameHeight / 2 - paddleHeight / 2;
        paddleRightY = frameHeight / 2 - paddleHeight / 2;
        paddleLeftX = paddleWidth;
        paddleRightX = frameWidth - 2 * paddleWidth;

        paddleLeft.setX(paddleLeftX);
        paddleLeft.setY(paddleLeftY);
        paddleRight.setX(paddleRightX);
        paddleRight.setY(paddleRightY);


        ballX = frameWidth / 2;
        ballY = frameHeight / 2;

        ball.setY(ballY);
        ball.setX(ballX);

        //Set visibility
        net.setVisibility(View.VISIBLE);
        ball.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        authorTitle.setVisibility(View.INVISIBLE);
        gameTitle.setVisibility(View.INVISIBLE);
        paddleLeft.setVisibility(View.VISIBLE);
        paddleRight.setVisibility(View.VISIBLE);
        scoreRight.setVisibility(View.VISIBLE);
        scoreLeft.setVisibility(View.VISIBLE);

        //Set Timer

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (start_flag) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            move();
                        }
                    });
                }
            } // call move() every 20 ms
        }, 0, 100);


        //Initialize score


    }

    private void move() {

        if (action_flag) {
            //touching
            paddleLeftY += 5;
        } else {
            //releasing
            paddleLeftY -= 5;
        }

        //check  paddle position
        if (paddleLeftY < 0) {
            paddleLeftY = 0;
        }

        if (paddleLeftY > frameHeight - paddleHeight) {
            paddleLeftY = frameHeight - paddleHeight;
        }

        paddleLeft.setY(paddleLeftY);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (start_flag) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flag = true;

            } else if (event.getAction() == MotionEvent.ACTION_UP){
                action_flag = false;
            }
        }
        return super.onTouchEvent(event);

    }

}