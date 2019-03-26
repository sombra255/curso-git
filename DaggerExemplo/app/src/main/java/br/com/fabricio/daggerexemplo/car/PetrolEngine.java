package br.com.fabricio.daggerexemplo.car;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by Fabricio on 25/03/2019.
 */

public class PetrolEngine implements Engine {
    private static final String TAG = "Car";

    @Inject
    public PetrolEngine() {
    }

    @Override
    public void start() {
        Log.d(TAG, "Petrol Engine Started...");
    }
}
