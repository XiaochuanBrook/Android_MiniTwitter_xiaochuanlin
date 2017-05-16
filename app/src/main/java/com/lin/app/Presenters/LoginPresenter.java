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

import com.lin.app.Models.Interfaces.LoginModelInterface;
import com.lin.app.Ui.Views.LoginView;

public class LoginPresenter implements LoginModelInterface.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginModelInterface loginModelInterface;

    public LoginPresenter(LoginView loginView, final LoginModelInterface loginModelInterface) {
        this.loginView = loginView;
        this.loginModelInterface = loginModelInterface;
    }

    public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        loginModelInterface.login(username, password, this);
    }

    public void onDestroy() {
        loginView = null;
    }

    public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override public void onSuccess() {
        if (loginView != null) {
            loginView.onLoginSuccess();
        }
    }

    @Override
    public void onFailed() {
        if (loginView != null) {
            loginView.onLoginFailed();
            loginView.hideProgress();
        }
    }

    public LoginView getView() {
        return loginView;
    }
}
