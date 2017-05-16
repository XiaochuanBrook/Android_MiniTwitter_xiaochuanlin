package com.lin.app.Ui.Views;

import android.net.Uri;

/**
 * Created by brooklin on 2017-05-09.
 */

public interface PostView {
    void showProgress();

    void hideProgress();

    void onImageAdd(final Uri uri);

    void onSent();
}
