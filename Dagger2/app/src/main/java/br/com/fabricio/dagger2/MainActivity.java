package br.com.fabricio.dagger2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import br.com.fabricio.dagger2.contract.ApplicationComponent;
import br.com.fabricio.dagger2.contract.CarComponent;
import br.com.fabricio.dagger2.contract.DaggerCarComponent;
import br.com.fabricio.dagger2.domain.Car;
import br.com.fabricio.dagger2.module.CarModule;


public class MainActivity extends AppCompatActivity {

    private CarComponent mCarComponent;


    private Car mCar;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCarComponent = DaggerCarComponent
                .builder()
                .applicationComponent( getApplicationComponent() )
                .carModule( new CarModule())
                .build();


        //mCar = new Car();

        mCar = mCarComponent.provideCar();
        mCarComponent.inject(mCar);

        mCar.increaseSpeed(90);
        Toast.makeText(this, "Car: "+mCar.getSpeed(), Toast.LENGTH_SHORT).show();


        //mSharedPreferences = getSharedPreferences("PREF_KEY", Context.MODE_PRIVATE);
        mSharedPreferences = mCarComponent.providePreferences();
        mSharedPreferences.edit().putInt(Car.SPEED, mCar.getSpeed()).apply();
        Toast.makeText(this, "SharedPreferences: " + mSharedPreferences.getInt(Car.SPEED, 0), Toast.LENGTH_LONG).show();
    }


    private ApplicationComponent getApplicationComponent(){
        return( ((AndroidApplication) getApplication()).getApplicationComponent() );
    }
}
