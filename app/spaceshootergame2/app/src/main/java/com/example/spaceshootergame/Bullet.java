package com.example.spaceshootergame;

import android.content.Context;
import android.graphics.*;

public class Bullet {

    float x, y;
    Bitmap img;

    public Bullet(Context context, float x, float y) {

        this.x = x;
        this.y = y;

        img = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.player_bullet
        );

        img = Bitmap.createScaledBitmap(img, 20, 40, false);
    }

    public void update() {
        y -= 20;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(img, x, y, null);
    }
}
