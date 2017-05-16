package com.lin.app;

import com.lin.app.Bean.FeedItem;
import com.lin.app.Models.Interfaces.FindItemInterface;
import com.lin.app.Presenters.MainPresenter;
import com.lin.app.Ui.Views.MainView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainView view;
    @Mock
    FindItemInterface interactor;

    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new MainPresenter(view, interactor);
    }

    @Test
    public void checkRefreshActionNoShowingProgress() {
        presenter.onRefresh();
        verify(view, never()).showProgress();
    }

    @Test
    public void checkRefreshActionCallFindItem() {
        presenter.onRefresh();
        verify(interactor, times(1)).fetchFeedItems(presenter);
    }

    @Test
    public void checkShowsProgressOnResume() {
        presenter.onResume();
        verify(view, times(1)).showProgress();
    }

    @Test
    public void checkIfMessageOnItemClick() {
        presenter.onItemClicked(new FeedItem());
        verify(view, times(1)).showMessage(anyString());
    }

    @Test
    public void checkIfToastDisplayed() {
        ArgumentCaptor<String> captor = forClass(String.class);
        FeedItem item = new FeedItem();
        item.setAuthor("Mike");
        presenter.onItemClicked(item);
        verify(view, times(1)).showMessage(captor.capture());
        assertThat(captor.getValue(), is("Mike's item clicked"));
    }

    @Test
    public void checkIfViewNullOnDestroy() {
        presenter.onDestroy();
        assertNull(presenter.getMainView());
    }

    @Test
    public void checkIfItemsPassedToView() {
        FeedItem item1 = new FeedItem();
        FeedItem item2 = new FeedItem();
        FeedItem item3 = new FeedItem();

        List<FeedItem> items = Arrays.asList(item1, item2, item3);
        presenter.onFinished(items);
        verify(view, times(1)).setItems(items);
        verify(view, times(1)).hideProgress();
    }
}
