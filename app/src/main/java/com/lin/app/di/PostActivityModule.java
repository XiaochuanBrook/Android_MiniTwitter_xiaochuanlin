package com.lin.app.di;

import com.lin.app.Models.PostDataModel;
import com.lin.app.Presenters.PostPresenter;
import com.lin.app.Ui.Activities.PostActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brooklin on 2017-05-13.
 */
@Module
public class PostActivityModule {

    private PostActivity activity;

    public PostActivityModule(PostActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    PostActivity providesMainActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    PostPresenter providedPresenters() {
        return new PostPresenter(activity, new PostDataModel());
    }
}
