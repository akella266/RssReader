package by.intervale.akella266.rssreader.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.intervale.akella266.rssreader.data.local.Local;
import by.intervale.akella266.rssreader.data.local.NewsLocalDataSource;
import by.intervale.akella266.rssreader.data.remote.Remote;

@Singleton
public class NewsRepository implements NewsDataSource {

    private final NewsDataSource mNewsRemoteDataSource;
    private final NewsDataSource mNewsLocalDataSource;

    @Inject
    NewsRepository(@Remote NewsDataSource mNewsRemoteDataSource,
                          @Local NewsDataSource mNewsLocalDataSource) {
        this.mNewsRemoteDataSource = mNewsRemoteDataSource;
        this.mNewsLocalDataSource = mNewsLocalDataSource;
    }

    @Override
    public void getNews(@NonNull String source, @NonNull LoadNewsCallback callback) {
        getTasksFromRemoveDataSource(source, callback);
    }

    @Override
    public void getNews(@NonNull String id, @NonNull GetNewsCallback callback) {
        mNewsLocalDataSource.getNews(id, new GetNewsCallback() {
            @Override
            public void onNewsLoaded(News news) {
                callback.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveNews(List<News> news) {
        mNewsLocalDataSource.saveNews(news);
    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void clearNews(@NonNull String source, @NonNull OnClearingCompleteCallback callback) {

    }

    private void getTasksFromRemoveDataSource(@NonNull String source, @NonNull final LoadNewsCallback callback){
        mNewsRemoteDataSource.getNews(source, new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                mNewsLocalDataSource.clearNews(source, () -> saveNews(news));
                callback.onNewsLoaded(new ArrayList<>(news));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void getTasksFromLocalDataSource(@NonNull String source, @NonNull final LoadNewsCallback callback){
        mNewsLocalDataSource.getNews(source, new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                callback.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

}
