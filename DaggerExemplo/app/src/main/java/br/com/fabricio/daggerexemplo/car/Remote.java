package br.com.fabricio.daggerexemplo.car;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by Fabricio on 25/03/2019.
 */

public class Remote {

    private static final String TAG = "Car";

    @Inject
    public Remote() {
    }


    public void setListener(Car car) {
        Log.d(TAG, "Remote Connected... ");
    }
}
