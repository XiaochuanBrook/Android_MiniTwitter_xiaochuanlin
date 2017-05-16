package com.lin.app;

import android.net.Uri;

import com.lin.app.Models.PostDataModel;
import com.lin.app.Presenters.PostPresenter;
import com.lin.app.Ui.Views.PostView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by brooklin on 2017-05-12.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostPresenterTest {
    @Mock
    PostView view;
    @Mock
    PostDataModel interactor;

    private PostPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new PostPresenter(view, interactor);
    }

    @Test
    public void pictureAddTest() throws Exception {
        ArgumentCaptor<Uri> captor = forClass(Uri.class);
        presenter.addPicture(captor.capture());
        verify(view, times(1)).onImageAdd(captor.capture());
    }

    @Test
    public void postTestTest() throws Exception {
        presenter.post("post");
        verify(interactor, times(1)).post("post", presenter);
    }

    @Test
    public void checkIfViewIsReleasedOnDestroy() {
        presenter.onDestroy();
        assertNull(presenter.getView());
    }

}
