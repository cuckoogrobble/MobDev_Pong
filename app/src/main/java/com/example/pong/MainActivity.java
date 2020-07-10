package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
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
    private TextView scoreLeftText, scoreRightText, gameTitle, authorTitle, gameOverText;
    private View net;

    //Size
    private int frameHeight = 0;
    private int frameWidth = 0;
    private int paddleHeight = 0;
    private int paddleWidth = 0;


    //Position
    private float paddleLeftY, paddleRightY, paddleLeftX, paddleRightX;
    private float ballX, ballY;

    //Speed paddles? do I need this???
    private int paddleLeftSpeed;

    //Ball Velocity and Speed
    private int xVel = -1 , yVel = -1, xSpeed = 5, ySpeed = 5;

    //Score
    private int scoreLeft = 0;
    private int scoreRight = 0;

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
        scoreRightText.setVisibility(View.INVISIBLE);
        scoreLeftText.setVisibility(View.INVISIBLE);
        gameOverText.setVisibility(View.INVISIBLE);
    }

    protected void ini() {
        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        net = findViewById(R.id.net);
        ball = findViewById(R.id.ball);
        paddleLeft = findViewById(R.id.paddleLeft);
        paddleRight = findViewById(R.id.paddleRight);
        scoreLeftText = findViewById(R.id.score1pl);
        scoreRightText = findViewById(R.id.score2pl);
        gameTitle = findViewById(R.id.gameTitle);
        authorTitle = findViewById(R.id.author);
        startButton = findViewById(R.id.startButton);
        gameOverText = findViewById(R.id.gameOverText);

    }

    public void startGame(View view) {

        //set score to 0
        resetScore();

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
        scoreRightText.setVisibility(View.VISIBLE);
        scoreLeftText.setVisibility(View.VISIBLE);
        gameOverText.setVisibility(View.INVISIBLE);

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

                            if (scoreLeft == 1 || scoreRight == 1){
                                gameStop();

                            }
                        }
                    });
                }
            } // call move() every 20 ms
        }, 0, 100);



    }

    private void resetScore() {
        scoreLeft = scoreRight = 0;
        scoreLeftText.setText(""+scoreLeft);
        scoreRightText.setText(""+scoreRight);
    }

    public void gameStop() {

        start_flag = false;
        startLayout.setVisibility(View.VISIBLE);
        ball.setVisibility(View.INVISIBLE);
        paddleLeft.setVisibility(View.INVISIBLE);
        paddleRight.setVisibility(View.INVISIBLE);
        net.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        gameTitle.setVisibility(View.VISIBLE);
        authorTitle.setVisibility(View.VISIBLE);
        gameOverText.setVisibility(View.VISIBLE);

    }

    public void move() {

        //moving ball up left

        ballX += (xVel * xSpeed);
        ballY += (yVel * ySpeed);

        //point for right paddle user and restart the game
        if (ballX < 0 ){
            scoreRight++;

            //update right score
            scoreRightText.setText(""+scoreRight);

            newBall();
        }

        //point for left paddle user and restart the game
        if  (ballX > frameWidth){
            scoreLeft++;

            //update left score
            scoreLeftText.setText(""+scoreLeft);

            newBall();
        }



        ball.setX(ballX);
        ball.setY(ballY);






        //moving paddle

        if (action_flag) {
            //touching  - moving down
            paddleLeftY += 5;
        } else {
            //releasing - moving up
            paddleLeftY -= 5;
        }

        //check paddle position to set it within the limits
        if (paddleLeftY < 0) paddleLeftY = 0;
        if (paddleLeftY > frameHeight - paddleHeight) paddleLeftY = frameHeight - paddleHeight;

        //move paddle
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

    private void changeXDir(){
        ballX += 12;
    }

    private void newBall(){
        //start from the middle
        ballX = (float)Math.floor(frameWidth/2);
        //start randomly from the middle
        ballY = (float)Math.floor(Math.random()*(frameHeight - ball.getHeight()));

        ball.setX(ballX);
        ball.setY(ballY);

    }

}