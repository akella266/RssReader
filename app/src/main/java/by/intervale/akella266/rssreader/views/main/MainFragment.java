package by.intervale.akella266.rssreader.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.di.ActivityScoped;
import by.intervale.akella266.rssreader.util.SourceChangedEvent;
import by.intervale.akella266.rssreader.views.newsDetails.DetailsActivity;

@ActivityScoped
public class MainFragment extends Fragment implements MainContract.View{

    private Unbinder unbinder;
    @BindView(R.id.rv_news)
    RecyclerView mRecycler;
    @BindView(R.id.text_view_no_news)
    TextView mTextViewNoContent;
    MainAdapter mAdapter;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.tab_layout)
    TabLayout mTabs;

    @Inject
    MainContract.Presenter mPresenter;

    @Inject
    public MainFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new MainAdapter(getActivity().getApplicationContext(), news -> mPresenter.openNewsDetails(news));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter);

        mRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        mRefreshLayout.setmScrollChild(mRecycler);
        mRefreshLayout.setOnRefreshListener(() -> mPresenter.loadNews(true, false));

        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:{
                        mPresenter.setFiltering(NewsFilterType.IN_WORLD);
                        break;
                    }
                    case 1:{
                        mPresenter.setFiltering(NewsFilterType.SOCIETY);
                        break;
                    }
                    case 2:{
                        mPresenter.setFiltering(NewsFilterType.REALITY);
                        break;
                    }
                    case 3:{
                        mPresenter.setFiltering(NewsFilterType.AUTO);
                        break;
                    }
                    case 4:{
                        mPresenter.setFiltering(NewsFilterType.TECH);
                        break;
                    }
                    case 5:{
                        mPresenter.setFiltering(NewsFilterType.FINANCE);
                        break;
                    }
                    case 6:{
                        mPresenter.setFiltering(NewsFilterType.SPORT);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void showNews(List<News> news) {
        mTextViewNoContent.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        mRecycler.getLayoutManager().scrollToPosition(0);
        mAdapter.setNews(news);
    }

    @Override
    public void showTitle(String title) {
        AppCompatActivity activity;
        if ((activity = (AppCompatActivity)getActivity()) != null){
            ActionBar bar = activity.getSupportActionBar();
            if(bar != null){
                bar.setTitle(title);
            }
        }
    }

    @Override
    public void showLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        mRefreshLayout.post(() -> mRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showNewsDetails(News news) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_NEWS_ID, news.getId());
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(this.getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNoNews() {
        mTextViewNoContent.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSourceChanged(SourceChangedEvent event){
        String sourceUrl = event.getSourceUrl();
        mPresenter.setSource(sourceUrl);
        mPresenter.loadNews(true, false);
    }
}
