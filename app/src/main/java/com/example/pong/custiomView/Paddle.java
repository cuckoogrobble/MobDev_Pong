package com.example.pong.custiomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Paddle extends View {

    private static final int PADDLE_WIDTH = 50;
    private static final int PADDLE_HEIGHT = 200;
    Rect paddle1, paddle2;
    Paint paint, paint2;



    public Paddle(Context context, int x, int y) {
        super(context);
        init(null);
    }

    public Paddle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Paddle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public Paddle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(@Nullable AttributeSet set){
        paddle1 = new Rect();
        paint = new Paint();
        paddle2 = new Rect();
        paint2 = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas){

/*
        paddle1.left = 10;
        paddle1.top = 500;
        paddle1.right = paddle1.left + PADDLE_WIDTH;
        paddle1.bottom = paddle1.top + PADDLE_HEIGHT;


        paint.setColor(Color.BLUE);
        canvas.drawRect(paddle1, paint);


        paddle2.left = 2500;
        paddle2.top = 500;
        paddle2.right = paddle2.left + PADDLE_WIDTH;
        paddle2.bottom = paddle2.top + PADDLE_HEIGHT;


        paint.setColor(Color.BLUE);
        canvas.drawRect(paddle2, paint2);*/
    }
}
