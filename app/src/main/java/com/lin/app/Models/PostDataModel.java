package com.lin.app.Models;

import android.net.Uri;

import com.lin.app.Bean.FeedItem;
import com.lin.app.Models.Interfaces.PostDataModelInterface;
import com.lin.app.Utils.LoginUtil;

import static com.lin.app.Utils.DataPoolUtil.postFeedItem;

/**
 * Created by brooklin on 2017-05-10.
 */

public class PostDataModel implements PostDataModelInterface {

    @Override
    public void post(final String str, final OnPostFinishedListener listener) {
        final FeedItem item = new FeedItem();
        item.setAuthor(LoginUtil.getUserName());
        item.setDescription(str);
        postFeedItem(item);
        listener.onFinish(true);
    }

    @Override
    public void postPicture(final String str, Uri uri, final OnPostFinishedListener listener) {
        final FeedItem item = new FeedItem();
        item.setAuthor(LoginUtil.getUserName());
        item.setDescription(str);
        item.setPicture(uri.toString());

        postFeedItem(item);
        listener.onFinish(true);
    }


}
