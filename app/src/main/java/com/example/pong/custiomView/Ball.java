package com.example.pong.custiomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Ball extends View {


    public Ball(Context context) {
        super(context);

        init(null);
    }

    public Ball(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Ball(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public Ball(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(@Nullable AttributeSet set){

    }
    @Override
    protected void onDraw (Canvas canvas){

/*        Rect rect = new Rect();
        rect.left = X;
        rect.top = Y;
        rect.right = rect.left + BALL_SIZE;
        rect.bottom = rect.top + BALL_SIZE;

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(rect, paint);*/
    }
}
