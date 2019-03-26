package br.com.fabricio.daggerexemplo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import br.com.fabricio.daggerexemplo.car.Car;
import br.com.fabricio.daggerexemplo.dagger.CarComponent;
import br.com.fabricio.daggerexemplo.dagger.DaggerCarComponent;
import br.com.fabricio.daggerexemplo.dagger.DieselEngineModule;

public class MainActivity extends AppCompatActivity {

    @Inject
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarComponent carComponent = DaggerCarComponent.builder()
                .dieselEngineModule(new DieselEngineModule(100))
                .build();
//        car = carComponent.getCar();
        carComponent.inject(this);
        car.drive();
    }
}
