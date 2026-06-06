package com.example.spaceshootergame;

import android.content.Context;
import android.graphics.*;

public class EnemyBullet {

    float x, y;
    Bitmap img;

    public EnemyBullet(Context context, float x, float y) {

        this.x = x;
        this.y = y;

        img = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.enemy_bullet
        );

        img = Bitmap.createScaledBitmap(img, 15, 30, false);
    }

    public void update() {
        y += 12;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(img, x, y, null);
    }
}
