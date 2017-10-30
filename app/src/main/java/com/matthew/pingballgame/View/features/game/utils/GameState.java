package com.matthew.pingballgame.View.features.game.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

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
    final int BATLENGTH = 75;
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

    public GameState(int width, int height, Bitmap win) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.topBatX = (screenWidth / 2) - (BATLENGTH / 2);
        this.bottombatx = (screenWidth / 2) - (BATLENGTH / 2);
        bottombaty = screenHeight - (screenWidth / 4);
        limitPlayer1 = 0;
        limitPlayer2 = screenHeight;
        this.win = win;
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

        if (_ballX > topBatX && _ballX < topBatX + BATLENGTH && _ballY < TOPBATY)
            _ballVelocityY *= -1;  //Collisions with the bats

        if (_ballX > bottombatx && _ballX < bottombatx + BATLENGTH
                && _ballY > bottombaty)
            _ballVelocityY *= -1;
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
        if (textPlayer1 < 10 && textPlayer2 < 10) {
//draw the ball
            canvas.drawRect(new Rect(_ballX, _ballY, _ballX + BALLSIZE, _ballY + BALLSIZE),
                    paint);
//draw text player1
            canvas.drawText("Player 1", 25, (screenHeight / 2) - 25, paint);
            canvas.drawText(String.valueOf(textPlayer1), 25, (screenHeight / 2), paint);

            //draw text player2
            canvas.drawText("Player 2", screenWidth - 75, (screenHeight / 2) - 25, paint);
            canvas.drawText(String.valueOf(textPlayer2), screenWidth - 75, (screenHeight / 2), paint);
//draw the bats
            canvas.drawRect(new Rect(topBatX, TOPBATY, topBatX + BATLENGTH,
                    TOPBATY + BATHEIGHT), paint); //top bat
            canvas.drawRect(new Rect(bottombatx, bottombaty, bottombatx + BATLENGTH,
                    bottombaty + BATHEIGHT), paint); //bottom bat

        } else {
            setTextWin(paint);
            if (textPlayer1 >= 10) {
                canvas.drawText("Win Player 1", (screenWidth / 2), (screenHeight / 2), paint);
            } else {
                canvas.drawText("Win Player 2", (screenWidth / 2), (screenHeight / 2), paint);
            }
            canvas.drawBitmap(win, (screenWidth / 2), (screenHeight / 2) + 100, paint);
        }
    }
}
