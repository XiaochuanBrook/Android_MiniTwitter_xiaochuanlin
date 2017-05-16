package com.lin.app;

import android.content.SharedPreferences;

import com.lin.app.Bean.FeedItem;
import com.lin.app.Utils.DataPoolUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static com.lin.app.Utils.DataPoolUtil.getSharePreferences;
import static com.lin.app.Utils.DataPoolUtil.getSubListFromFeedItemList;
import static com.lin.app.Utils.LoginUtil.saveUserInfo;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by brooklin on 2017-05-13.
 */

public class UtilsTest {

    SharedPreferences sharedPreferences;
    private android.content.SharedPreferences.Editor editor;

    @Test
    public void parseResultTest() {

    }

    @Before
    public void before() throws Exception {
        sharedPreferences = Mockito.mock(SharedPreferences.class);
        editor = Mockito.mock(android.content.SharedPreferences.Editor.class);
        DataPoolUtil.setSharedPreferenes(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
    }

    @Test
    public void getSharedPreferencesTest() throws Exception {
        assertEquals(sharedPreferences, getSharePreferences());
    }

    @Test
    public void PostToDataPoolTest() throws Exception {
        FeedItem item1 = new FeedItem();
        item1.setAuthor("Ken");
        FeedItem item2 = new FeedItem();
        item2.setAuthor("Lily");
        item2.setPicture("1.png");
        FeedItem item3 = new FeedItem();
        item3.setDescription("hello");
        ArrayList<FeedItem> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);

        DataPoolUtil.PostToDataPool(list);

        verify(editor, times(1)).putString("DATA", "[{\"title\":\"Ken\"},{\"title\":\"Lily\",\"picture\":\"1.png\"},{\"description\":\"hello\"}]");
        verify(editor, times(1)).apply();
    }

    @Test
    public void getDataPoolTest() throws Exception {
        String data = "[{\"title\":\"Ken\"},{\"title\":\"Lily\",\"picture\":\"1.png\"},{\"description\":\"hello\"},{\"description\":\"hello\"},{\"description\":\"hello\"},{\"description\":\"hello\"},{\"description\":\"hello\"}]";
        when(sharedPreferences.getString("DATA", "")).thenReturn(data);
        ArrayList<FeedItem> list = DataPoolUtil.getDataFromDataPool();
        FeedItem item1 = new FeedItem();
        item1.setAuthor("Ken");
        FeedItem item2 = new FeedItem();
        item2.setAuthor("Lily");
        item2.setPicture("1.png");
        FeedItem item3 = new FeedItem();
        item3.setDescription("hello");
        FeedItem item4 = new FeedItem();
        item4.setDescription("hello");
        FeedItem item5 = new FeedItem();
        item5.setDescription("hello");
        ArrayList<FeedItem> list2 = new ArrayList<>();
        list2.add(item1);
        list2.add(item2);
        list2.add(item3);
        list2.add(item4);
        list2.add(item5);


        for(int i = 0; i < 5; i++) {
            assertEquals(list.get(i).getAuthor(),list2.get(i).getAuthor());
            assertEquals(list.get(i).getAvatar(),list2.get(i).getAvatar());
            assertEquals(list.get(i).getDescription(),list2.get(i).getDescription());
            assertEquals(list.get(i).getPicture(),list2.get(i).getPicture());
        }
    }


    @Test
    public void getSubListFromFeedItemListTest() throws Exception {
        ArrayList list = new ArrayList();
        for(int i = 0; i<20; i++) {
            list.add(i, i);
        }
        ArrayList result = getSubListFromFeedItemList(list);
        // first time load more, size = 5 + 2
        assertEquals(result.size(),7);
        result = getSubListFromFeedItemList(list);
        assertEquals(result.size(),9);

    }

    @Test
    public void saveUserInfoTest() throws Exception {
        saveUserInfo("aaa", "bbb");
        verify(editor, times(1)).putString("user_name", "aaa");
        verify(editor, times(1)).putString("user_password", "bbb");
    }
}
