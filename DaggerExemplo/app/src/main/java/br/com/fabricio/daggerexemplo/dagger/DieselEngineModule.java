package br.com.fabricio.daggerexemplo.dagger;

import br.com.fabricio.daggerexemplo.car.DieselEngine;
import br.com.fabricio.daggerexemplo.car.Engine;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Fabricio on 25/03/2019.
 */

@Module
public class DieselEngineModule {
    private int horsePower;

    public DieselEngineModule(int horsePower) {
        this.horsePower = horsePower;
    }

    @Provides
    Engine provideEngine(){
        return new DieselEngine(horsePower);
    }

}
