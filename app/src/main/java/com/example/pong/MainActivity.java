package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pong.custiomView.Ball;
import com.example.pong.custiomView.Paddle;

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

    //Score

    //Status
    private boolean start_flag = false;
    private boolean action_flag = false;

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



/*        //doesnt play much of a role yet
        Ball ball = new Ball(this);
        Paddle paddle1 = new Paddle(this , 10, 500);
        Paddle paddle2 = new Paddle(this, 2500, 500);*/
    }

    protected void ini(){
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

    public void startGame(View view){
        start_flag = true;

        // getting frameHeight and frameWidth
        if (frameHeight == 0){
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            paddleHeight = paddleLeft.getHeight();
            paddleWidth = paddleLeft.getWidth();
        }

        //initial positions
        paddleLeftY = frameHeight / 2;
        paddleRightY = frameHeight /2;
        paddleLeftX = paddleWidth;
        paddleRightX = frameWidth - 2 * paddleWidth;

        ballX = frameHeight / 2;
        ballY = frameWidth / 2;

        paddleLeft.setY(paddleLeftY);
        paddleLeft.setX(paddleLeftX);

        paddleRight.setY(paddleRightY);
        paddleRight.setX(paddleRightX);

        ball.setY(ballY);
        ball.setX(ballX);

        //Set visibility
        net.setVisibility(View.VISIBLE);
        ball.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        authorTitle.setVisibility(View.INVISIBLE);
        gameTitle.setVisibility(View.INVISIBLE);

        //Initialize score


    }



}