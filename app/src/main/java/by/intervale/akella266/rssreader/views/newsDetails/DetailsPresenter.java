package by.intervale.akella266.rssreader.views.newsDetails;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.data.NewsDataSource;
import by.intervale.akella266.rssreader.data.NewsRepository;
import by.intervale.akella266.rssreader.data.callbacks.GetNewsCallback;

public class DetailsPresenter implements DetailsContract.Presenter{

    private Context mContext;
    private DetailsContract.View mView;
    private NewsRepository mNewsRepository;
    @Nullable
    private String mNewsId;

    @Inject
    DetailsPresenter(NewsRepository mNewsRepository,
                            @Nullable String mNewsId) {
        this.mNewsRepository = mNewsRepository;
        this.mNewsId = mNewsId;
    }

    @Override
    public void loadPage() {
        mNewsRepository.getNews(mNewsId, new GetNewsCallback() {
            @Override
            public void onNewsLoaded(News news) {
                Log.i("Link", news.getLink());
                mView.showPage(news.getLink());
            }

            @Override
            public void onDataNotAvailable() {
                mView.showError(mContext.getString(R.string.error_invalid_url));
            }
        });
    }

    @Override
    public void takeView(DetailsContract.View view) {
        this.mView = view;
        loadPage();
    }

    @Override
    public void dropView() {
        this.mView = null;
    }

    @Override
    public String getNewsId() {
        return mNewsId;
    }

    @Override
    public void setNewsId(String newsId) {
        this.mNewsId = newsId;
    }
}
