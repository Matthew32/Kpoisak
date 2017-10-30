package com.matthew.pingballgame.View.features.game.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;


/**
 * Created by Matthew on 29/10/2017.
 */

public class GameThread extends Thread {

    /**
     * Handle to the surface manager object we interact with
     */
    private SurfaceHolder _surfaceHolder;
    private Paint _paint;
    private GameState _state;

    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler, Bitmap imageWin) {
        _surfaceHolder = surfaceHolder;
        _paint = new Paint();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        //get width and height
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        _state = new GameState(width, height, imageWin);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Canvas canvas = _surfaceHolder.lockCanvas();
                _state.update();
                _state.draw(canvas, _paint);
                _surfaceHolder.unlockCanvasAndPost(canvas);
            } catch (Exception ex) {
                Log.i("Error002", "Error 002: SurfaceViewThreadDrawStop Something happened;");
            }
        }
    }

    public GameState getGameState() {
        return _state;
    }
}