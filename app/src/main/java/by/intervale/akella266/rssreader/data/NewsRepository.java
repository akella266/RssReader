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

    private Map<String, News> mCachedNews;
    private boolean mCacheIsDirty = false;
    private String mOldSource;

    @Inject
    public NewsRepository(@Remote NewsDataSource mNewsRemoteDataSource) {
        this.mNewsRemoteDataSource = mNewsRemoteDataSource;
        this.mOldSource = "";
    }

    @Override
    public void getNews(@NonNull String source, @NonNull LoadNewsCallback callback) {

        if (mCachedNews != null && !mCacheIsDirty && source.equals(mOldSource)){
            callback.onNewsLoaded(new ArrayList<>(mCachedNews.values()));
            return;
        }

        if(mCacheIsDirty){
            getTasksFromRemoveDataSource(source, callback);
        }
        else{
            //запрос из бд
            getTasksFromRemoveDataSource(source, callback);
        }
    }

    @Override
    public void getNews(@NonNull String source, @NonNull String id, @NonNull GetNewsCallback callback) {

    }

    @Override
    public void saveNews(@NonNull String source, News news) {

    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void clearNews() {

    }

    private void getTasksFromRemoveDataSource(@NonNull String source, @NonNull final LoadNewsCallback callback){
        mNewsRemoteDataSource.getNews(source, new LoadNewsCallback() {
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
        mCacheIsDirty = false;
    }
}
