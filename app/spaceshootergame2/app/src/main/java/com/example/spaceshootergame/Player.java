package com.example.spaceshootergame;

import android.content.Context;
import android.graphics.*;

public class Player {

    float x, y;
    Bitmap img;

    int lives = 5;

    public Player(Context context, int screenX, int screenY) {

        img = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.player
        );

        img = Bitmap.createScaledBitmap(img, 100, 100, false);

        x = screenX / 2f;
        y = screenY - 200;
    }

    public void setX(float touchX) {

        x = touchX - img.getWidth() / 2f;

        if (x < 0) x = 0;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(img, x, y, null);
    }
}