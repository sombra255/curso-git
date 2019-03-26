package br.com.fabricio.daggerexemplo.car;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by Fabricio on 25/03/2019.
 */

public class DieselEngine implements Engine {

    private static final String TAG = "Car";
    private int horsePower;

    @Inject
    public DieselEngine(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public void start() {
        Log.d(TAG, "Diesel Engine Started...: HorsePower: "+horsePower);
    }
}
