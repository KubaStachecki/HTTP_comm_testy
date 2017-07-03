package com.example.kuba.com.googlerepos.modules;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class ServiceModule {

    public String mBaseUrl;

    public ServiceModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }


    @Singleton
    @Provides
    GsonConverterFactory provideGson() {
        return GsonConverterFactory.create();

    }


    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideAdapterFactory() {

        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(GsonConverterFactory gson, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {

        return new Retrofit.Builder()
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .baseUrl(mBaseUrl)
                .build();
    }
}
