package com.lin.app;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;

import com.lin.app.Bean.FeedItem;
import com.lin.app.Utils.DataPoolUtil;
import com.lin.app.di.AppComponent;
import com.lin.app.di.AppModule;
import com.lin.app.di.DaggerAppComponent;

import java.util.ArrayList;

/**
 * Created by brooklin on 2017-05-11.
 */

public class Twitter extends Application {

    private static Context mAppContext;
    private static ArrayList<FeedItem> mList;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        initAppComponent();

        DataPoolUtil.setSharedPreferenes(getSharedPreferences(Twitter.class.getName(), Context.MODE_PRIVATE));
    }
    public static Context getAppContext() {
        return mAppContext;
    }

    private AppComponent appComponent;

    private void initAppComponent(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
