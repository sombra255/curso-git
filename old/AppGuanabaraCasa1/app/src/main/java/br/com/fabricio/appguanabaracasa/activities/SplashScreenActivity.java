package br.com.fabricio.appguanabaracasa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import br.com.fabricio.appguanabaracasa.R;

public class SplashScreenActivity extends AppCompatActivity {

    Animation animFadein;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView splash_text = (TextView) findViewById(R.id.splash_teste);

        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);

        splash_text.startAnimation(animFadein);

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                finish();

                Intent intent = new Intent();
                intent.setClass(SplashScreenActivity.this, CompraActivity.class);
                startActivity(intent);
            }
        }, 10000);
    }

}
