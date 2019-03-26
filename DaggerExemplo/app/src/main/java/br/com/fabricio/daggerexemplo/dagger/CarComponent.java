package br.com.fabricio.daggerexemplo.dagger;

import javax.inject.Named;

import br.com.fabricio.daggerexemplo.car.Car;
import br.com.fabricio.daggerexemplo.MainActivity;
import dagger.BindsInstance;
import dagger.Component;

@Component (modules = {WheelsModule.class, PetrolEngineModule.class})
public interface CarComponent {

    public Car getCar();
    public void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder horsePower(@Named("horse power") int horsePower);

        @BindsInstance
        Builder engineCapacity(@Named("engine capacity")int engineCapacity);

        CarComponent build();
    }
}
