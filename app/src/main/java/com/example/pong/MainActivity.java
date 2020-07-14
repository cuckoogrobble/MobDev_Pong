package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    //Game Layout
    private FrameLayout gameFrame;
    private LinearLayout startLayout, endLayout;
    private TableLayout statsTable, settings;

    //Game Elements
    private TextView scoreLeftText, scoreRightText, gameTitle, authorTitle, gameOverText, hitsText, hits1pl,hits2pl, timerText;
    private TextView credits, date, statScore1, statScore2, statHits1, statHits2, statsTimer, whoWon;
    private ImageView net, paddleLeft, paddleRight, ball;

    //User values
    ToggleButton speedAssignation, paddleLengthAssignation;

    //Size
    private int frameHeight = 0;
    private int frameWidth = 0;
    private int paddleHeight = 0;
    private int paddleWidth = 0;
    private int ballHeight;


    //Positions
    private float paddleLeftY, paddleRightY, paddleLeftX, paddleRightX;
    private float ballX, ballY;

    //Speed paddles
    private int paddleLeftSpeed;
    private int paddleRightSpeed;

    //Left Paddle Height
    //default
    private static final int PADDLE_HEIGHT = 100;
    //user defined ball speed
    private  int userPaddleHeight = 100;

    //Right Paddle Velocity (direction)
    private int yPaddleVel;

    //Ball Velocity and Speed
    private int xBallVel, yBallVel, speed;
    //default
    private static final int BALL_SPEED = 5;
    //user defined ball speed
    private  int userSpeed = 5;

    //Game Speed
    private static final int GAME_SPEED = 30;

    //Score
    private int scoreLeft = 0;
    private int scoreRight = 0;

    //Hits
    private int hits1 = 0;
    private int hits2 = 0;

    //Timer
    private Timer timer;
    private Handler handler = new Handler();

    //Status
    private boolean start_flag = false;
    private boolean action_flag = false;

    //Time count
    private long startTime = 0;
    private long currentTime = 0;

    //Sound

    //Start Button
    private Button startButton;


    //Sensor Manager
    private SensorManager sensorManager;
    Sensor accelerometer;

    //Strings
    String youWon, youLost;

    private static final String TAG = "MainActivity";


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
        hitsText.setVisibility(View.INVISIBLE);
        hits1pl.setVisibility(View.INVISIBLE);
        hits2pl.setVisibility(View.INVISIBLE);
        timerText.setVisibility(View.INVISIBLE);
        gameOverText.setVisibility(View.INVISIBLE);
        endLayout.setVisibility(View.INVISIBLE);
        startLayout.setVisibility(View.VISIBLE); //trying to get the date and credits to bottom


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
       // Log.d(TAG, "onSensorChanged: Y: "+ event.values[1]);
        paddleLeftY += event.values[1];

        if (paddleLeftY < 0) paddleLeftY = 0;
        if (paddleLeftY > frameHeight - userPaddleHeight) paddleLeftY = frameHeight - userPaddleHeight;
        paddleLeft.setY(paddleLeftY);
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
        hitsText = findViewById(R.id.hitsText);
        hits1pl = findViewById(R.id.hits1pl);
        hits2pl = findViewById(R.id.hits2pl);
        timerText = findViewById(R.id.timerText);
        gameTitle = findViewById(R.id.gameTitle);
        authorTitle = findViewById(R.id.author);
        startButton = findViewById(R.id.startButton);
        gameOverText = findViewById(R.id.gameOverText);
        date = findViewById(R.id.date);
        credits = findViewById(R.id.credits);
        statsTable = findViewById(R.id.statsTable);
        statScore1 = findViewById(R.id.scorePlayer1);
        statScore2 = findViewById(R.id.scorePlayer2);
        statHits1 = findViewById(R.id.hitsPlayer1);
        statHits2 = findViewById(R.id.hitsPlayer2);
        statsTimer = findViewById(R.id.timerNumber);
        endLayout = findViewById(R.id.endLayout);
        whoWon = findViewById(R.id.whoWon);
        settings = findViewById(R.id.settings);
        speedAssignation = findViewById(R.id.ballSpeed);
        paddleLengthAssignation = findViewById(R.id.paddlesLength);

        youLost = getString(R.string.youLost);
        youWon = getString(R.string.youWon);


        // getting frameHeight and frameWidth
        if (frameHeight == 0) {
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            paddleHeight = paddleLeft.getHeight();
            Log.d(TAG, "ini: PADDLE HEIGHT: " +paddleHeight);
            paddleWidth = paddleLeft.getWidth();
        }


        //get ball height
        ballHeight = ball.getHeight();


        //ini random right paddle velocity
        yPaddleVel = getSign(Math.random() * 2.0 - 1);

        //Setting accelerometer
        Log.d(TAG, "ini: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //Permission to use the sensor
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //getting the accelerometer sensor

        //toggles
        speedAssignation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) userSpeed = 10;
                else userSpeed = BALL_SPEED;

            }
        });

        Log.d(TAG, "ini: userSpeed: "+userSpeed);

        paddleLengthAssignation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) userPaddleHeight = 150;
                else userPaddleHeight = 100;
            }
        });


    }

    private void changePaddleVelocity() {
        yPaddleVel *= -1;
    }

    public void startGame(View view) {

        //I thought this would help with game speed, but apparently not
        //ini();
        //This also doesnt help
       // gameSpeed = 50;
       // speed = 20; // also not

        //initialize ball speed and velocity
        speed = userSpeed;
        Log.d(TAG, "startGame: BALL SPEED: " + speed);

        newBall();

        //initialize paddle height
        paddleLeft.getLayoutParams().height = userPaddleHeight;
        Log.d(TAG, "startGame: paddle height: "+userPaddleHeight);

        //initialize paddle speed
        paddleLeftSpeed = 15;
        paddleRightSpeed = 10;

        //Initialize time
        startTime = System.currentTimeMillis() / 1000;

        //set score to 0
        resetScore();

        start_flag = true;

        // getting frameHeight and frameWidth
        if (frameHeight == 0) {
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            paddleHeight = paddleLeft.getHeight();
            Log.d(TAG, "startGame: PADDLE HEIGHT: " +paddleHeight);
            paddleWidth = paddleLeft.getWidth();
        }


        //initial positions
        //paddles
        paddleLeftY = frameHeight / (float)2 - paddleHeight / (float)2;
        paddleRightY = frameHeight / (float)2 - paddleHeight / (float)2;
        paddleLeftX = paddleWidth;
        paddleRightX = frameWidth - 2 * paddleWidth;

        paddleLeft.setX(paddleLeftX);
        paddleLeft.setY(paddleLeftY);
        paddleRight.setX(paddleRightX);
        paddleRight.setY(paddleRightY);

        //Ball
        ballX = frameWidth / 2f;
        ballY = frameHeight / 2f;

        ball.setY(ballY);
        ball.setX(ballX);

        //Scores
        int scoreTextHeight = scoreLeftText.getHeight();

        scoreLeftText.setX(frameWidth/(float)2 - frameWidth/(float)4);
        scoreLeftText.setY(scoreTextHeight/(float)2);

        scoreRightText.setX(frameWidth/(float)2 + frameWidth/(float)4);
        scoreRightText.setY(scoreTextHeight/(float)2);

        //Hits
        hitsText.setX(5);
        hitsText.setY(frameHeight - hitsText.getHeight() - 5);

        int hitsHeight = hits1pl.getHeight();

        hits1pl.setX(frameWidth/2f - frameWidth/4f);
        hits1pl.setY(frameHeight - hitsHeight - hitsHeight/2f);

        hits2pl.setX(frameWidth/2f + frameWidth/4f);
        hits2pl.setY(frameHeight - hitsHeight - hitsHeight/2f);

        //TIMER
        timerText.setX(frameWidth - 2 * (timerText.getWidth()));
        timerText.setY(0);

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
        hitsText.setVisibility(View.VISIBLE);
        hits1pl.setVisibility(View.VISIBLE);
        hits2pl.setVisibility(View.VISIBLE);
        timerText.setVisibility(View.VISIBLE);
        gameOverText.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        credits.setVisibility(View.INVISIBLE);
        settings.setVisibility(View.INVISIBLE);

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

                            if (scoreLeft == 10 || scoreRight == 10){
                                gameStop();
                            }
                        }
                    });
                }
            } // call move(),  every gameSpeed ms, now 50 ms.
        }, 0, GAME_SPEED);



        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        Log.d(TAG, "startGame: Registered accelerometer listener");


    }

    private void resetScore() {
        scoreLeft = scoreRight = 0;
        scoreLeftText.setText(""+scoreLeft);
        scoreRightText.setText(""+scoreRight);
    }

    @SuppressLint("SetTextI18n")
    public void gameStop() {

        start_flag = false;
        startLayout.setVisibility(View.VISIBLE);
        ball.setVisibility(View.INVISIBLE);
        paddleLeft.setVisibility(View.INVISIBLE);
        paddleRight.setVisibility(View.INVISIBLE);
        scoreLeftText.setVisibility(View.INVISIBLE);
        scoreRightText.setVisibility(View.INVISIBLE);
        timerText.setVisibility(View.INVISIBLE);
        hitsText.setVisibility(View.INVISIBLE);
        hits1pl.setVisibility(View.INVISIBLE);
        hits2pl.setVisibility(View.INVISIBLE);
        net.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        authorTitle.setVisibility(View.GONE);
        gameOverText.setVisibility(View.VISIBLE);
        date.setVisibility(View.INVISIBLE);
        credits.setVisibility(View.INVISIBLE);
        statsTable.setVisibility(View.VISIBLE);
        startLayout.setVisibility(View.INVISIBLE);
        endLayout.setVisibility(View.VISIBLE);
        settings.setVisibility(View.INVISIBLE);


        statScore1.setText(""+scoreLeft);
        statScore2.setText(""+scoreRight);
        statHits1.setText(""+hits1);
        statHits2.setText(""+hits2);
        statsTimer.setText(""+(currentTime - startTime) + " seconds");

       whoWon.setText((scoreLeft>scoreRight) ? youWon : youLost);

    }

    public void move() {

        //getting timer to move
        currentTime = System.currentTimeMillis()/1000;
        timerText.setText("TIMER: "+(currentTime - startTime));

        //moving ball

        ballX += (xBallVel * speed);
        ballY += (yBallVel * speed);

        //moving right Paddle
        paddleRightY += (yPaddleVel * paddleRightSpeed);

        if ( paddleRightY < 0 ) changePaddleVelocity();
        if ( paddleRightY + paddleHeight >= frameHeight) changePaddleVelocity();


        //point for right paddle user and restart the game --> collision with left wall
        if (ballX < 0 ||  ballX < paddleLeftX){
            scoreRight++;

            //update right score
            scoreRightText.setText(""+scoreRight);

            newBall();
        }

        //point for left paddle user and restart the game -->collision with right wall
        if  (ballX >= frameWidth || ballX > (paddleRightX + paddleWidth)){
            scoreLeft++;

            //update left score
            scoreLeftText.setText(""+scoreLeft);

            newBall();
        }

        //Collision with upper and lower walls

        if (ballY < 0  || ballY > frameHeight - ballHeight/2f){
            changeYDir();
        }

        //collision with paddle

        //position of center of ball
        float ballCenterX = ballX + ballHeight/2f;
        float ballCenterY = ballY + ballHeight/2f;

       //collision left paddle
        if ( ballCenterX >= paddleLeftX && ballCenterX <= paddleLeftX + paddleWidth &&    // the ball is in the x  axis between the left wall and the paddle
         ballCenterY >= paddleLeftY && ballCenterY <= paddleLeftY + userPaddleHeight) {      //in the y axis between the upper corner (paddleLeftY) and the lower corner paddleLeftY + size
            changeXDir();
            hits1++;
            hits1pl.setText(""+hits1);
        }

        //collision right paddle

        if ( (ballCenterX + ballHeight/4f) >= paddleRightX && (ballCenterX + ballHeight/4f) <= frameWidth &&    // the ball is in the x  axis between the left wall and the paddle
                ballCenterY >= paddleRightY && ballCenterY <= paddleRightY + paddleHeight) {
            Log.d(TAG, "move: paddleHeight: "+paddleHeight);//in the y axis between the upper corner (paddleLeftY) and the lower corner paddleLeftY + size
            changeXDir();
            hits2++;
            hits2pl.setText(""+hits2);
        }

        //update positions
        ball.setX(ballX);
        ball.setY(ballY);
        paddleRight.setY(paddleRightY);
    }

    private void changeXDir(){
        xBallVel *=-1;
    }

    private void changeYDir(){
        yBallVel *=-1;
    }

    private void newBall(){
        //start from the middle
        ballX = (float)Math.floor(frameWidth/2 -ballHeight/2);
        //start randomly from the middle
        ballY = (float)Math.floor(Math.random()*(frameHeight - ballHeight));

        //Math.random() returns a number between 0 and 1, multiplied by 2, between 0 and 2, minus 1, between -1 and 1
        xBallVel = getSign(Math.random() * 2.0 - 1);
        yBallVel = getSign(Math.random() * 2.0 - 1);



        ball.setX(ballX);
        ball.setY(ballY);

    }

    private int getSign(double random) {
        if (random <= 0) return -1;
        return 1;
    }

}