package com.matthew.pingballgame.View.features.game.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Matthew on 02/11/2017.
 */

public class Shared {
    private static final String NAMESHARED = "share";

    public static int TOTALPOINTS(Context con) {
        SharedPreferences prefs = con.getSharedPreferences(NAMESHARED, MODE_PRIVATE);
        return prefs.getInt("points", 0); //0 is the default value.
    }

    public static void saveHighScore(Context con, int score) {
        // MY_PREFS_NAME - a static String variable like:
//public static final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences.Editor editor = con.getSharedPreferences(NAMESHARED, MODE_PRIVATE).edit();

        editor.putInt("points", score);
        editor.apply();
    }


}
