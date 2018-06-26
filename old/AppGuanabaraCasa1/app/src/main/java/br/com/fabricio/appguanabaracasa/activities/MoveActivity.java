package br.com.fabricio.appguanabaracasa.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import br.com.fabricio.appguanabaracasa.R;

/**
 * Created by Fabricio on 12/10/2016.
 */

public class MoveActivity extends Activity {

    Animation animFadein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fadein);


        // load the animation
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
    }
}
