package by.intervale.akella266.rssreader.views.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import javax.inject.Inject;

import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.data.NewsDataSource;
import by.intervale.akella266.rssreader.data.NewsRepository;
import by.intervale.akella266.rssreader.di.ActivityScoped;

@ActivityScoped
public class MainPresenter implements MainContract.Presenter {

    private Context mContext;
    private MainContract.View mView;
    private final NewsRepository mNewsRepository;

    @Inject
    MainPresenter(Context context, NewsRepository newsRepository){
        this.mContext = context;
        this.mNewsRepository = newsRepository;
    }

    @Override
    public void loadNews(final boolean showLoadingUI){
        if (showLoadingUI && mView != null){
            mView.showLoadingIndicator(true);
        }

        if (!checkNetworkAvailable()){
            mView.showError("Интернет недоступен");
            return;
        }

        mNewsRepository.getNews(new NewsDataSource.LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                mView.showNews(news);
                mView.showLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable() {
                mView.showError("Сервер недоступен");
            }
        });
    }

    @Override
    public void takeView(MainContract.View view) {
        mView = view;
        loadNews(true);
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void setFiltering(NewsFilteringType type) {

    }

    @Override
    public boolean checkNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;

    }
}
