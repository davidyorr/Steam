package com.mangoshine.steam.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.mangoshine.steam.R;
import com.mangoshine.steam.core.market.MarketListingsListAdapter;
import com.mangoshine.steam.io.MarketClient;

public class MarketFragment extends AbstractBaseFragment
                            implements SwipeRefreshLayout.OnRefreshListener,
                                       ObservableScrollViewCallbacks {
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mHeader;
    View mMarketFragmentContainer;
    View mMarketSwipeContainer;
    ObservableListView mListView;
    MarketClient mMarketClient;
    MarketListingsListAdapter mAdapter;

    String[] mMarketGames;
    private int mBaseTranslationY;

    private final int MENU_GROUP_ID = 0;

    public MarketFragment() {
        mLayout = R.layout.fragment_market;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mMarketGames = getResources().getStringArray(R.array.market_games);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mHeader = view.findViewById(R.id.header);
        mMarketFragmentContainer = view.findViewById(R.id.market_fragment_container);
        mMarketSwipeContainer = view.findViewById(R.id.market_swipe_container);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.market_swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mListView = (ObservableListView) view.findViewById(R.id.market_listings_scrollable_list);
        mAdapter = new MarketListingsListAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mListView.setScrollViewCallbacks(this);

        // add padding where the header is
        mListView.addHeaderView(inflater.inflate(R.layout.blank_toolbar, mListView, false));
        mListView.addHeaderView(inflater.inflate(R.layout.blank_list_header, mListView, false));

        // move the swipe refresh animation down to account for the padding
        mHeader.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mSwipeRefreshLayout.setProgressViewOffset(false, mHeader.getMeasuredHeight() - (mHeader.getMeasuredHeight() / 4), mHeader.getMeasuredHeight() + (mHeader.getMeasuredHeight() / 4));

        mMarketClient = new MarketClient();
        mMarketClient.refreshPopularListings(mAdapter, mSwipeRefreshLayout);

        setTitle(mMarketGames[0]);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        for (int i = 0; i < mMarketGames.length; i++) {
            menu.add(MENU_GROUP_ID, i, Menu.NONE, mMarketGames[i]);
        }
    }

    @Override
    public void onRefresh() {
        mMarketClient.refreshPopularListings(mAdapter, mSwipeRefreshLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        setTitle(mMarketGames[id]);

        // popular
        if (id == 0) {
            mMarketClient.refreshPopularListings(mAdapter, mSwipeRefreshLayout);
        }
        // all
        else if (id == 1) {

        }
        // individual games
        else {
            mMarketClient.refreshGameListings(mAdapter, mSwipeRefreshLayout, item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (dragging) {
            int toolbarHeight = mToolbar.getHeight();
            if (firstScroll) {
                float currentHeaderTranslationY = mHeader.getTranslationY();
                if (-toolbarHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
            mHeader.animate().cancel();
            mHeader.setTranslationY(headerTranslationY);
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        mBaseTranslationY = 0;

        if (scrollState == ScrollState.DOWN) {
            showToolbar();
        } else if (scrollState == ScrollState.UP) {
            int toolbarHeight = mToolbar.getHeight();
            int scrollY = mListView.getCurrentScrollY();
            if (toolbarHeight <= scrollY) {
                hideToolbar();
            } else {
                showToolbar();
            }
        } else {
            if (!toolbarIsShown() && !toolbarIsHidden()) {
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown() {
        return mHeader.getTranslationY() == 0;
    }

    private boolean toolbarIsHidden() {
        return mHeader.getTranslationY() == -mToolbar.getHeight();
    }

    private void showToolbar() {
        float headerTranslationY = mHeader.getTranslationY();
        if (headerTranslationY != 0) {
            mHeader.animate().cancel();
            mHeader.animate().translationY(0).setDuration(200).start();
        }
    }

    private void hideToolbar() {
        float headerTranslationY = mHeader.getTranslationY();
        int toolbarHeight = mToolbar.getHeight();
        if (headerTranslationY != - toolbarHeight) {
            mHeader.animate().cancel();
            mHeader.animate().translationY(-toolbarHeight).setDuration(200).start();
        }
    }
}
