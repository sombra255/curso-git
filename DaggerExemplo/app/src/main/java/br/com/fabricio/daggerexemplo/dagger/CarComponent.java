package br.com.fabricio.daggerexemplo.dagger;

import br.com.fabricio.daggerexemplo.car.Car;
import br.com.fabricio.daggerexemplo.MainActivity;
import dagger.Component;

@Component (modules = {WheelsModule.class, DieselEngineModule.class})
public interface CarComponent {

    public Car getCar();
    public void inject(MainActivity mainActivity);
}
