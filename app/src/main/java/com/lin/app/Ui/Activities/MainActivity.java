
package com.lin.app.Ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lin.app.Bean.FeedItem;
import com.lin.app.Presenters.MainPresenter;
import com.lin.app.R;
import com.lin.app.Twitter;
import com.lin.app.Utils.LoginUtil;
import com.lin.app.Ui.Views.MainView;
import com.lin.app.Ui.RecyclerViewAdapter.FeedsRecyclerViewAdapter;
import com.lin.app.Ui.Views.OnItemClickListener;
import com.lin.app.di.MainActivityModule;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView, OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    public static final int POST = 102;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    @BindView(R.id.fab)
    View mFab;

    @BindView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @Inject
    public MainPresenter presenter;
    private FeedsRecyclerViewAdapter mAdapter;
    private boolean mIsLoadedMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, PostActivity.class), POST);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        if (!LoginUtil.isUserSignedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void setupComponent() {
        ((Twitter)Twitter.getAppContext())
                .getAppComponent()
                .getMainActivityComponent(new MainActivityModule(this))
                .inject(this);
    }

    @Override
    protected void onResume() {
        setupComponent();
        super.onResume();
        presenter.onResume();
        mIsLoadedMore = false;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                LoginUtil.saveUserInfo("", "");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(final List<FeedItem> items) {
        final FeedItem fakeFeedItem = new FeedItem();
        fakeFeedItem.setAuthor(FeedItem.FAKE_FOOTER_FEED_KEY);
        items.add(0, fakeFeedItem);

        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }

        mAdapter = new FeedsRecyclerViewAdapter(this, items);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        if (mIsLoadedMore) {
            mRecyclerView.scrollToPosition(0);
            mIsLoadedMore = false;
        }


    }

    @Override
    public void showMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public
    void onItemClick(final FeedItem item) {
        if (item.getAuthor().equals(FeedItem.FAKE_FOOTER_FEED_KEY)) {
            if (!mIsLoadedMore) {
                presenter.onLoadMore();
                mIsLoadedMore = true;
                showMessage("loading more data.....");
            }
        } else  {
            presenter.onItemClicked(item);
        }
    }


    @Override
    protected
    void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == POST ) {
            showMessage("post sent");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
