package com.lin.app.di;

import com.lin.app.Ui.Activities.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by brooklin on 2017-05-13.
 */

@ActivityScope
@Subcomponent( modules = LoginActivityModule.class )
public interface LoginActivityComponent {
    LoginActivity inject(LoginActivity activity);
}