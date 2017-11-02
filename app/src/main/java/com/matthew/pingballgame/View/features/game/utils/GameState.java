package com.matthew.pingballgame.View.features.game.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;

import com.matthew.pingballgame.R;
import com.matthew.pingballgame.View.features.game.data.Shared;

/**
 * Created by Matthew on 29/10/2017.
 */

public class GameState {


    //screen width and height
    int screenWidth = 300; //default value
    int screenHeight = 420; // default value

    //The ball
    final int BALLSIZE = 10;
    int _ballX = 100;
    int _ballY = 100;
    int _ballVelocityX = 10;
    int _ballVelocityY = 10;

    //The bats
    int batLength1 = 75;
    private Context con;
    final int BATLENGTH2 = 75;
    final int BATHEIGHT = 10;
    int topBatX;
    final int TOPBATY = 20;
    int bottombatx;
    int bottombaty = 400;
    final int BATSPEED = 10;
    int textPlayer1;
    int textPlayer2;
    int limitPlayer2 = screenHeight;
    int limitPlayer1 = 0;
    private Bitmap win;
    private int player = -1;

    public GameState(int width, int height, Bitmap win, Context con) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.batLength1 = height;
        this.topBatX = (screenWidth / 2) - (batLength1 / 2);
        this.bottombatx = (screenWidth / 2) - (BATLENGTH2 / 2);
        this.bottombaty = screenHeight - (screenWidth / 4);
        this.limitPlayer1 = 0;
        this.limitPlayer2 = screenHeight;
        this.win = win;
        this.con = con;
    }

    //The update method
    public void update() {

        _ballX += _ballVelocityX;
        _ballY += _ballVelocityY;

        //DEATH!

        if (_ballY > limitPlayer2/* mark player 2*/ || _ballY < limitPlayer1 /* mark player 1*/) {
            if (_ballY > limitPlayer2) {
                textPlayer1 += 1;
            } else {
                textPlayer2 += 1;
            }

            _ballX = screenWidth / 2;
            _ballY = screenHeight / 2;
        }

        //Collisions with the sides

        if (_ballX > screenWidth || _ballX < 0)
            _ballVelocityX *= -1;    //Collisions with the bats

        if (_ballX > topBatX && _ballX < topBatX + batLength1 && _ballY < TOPBATY) {
            //insert make points in player 1
            textPlayer2 += 1;
            _ballVelocityX *= 1.2;
            _ballVelocityY *= -1;  //Collisions with the bats
        }

        if (_ballX > bottombatx && _ballX < bottombatx + BATLENGTH2
                && _ballY > bottombaty) {
            _ballVelocityY *= -1;
        }
    }

    public void setMovement(int player) {
        this.player = player;

    }

    public int movementPlayer(int x) {
        if (player == 0) {
           // topBatX = x;
        } else if (player == 1) {
            bottombatx = x;
        }

        return 1;
    }

    public void disablePlayer() {
        player = -1;
    }

    public boolean keyPressed(int keyCode, KeyEvent msg) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) //left
        {
            topBatX += BATSPEED;
            bottombatx -= BATSPEED;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) //right
        {
            topBatX -= BATSPEED;
            bottombatx += BATSPEED;
        }

        return true;
    }

    private void setCanvas(Canvas can, Paint pa) {
        //Clear the screen
        can.drawRGB(20, 20, 20);

//set the colour
        pa.setARGB(200, 0, 200, 0);

        pa.setTextSize(20);

    }

    private void setTextWin(Paint pa) {
        pa.setTextSize(50);
    }


    //the draw method
    public void draw(Canvas canvas, Paint paint) {
        setCanvas(canvas, paint);
        if (textPlayer1 < 10 /*&& textPlayer2 < 10*/) {
//draw the ball
            canvas.drawRect(new Rect(_ballX, _ballY, _ballX + BALLSIZE, _ballY + BALLSIZE),
                    paint);
//draw text player1
            int highscore = Shared.TOTALPOINTS(con);
            canvas.drawText(con.getString(R.string.record), 25, (screenHeight / 2) - 25, paint);
            canvas.drawText(String.valueOf(highscore), 25, (screenHeight / 2), paint);

            //draw text player2
            canvas.drawText(con.getString(R.string.actualPoints), 25, (screenHeight / 2) - 100, paint);
            canvas.drawText(String.valueOf(textPlayer2), 25, (screenHeight / 2) - 75, paint);
            //draw text player2
            canvas.drawText(con.getString(R.string.trys), 25, (screenHeight / 2) - 150, paint);
            canvas.drawText(String.valueOf(10 - textPlayer1), 25, (screenHeight / 2) - 125, paint);
//draw the bats
            canvas.drawRect(new Rect(topBatX, TOPBATY, topBatX + batLength1,
                    TOPBATY + BATHEIGHT), paint); //top bat
            canvas.drawRect(new Rect(bottombatx, bottombaty, bottombatx + BATLENGTH2,
                    bottombaty + BATHEIGHT), paint); //bottom bat

        } else {
            setTextWin(paint);
            int x = 0;
            int y = (screenHeight / 2);
            String record = con.getString(R.string.high);
            int highscore = Shared.TOTALPOINTS(con);


            if (textPlayer2 > highscore) {
                Shared.saveHighScore(con, textPlayer2);
                record += con.getString(R.string.newrecord) + textPlayer2;
            } else {
                record += highscore;
            }
            //  if (textPlayer1 >= 10) {
            canvas.drawText(record, x, y, paint);
            //} else {
            //canvas.drawText("Win Player 2", (screenWidth / 2), (screenHeight / 2), paint);
            //}
            canvas.drawBitmap(win, x, y + 100, paint);
        }
    }
}
