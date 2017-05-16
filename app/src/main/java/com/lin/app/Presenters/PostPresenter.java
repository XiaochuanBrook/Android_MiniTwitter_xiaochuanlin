package com.lin.app.Presenters;

import android.net.Uri;

import com.lin.app.Models.PostDataModel;
import com.lin.app.Models.Interfaces.PostDataModelInterface;
import com.lin.app.Ui.Views.PostView;

/**
 * Created by brooklin on 2017-05-09.
 */

public class PostPresenter implements PostDataModelInterface.OnPostFinishedListener{

    private PostDataModelInterface mPostDataModel;
    private PostView mPostView;

    public PostPresenter (PostView postView, PostDataModel postDataModel) {
        mPostView = postView;
        mPostDataModel  = postDataModel;

    }
    public void post(final String str) {
        mPostView.showProgress();
        mPostDataModel.post(str,this);
    }

    public void addPicture(final  Uri uri) {
        mPostView.onImageAdd(uri);
    }

    public void postPicture(final String str, final Uri uri) {
        mPostView.showProgress();
        mPostDataModel.postPicture(str, uri, this);
    }

    public void onDestroy(){
        mPostView.hideProgress();
        mPostDataModel = null;
        mPostView = null;
    }

    @Override
    public void onFinish(boolean success) {
        if (mPostView != null) {
            mPostView.hideProgress();
            mPostView.onSent();
        }
    }

    public PostView getView() {
        return mPostView;
    }
}
