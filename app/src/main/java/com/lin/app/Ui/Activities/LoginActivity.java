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

package com.lin.app.Ui.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lin.app.Models.LoginModel;
import com.lin.app.Presenters.LoginPresenter;
import com.lin.app.R;
import com.lin.app.Twitter;
import com.lin.app.Utils.LoginUtil;
import com.lin.app.Ui.Views.LoginView;
import com.lin.app.di.LoginActivityModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    @BindView(R.id.username)
    EditText mUsername;

    @BindView(R.id.password)
    EditText mPassword;

    @Inject
    public LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            final int result = checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        mUsername.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        mPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        LoginUtil.saveUserInfo(mUsername.getText().toString(), mPassword.getText().toString());
        finish();
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(this, "Invalid account", Toast.LENGTH_SHORT).show();
        hideProgress();
    }

    @Override
    public void onClick(View v) {
        final String userName = mUsername.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            setUsernameError();
            return;
        }

        final String userPassword = mPassword.getText().toString();

        if (TextUtils.isEmpty(userPassword)) {
            setPasswordError();
            return;
        }
        presenter.validateCredentials(mUsername.getText().toString(), mPassword.getText().toString());
    }

    @Override
    public void setupComponent() {
        ((Twitter)Twitter.getAppContext())
                .getAppComponent()
                .getLoginActivityComponent(new LoginActivityModule(this))
                .inject(this);
    }
}
