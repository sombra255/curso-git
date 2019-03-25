package br.com.fabricio.daggerexemplo;

import dagger.Component;

@Component
public interface CarComponent {

    public Car getCar();
    public void inject(MainActivity mainActivity);
}
