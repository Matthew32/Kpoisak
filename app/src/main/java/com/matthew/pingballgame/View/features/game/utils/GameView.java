package com.matthew.pingballgame.View.features.game.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.matthew.pingballgame.R;

/**
 * Created by Matthew on 29/10/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread _thread;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private float x1, x2;
    static final int MIN_DISTANCE = 150;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //So we can listen for events...
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);


        //and instantiate the thread

        Resources res = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.win);
        _thread = new GameThread(holder, context, new Handler(), bitmap);
        // Gesture detection

        gestureDetector = new GestureDetector(getContext(), new MyGestureDetector());
        //gestureDetector = new GestureDetector(this, null);
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }

        };
        this.setOnTouchListener(gestureListener);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        return _thread.getGameState().keyPressed(keyCode, msg);
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        //Mandatory, just swallowing it for this example

    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        _thread.start();
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            _thread.stop();
        } catch (Exception ex) {
            Log.i("Error001", "Error 001: SurfaceViewThreadStop Something happened;");
        }
    }


    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    return _thread.getGameState().keyPressed(KeyEvent.KEYCODE_DPAD_LEFT, null);

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    return _thread.getGameState().keyPressed(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                    // Toast.makeText(getContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

}

