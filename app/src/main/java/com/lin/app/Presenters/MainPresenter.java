/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.lin.app.Presenters;

import com.lin.app.Bean.FeedItem;
import com.lin.app.Models.Interfaces.FindItemInterface;
import com.lin.app.Ui.Views.MainView;

import java.util.List;

public class MainPresenter implements FindItemInterface.OnFinishedListener {

    private MainView mainView;
    private FindItemInterface findItemsModelInterface;

    public MainPresenter(MainView mainView, FindItemInterface findItemsModelInterface) {
        this.mainView = mainView;
        this.findItemsModelInterface = findItemsModelInterface;
    }

    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

        findItemsModelInterface.fetchFeedItems(this);
    }

    public void onRefresh() {
        findItemsModelInterface.fetchFeedItems(this);
        if (mainView != null) {
            mainView.showMessage(String.format("view refreshed"));
        }
    }

    public void onLoadMore() {
        findItemsModelInterface.fetchFeedItems(this);
    }

    public void onItemClicked(final FeedItem item) {
        if (mainView != null) {
            mainView.showMessage(String.format("%s's item clicked", item.getAuthor()));
        }
    }

    public void onDestroy() {
        mainView = null;
    }

    public void onFinished(List<FeedItem> items) {
        if (mainView != null) {
            if (items != null && items.size() > 0) {
                mainView.setItems(items);
            } else {
                mainView.showMessage("error in loading data");
            }
            mainView.hideProgress();
        }
    }

    public MainView getMainView() {
        return mainView;
    }
}
