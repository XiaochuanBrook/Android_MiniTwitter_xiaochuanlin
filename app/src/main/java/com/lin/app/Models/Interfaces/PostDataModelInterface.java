package com.lin.app.Models.Interfaces;

import android.net.Uri;

/**
 * Created by brooklin on 2017-05-09.
 */

public interface PostDataModelInterface {
    void post(final String str, final OnPostFinishedListener listener);

    void postPicture(final String str, final Uri uri, final OnPostFinishedListener listener);

    interface OnPostFinishedListener {
        void onFinish(final boolean success);
    }
}
