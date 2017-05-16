package com.lin.app.di;

import com.lin.app.Ui.Activities.MainActivity;

import dagger.Subcomponent;

/**
 * Created by brooklin on 2017-05-13.
 */

@ActivityScope
@Subcomponent( modules = MainActivityModule.class )
public interface MainActivityComponent {
    MainActivity inject(MainActivity activity);
}