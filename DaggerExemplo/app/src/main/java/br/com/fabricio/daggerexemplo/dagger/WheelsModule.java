package br.com.fabricio.daggerexemplo.dagger;

import br.com.fabricio.daggerexemplo.car.Rims;
import br.com.fabricio.daggerexemplo.car.Tires;
import br.com.fabricio.daggerexemplo.car.Wheels;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Fabricio on 25/03/2019.
 */

@Module
public abstract class WheelsModule {

    @Provides
    static Rims provideRims(){
        return new Rims();
    }

    @Provides
    static Tires provideTires(){
        Tires tires = new Tires();
        tires.inflate();
        return tires;
    }

    @Provides
    static Wheels provideWheels(Rims rims, Tires tires){
        return new Wheels(rims, tires);
    }
}
