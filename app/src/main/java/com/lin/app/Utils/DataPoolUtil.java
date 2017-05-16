package com.lin.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.lin.app.Models.Interfaces.FindItemInterface;
import com.lin.app.R;
import com.lin.app.Twitter;
import com.lin.app.Bean.FeedItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by brooklin on 2017-05-11.
 */

public class DataPoolUtil {
    private static ArrayList<FeedItem> mList;
    private static SharedPreferences mSharePreferences;
    public static int DEFAULT_DATA_SIZE = 5;
    private static int mDataSize = DEFAULT_DATA_SIZE;

    public static void setSharedPreferenes(final SharedPreferences sharePreferences) {
        mSharePreferences = sharePreferences;
    }

    public static SharedPreferences getSharePreferences() {
        return mSharePreferences;
    }

    /**
     * Helper method to post data
     * @param data
     * @return true if post success
     */
    public static boolean PostToDataPool(final ArrayList<FeedItem> data) {
        final Gson gson = new Gson();
        final String json = gson.toJson(data);
        SharedPreferences.Editor editor = getSharePreferences().edit();
        editor.putString("DATA", json);
        editor.apply();
        return true;
    }

    /**
     * Helper method to get data from existing data pool
     * @return string value of the data
     */
    public static ArrayList<FeedItem> getDataFromDataPool() {
        final String data = getSharePreferences().getString("DATA", "");
        if (data.length() <= 0) {
            return null;
        }
        ArrayList<FeedItem> result = parseResultFromReferences(data);
        setListReference(result);
        return getSubListFromFeedItemList(result);
    }

    /**
     * Helper method to just get part of the return data
     * @param list the full data list
     * @return a sublist according to the mDataSize
     */
    public static ArrayList<FeedItem> getSubListFromFeedItemList(final ArrayList<FeedItem> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        // on every time request 2 more items
        mDataSize = mDataSize + 2;

        final int lastPos = list.size();
        final int startPost = lastPos - mDataSize;

        if (startPost < 0) {
            return list;
        }
        return new ArrayList<>(list.subList(lastPos - mDataSize, lastPos));
    }

    /**
     * Helper method to parse a json data string from sharePreference
     * @param str data string
     * @return list ArrayList<FeedItem>
     */
    private static ArrayList<FeedItem> parseResultFromReferences(String str) {
        Type type  = new TypeToken<List<FeedItem>>() {}.getType();
        ArrayList<FeedItem> result;
        result = new Gson().fromJson(str, type);
        return result;
    }

    /**
     * Helper method to parse a json data string from raw data
     * @param result data string
     * @return list ArrayList<FeedItem>
     */
    private static ArrayList<FeedItem> parseResultFromRawData(String result) {
        final ArrayList<FeedItem> feedsList = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setAuthor(post.optString("name"));
                item.setDescription(post.optString("content"));
                item.setPicture(post.optString("picture"));
                item.setAvatar(post.optString("avatar"));
                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return feedsList;
        }
        return feedsList;
    }

    private static void setListReference(final ArrayList<FeedItem> list) {
        mList = list;
    }

    private static ArrayList<FeedItem> getListReference() {
        return mList;
    }

    public static void postFeedItem(final FeedItem item) {
        final ArrayList<FeedItem> list = getListReference();
        // attach the new item at the end of the current version and save
        list.add(item);
        PostToDataPool(list);
    }

    public static class LoadAssetDataTask extends AsyncTask<String, Void, String> {

        private final FindItemInterface.OnFinishedListener mListener;

        public LoadAssetDataTask(final FindItemInterface.OnFinishedListener listener) {
            mListener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection;
            try {
                final Context context = Twitter.getAppContext();
                InputStream input = context.getResources().openRawResource(R.raw.data);

                // some network handling should happen here in real world
                // but here we just load the data from R.raw.data

                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(input));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }

                    return response.toString();
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (mListener != null && !TextUtils.isEmpty(result)) {
                ArrayList<FeedItem> data = parseResultFromRawData(result);
                PostToDataPool(data);
                setListReference(data);
                // only show 5 items at first time data loading
                data = getSubListFromFeedItemList(data);
                mListener.onFinished(data);
            }
        }
    }
}
