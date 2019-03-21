package br.com.fabricio.dagger2.contract;

import android.content.SharedPreferences;

import br.com.fabricio.dagger2.domain.Car;
import br.com.fabricio.dagger2.module.CarModule;
import br.com.fabricio.dagger2.scope.PerActivity;
import dagger.Component;

/**
 * Created by viniciusthiengo on 10/5/15.
 */

@PerActivity
@Component(
        dependencies = {
                ApplicationComponent.class
        },
        modules = {
                CarModule.class
        }
)
public interface CarComponent {
    public void inject(Car car);

    Car provideCar();

    SharedPreferences providePreferences();
}
