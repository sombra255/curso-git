package br.com.fabricio.daggerexemplo.dagger;

import br.com.fabricio.daggerexemplo.car.Engine;
import br.com.fabricio.daggerexemplo.car.PetrolEngine;
import dagger.Binds;
import dagger.Module;

/**
 * Created by Fabricio on 25/03/2019.
 */

@Module
public abstract class PetrolEngineModule {

    @Binds
    abstract Engine bindEngine(PetrolEngine engine);

}
