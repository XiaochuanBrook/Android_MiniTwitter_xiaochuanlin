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

package com.lin.app.Models;

import android.content.SharedPreferences;
import android.os.Handler;

import com.lin.app.Bean.FeedItem;
import com.lin.app.Models.Interfaces.FindItemInterface;
import com.lin.app.Utils.DataPoolUtil;
import java.util.ArrayList;

import static com.lin.app.Utils.DataPoolUtil.getDataFromDataPool;

public class MainScreenFeedsModel implements FindItemInterface {

    private OnFinishedListener mListener;

    private String RAW_DATA_LOADED = "raw_data_loaded";

    @Override
    public void fetchFeedItems(final OnFinishedListener listener, final boolean loadMore) {

        mListener = listener;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!DataPoolUtil.getSharePreferences().getBoolean(RAW_DATA_LOADED, false)){
                    // load data from assets folder if data pool is empty
                    DataPoolUtil.LoadAssetDataTask task = new DataPoolUtil.LoadAssetDataTask(mListener);
                    task.execute("https://www.google.com");
                    SharedPreferences.Editor editor = DataPoolUtil.getSharePreferences().edit();
                    editor.putBoolean(RAW_DATA_LOADED, true);
                    editor.commit();
                } else {
                    final ArrayList<FeedItem> data = getDataFromDataPool(loadMore);
                    mListener.onFinished(data);
                }
            }
        }, 1000);
    }
}
