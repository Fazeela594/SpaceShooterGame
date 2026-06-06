package com.example.spaceshootergame;

import android.content.Context;
import android.graphics.*;

public class Enemy {

    float x, y;
    Bitmap img;
    public boolean canShoot;

    public Enemy(Context context, float x, float y, int level) {

        this.x = x;
        this.y = y;

        int type = (int)(Math.random() * 3);

        if (type == 0)
            img = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy1);
        else if (type == 1)
            img = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy2);
        else
            img = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy3);

        img = Bitmap.createScaledBitmap(img, 65, 65, false);

        // better shooting balance
        canShoot = Math.random() < 0.35;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(img, x, y, null);
    }
}