package com.lin.app.Utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import static com.lin.app.Utils.DataPoolUtil.getSharePreferences;


/**
 * Created by brooklin on 2017-05-08.
 */

final public  class LoginUtil {
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_NAME = "user_name";
    private static String mUserName = "";

    public static String getUserName() {
        return mUserName;
    }

    public static boolean authentication(final String username, final String password) {
        if ("user".equals(username) && "psw".equals(password)) {
            return true;
        }
        if ("user2".equals(username) && "psw2".equals(password)) {
            return true;
        }
        return false;
    }



    public static void saveUserInfo(final String username, final String password) {
        final SharedPreferences.Editor editor = getSharePreferences().edit();
        editor.putString(USER_NAME, username);
        editor.putString(USER_PASSWORD, password);
        editor.apply();
    }

    public static boolean isUserSignedIn() {
        final SharedPreferences sharedPref =  getSharePreferences();
        final String userName = sharedPref.getString(USER_NAME, "");
        final String userPsw = sharedPref.getString(USER_PASSWORD, "");

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPsw)) {
            mUserName = userName;
            return true;
        }
        return false;
    }
}
