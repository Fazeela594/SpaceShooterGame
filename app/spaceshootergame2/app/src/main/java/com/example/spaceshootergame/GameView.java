package com.example.spaceshootergame;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    Thread thread;
    boolean isPlaying = true;

    int screenX, screenY;

    Player player;

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();

    int level = 1;
    int score = 0;

    boolean showLevel = false;
    int timer = 0;

    Paint paint = new Paint();

    long lastShoot = 0;

    Bitmap heart;
    Bitmap background;   // ⭐ ADD THIS

    public GameView(Context context) {
        super(context);

        screenX = getResources().getDisplayMetrics().widthPixels;
        screenY = getResources().getDisplayMetrics().heightPixels;

        player = new Player(context, screenX, screenY);

        spawnEnemies(level);

        heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        heart = Bitmap.createScaledBitmap(heart, 25, 25, false);

        // ⭐ BACKGROUND LOAD
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {

        // PLAYER BULLETS
        for (int i = 0; i < bullets.size(); i++) {

            Bullet b = bullets.get(i);
            b.y -= 20;

            if (b.y < 0) {
                bullets.remove(i);
                i--;
                continue;
            }

            for (int j = 0; j < enemies.size(); j++) {

                Enemy e = enemies.get(j);

                if (b.x < e.x + 75 &&
                        b.x + 20 > e.x &&
                        b.y < e.y + 75 &&
                        b.y + 40 > e.y) {

                    enemies.remove(j);
                    bullets.remove(i);
                    score += 10;
                    i--;
                    break;
                }
            }
        }

        // ENEMY SHOOT
        for (Enemy e : enemies) {
            if (e.canShoot && Math.random() < 0.01) {
                enemyBullets.add(new EnemyBullet(
                        getContext(),
                        e.x + 35,
                        e.y + 40
                ));
            }
        }

        // ENEMY BULLETS
        for (int i = 0; i < enemyBullets.size(); i++) {

            EnemyBullet eb = enemyBullets.get(i);

            eb.y += 12;

            if (eb.y > screenY) {
                enemyBullets.remove(i);
                i--;
                continue;
            }

            if (eb.x < player.x + 80 &&
                    eb.x + 20 > player.x &&
                    eb.y < player.y + 80 &&
                    eb.y + 30 > player.y) {

                player.lives--;
                enemyBullets.remove(i);
                i--;
            }
        }

        // LEVEL COMPLETE
        if (enemies.size() == 0 && !showLevel) {

            if (level >= 5) {
                gameOver();
                return;
            }

            showLevel = true;
            timer = 90;
        }

        if (showLevel) {
            timer--;

            if (timer <= 0) {
                level++;
                spawnEnemies(level);
                showLevel = false;
            }
        }

        if (player.lives <= 0) {
            gameOver();
        }
    }

    private void spawnEnemies(int level) {

        enemies.clear();

        int startX = 60;
        int startY = 160;

        int rows = 4;
        int cols = 5;

        if (level == 2) cols = 5;
        if (level == 3) cols = 6;
        if (level == 4) cols = 6;
        if (level == 5) cols = 7;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                enemies.add(new Enemy(
                        getContext(),
                        startX + c * 100,
                        startY + r * 90,
                        level
                ));
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            player.setX(event.getX());
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            long now = System.currentTimeMillis();

            if (now - lastShoot > 250) {

                bullets.add(new Bullet(
                        getContext(),
                        player.x + player.img.getWidth() / 2f - 10,
                        player.y
                ));

                lastShoot = now;
            }
        }

        return true;
    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            // ⭐ BACKGROUND DRAW
            canvas.drawBitmap(background, 0, 0, null);

            player.draw(canvas);

            for (Enemy e : enemies)
                e.draw(canvas);

            for (Bullet b : bullets)
                b.draw(canvas);

            for (EnemyBullet eb : enemyBullets)
                eb.draw(canvas);

            paint.setColor(Color.WHITE);
            paint.setTextSize(40);

            canvas.drawText("Score: " + score, 40, 60, paint);
            canvas.drawText("Level: " + level, 40, 120, paint);

            if (heart != null) {

                for (int i = 0; i < player.lives; i++) {

                    canvas.drawBitmap(
                            heart,
                            screenX - 40 - (i * 30),
                            20,
                            null
                    );
                }
            }

            if (showLevel) {

                paint.setTextSize(80);
                paint.setColor(Color.YELLOW);

                canvas.drawText(
                        "LEVEL " + level,
                        screenX / 2f - 180,
                        screenY / 2f,
                        paint
                );
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void gameOver() {

        isPlaying = false;

        Intent i = new Intent(getContext(), GameOverActivity.class);
        i.putExtra("score", score);
        getContext().startActivity(i);
    }

    private void sleep() {
        try {
            Thread.sleep(16);
        } catch (Exception e) {
        }
    }
}

