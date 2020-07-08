package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.pong.custiomView.Ball;
import com.example.pong.custiomView.Paddle;

public class MainActivity extends AppCompatActivity {


    DisplayMetrics displayMetrics = new DisplayMetrics();
    Context context;
    int height = 0;
    int width = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Getting Height ad Width
        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        height = Math.round(displayMetrics.heightPixels / displayMetrics.density);
        width = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        System.out.println("Height: " + height);
        System.out.println("Width: " + width);

        //doesnt play much of a role yet
        Ball ball = new Ball(this);
        Paddle paddle1 = new Paddle(this , 10, 500);
        Paddle paddle2 = new Paddle(this, 2500, 500);
    }



}