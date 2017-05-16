package com.lin.app.di;


import com.lin.app.Models.MainScreenFeedsModel;
import com.lin.app.Presenters.MainPresenter;
import com.lin.app.Ui.Activities.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brooklin on 2017-05-13.
 */
@Module
public class MainActivityModule {

    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    MainActivity providesMainActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    MainPresenter providedPresenters() {
        return new MainPresenter(activity, new MainScreenFeedsModel());
    }
}
