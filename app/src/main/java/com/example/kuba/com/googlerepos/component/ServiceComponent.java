package com.example.kuba.com.googlerepos.component;

import com.example.kuba.com.googlerepos.MainActivity;
import com.example.kuba.com.googlerepos.modules.ServiceModule;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ServiceModule.class})
public interface ServiceComponent {

    void inject(MainActivity activity);



}
