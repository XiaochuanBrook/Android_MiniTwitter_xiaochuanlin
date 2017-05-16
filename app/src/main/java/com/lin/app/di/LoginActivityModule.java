package com.lin.app.di;

import com.lin.app.Models.LoginModel;
import com.lin.app.Presenters.LoginPresenter;
import com.lin.app.Ui.Activities.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brooklin on 2017-05-13.
 */
@Module
public class LoginActivityModule {

    private LoginActivity activity;

    public LoginActivityModule(LoginActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    LoginActivity providesLoginActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    LoginPresenter providedPresenters() {
        return new LoginPresenter(activity, new LoginModel());
    }
}
