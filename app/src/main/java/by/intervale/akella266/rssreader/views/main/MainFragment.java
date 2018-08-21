package by.intervale.akella266.rssreader.views.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.di.ActivityScoped;

@ActivityScoped
public class MainFragment extends Fragment implements MainContract.View{

    private Unbinder unbinder;
    @BindView(R.id.rv_news)
    RecyclerView mRecycler;
    MainAdapter mAdapter;
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout mRefreshLayout;

    @Inject
    MainContract.Presenter mPresenter;

    @Inject
    public MainFragment() {}

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MainAdapter(getContext());
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
        // Set the scrolling view in the custom SwipeRefreshLayout.
        mRefreshLayout.setmScrollChild(mRecycler);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadNews(false);
            }
        });

        return view;
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
        mAdapter.setNews(news);
    }

    @Override
    public void showLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showNewsDetails(News news) {

    }

    @Override
    public void showError(String message) {
        Snackbar.make(mRecycler, message, Snackbar.LENGTH_SHORT).show();
    }
}
