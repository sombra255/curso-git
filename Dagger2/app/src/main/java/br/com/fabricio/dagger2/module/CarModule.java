package br.com.fabricio.dagger2.module;

import br.com.fabricio.dagger2.domain.Car;
import br.com.fabricio.dagger2.domain.Engine;
import br.com.fabricio.dagger2.scope.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by viniciusthiengo on 10/5/15.
 */

@Module
public class CarModule {
    @Provides
    @PerActivity
    public Engine provideEngine(){
        return( new Engine() );
    }


    @Provides
    @PerActivity
    public Car provideCar( ){
        return( new Car( ) );
    }
}
