package com.lin.app;

import com.lin.app.Models.LoginModel;
import com.lin.app.Presenters.LoginPresenter;
import com.lin.app.Ui.Views.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by brooklin on 2017-05-12.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    LoginView view;

    @Mock
    LoginModel interactor;

    LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, interactor);
    }

    @Test
    public void onUsernameError() throws Exception {
        presenter.onUsernameError();
        verify(view, times(1)).setUsernameError();
        verify(view, times(1)).hideProgress();
    }

    @Test
    public void onPasswordError() throws Exception {
        presenter.onPasswordError();
        verify(view, times(1)).setPasswordError();
        verify(view, times(1)).hideProgress();
    }

    @Test
    public void onSuccess() throws Exception {
        presenter.onSuccess();
        verify(view, times(1)).onLoginSuccess();
    }

    @Test
    public void onFailed() throws Exception {
        presenter.onFailed();
        verify(view, times(1)).onLoginFailed();
        verify(view, times(1)).hideProgress();
    }

    @Test
    public void checkIfShowsProgress() throws Exception {
        presenter.validateCredentials("", "");
        verify(view, times(1)).showProgress();
        verify(interactor, times(1)).login("","", presenter);

        presenter.validateCredentials("name", "");
        verify(view, times(2)).showProgress();
        verify(interactor, times(1)).login("name","", presenter);
    }

    @Test
    public void checkIfViewReleasedOnDestroy() {
        presenter.onDestroy();
        assertNull(presenter.getView());
    }
}
