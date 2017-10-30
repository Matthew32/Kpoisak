package com.matthew.pingballgame.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.matthew.pingballgame.View.features.game.GameActivity;

/**
 * Created by Matthew on 30/10/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Start home activity
        startActivity(new Intent(this, GameActivity.class));
        // close splash activity
        finish();
    }
}
