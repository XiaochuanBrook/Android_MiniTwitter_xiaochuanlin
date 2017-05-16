package com.lin.app.Models;

import android.os.Handler;
import android.text.TextUtils;

import com.lin.app.Models.Interfaces.LoginModelInterface;
import com.lin.app.Utils.LoginUtil;

public class LoginModel implements LoginModelInterface {

    private Handler mHandler;
    private Runnable mRunnable;
    private final static long TIME_OUT = 30000;

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        if (TextUtils.isEmpty(username)){
            listener.onUsernameError();
            return;
        }
        if (TextUtils.isEmpty(password)){
            listener.onPasswordError();
            return;
        }

        // post delay in 1000ms to mock the process time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (LoginUtil.authentication(username, password)) {
                    mHandler.removeCallbacks(mRunnable);
                    mRunnable = null;
                    mHandler = null;
                    listener.onSuccess();
                } else {
                    listener.onFailed();
                }
            }
        },1000);


        // post a failure in 30s as a time out
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                listener.onFailed();

            }
        };
        mHandler.postDelayed(mRunnable, TIME_OUT);
    }
}
