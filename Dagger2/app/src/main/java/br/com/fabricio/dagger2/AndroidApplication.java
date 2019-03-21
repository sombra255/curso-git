package br.com.fabricio.dagger2;

import android.app.Application;

import br.com.fabricio.dagger2.contract.ApplicationComponent;
import br.com.fabricio.dagger2.contract.DaggerApplicationComponent;
import br.com.fabricio.dagger2.module.ApplicationModule;


/**
 * Created by viniciusthiengo on 10/5/15.
 */
public class AndroidApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return( mApplicationComponent );
    }
}
