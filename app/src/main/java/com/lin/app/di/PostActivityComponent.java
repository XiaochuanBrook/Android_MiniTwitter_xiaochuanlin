package com.lin.app.di;

import com.lin.app.Ui.Activities.PostActivity;

import dagger.Subcomponent;

/**
 * Created by brooklin on 2017-05-13.
 */

@ActivityScope
@Subcomponent( modules = PostActivityModule.class )
public interface PostActivityComponent {
    PostActivity inject(PostActivity activity);
}