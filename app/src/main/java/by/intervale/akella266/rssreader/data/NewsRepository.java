package by.intervale.akella266.rssreader.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.intervale.akella266.rssreader.data.remote.Remote;

@Singleton
public class NewsRepository implements NewsDataSource {

    private final NewsDataSource mNewsRemoteDataSource;

    Map<String, News> mCachedNews;
    boolean mCacheisDirty = false;

    @Inject
    public NewsRepository(@Remote NewsDataSource mNewsRemoteDataSource) {
        this.mNewsRemoteDataSource = mNewsRemoteDataSource;
    }

    @Override
    public void getNews(@NonNull LoadNewsCallback callback) {

        if (mCachedNews != null && !mCacheisDirty){
            callback.onNewsLoaded(new ArrayList<>(mCachedNews.values()));
            return;
        }

        if(mCacheisDirty){
            getTasksFromRemoveDataSource(callback);
        }
        else{
            //запрос из бд
            getTasksFromRemoveDataSource(callback);
        }
    }

    @Override
    public void getNews(@NonNull String id, @NonNull GetNewsCallback callback) {

    }

    @Override
    public void saveNews(News news) {

    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void clearNews() {

    }

    private void getTasksFromRemoveDataSource(@NonNull final LoadNewsCallback callback){
        mNewsRemoteDataSource.getNews(new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                refreshCache(news);
                callback.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<News> news){
        if(mCachedNews == null){
            mCachedNews = new HashMap<>();
        }
        mCachedNews.clear();
        for(News item : news){
            mCachedNews.put(item.getId(), item);
        }
        mCacheisDirty = false;
    }
}
