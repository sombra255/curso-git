package br.com.fabricio.dagger2.contract;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import br.com.fabricio.dagger2.module.ApplicationModule;
import dagger.Component;

/**
 * Created by viniciusthiengo on 10/5/15.
 */

@Singleton
@Component( modules = {
        ApplicationModule.class
})
public interface ApplicationComponent {
    SharedPreferences providePreferences();
}
