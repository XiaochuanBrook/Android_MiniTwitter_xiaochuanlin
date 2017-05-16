package com.lin.app.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by brooklin on 2017-05-13.
 */

@Singleton
@Component( modules = AppModule.class)
public interface AppComponent {
    LoginActivityComponent getLoginActivityComponent (LoginActivityModule module);
    MainActivityComponent getMainActivityComponent (MainActivityModule module);
    PostActivityComponent getPostActivityComponent (PostActivityModule module);
}