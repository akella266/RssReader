package by.intervale.akella266.rssreader.data.local;

import android.arch.persistence.room.Insert;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.data.NewsDataSource;
import by.intervale.akella266.rssreader.data.Source;
import by.intervale.akella266.rssreader.data.callbacks.GetNewsCallback;
import by.intervale.akella266.rssreader.data.callbacks.LoadNewsCallback;
import by.intervale.akella266.rssreader.data.callbacks.LoadSourcesCallback;
import by.intervale.akella266.rssreader.data.callbacks.OnClearingCompleteCallback;
import by.intervale.akella266.rssreader.data.callbacks.SourceSavedCallback;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsLocalDataSource implements NewsDataSource {

    private NewsDao mNewsDao;
    private SourceDao mSourceDao;

    @Inject
    public NewsLocalDataSource(@NonNull NewsDao mNewsDao,
                               @NonNull SourceDao mSourceDao) {
        this.mNewsDao = mNewsDao;
        this.mSourceDao = mSourceDao;
    }

    @Override
    public void getNews(@NonNull String source, @NonNull LoadNewsCallback callback) {
        String s = source.split("/")[2];
        Log.d("Database", "getNews(all) Time:" + Calendar.getInstance().getTimeInMillis());
        mNewsDao.getAllNews(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<News>>() {
                    @Override
                    public void onSuccess(List<News> news) {
                        Log.d("Database", "News has been loaded");
                        callback.onNewsLoaded(news);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("Database", "News not loaded");
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getNews(@NonNull String id, @NonNull GetNewsCallback callback) {
        Log.d("Database", "getNews(id) Time:" + Calendar.getInstance().getTimeInMillis());
        mNewsDao.getNews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<News>() {
                    @Override
                    public void onSuccess(News news) {
                        callback.onNewsLoaded(news);
                    }
                    @Override
                    public void onError(Throwable e) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void saveNews(List<News> news) {
        Log.d("Database", "saveNews Time:" + Calendar.getInstance().getTimeInMillis());
        Completable.fromAction(() -> mNewsDao.addNews(news))
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d("Database", "News has been saved");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d("Database", "News not saved\n" + e.toString());
                    }
                });
    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void clearNews(@NonNull String source, @NonNull OnClearingCompleteCallback callback) {
        Log.d("Database", "clearNews Time:" + Calendar.getInstance().getTimeInMillis());
        Completable.fromAction(() -> mNewsDao.removeNewsFromSource(source.split("/")[2]))
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d("Database", "News has been cleared");
                        callback.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Database", "News not cleared");
                    }
                });
    }

    @Override
    public void getSources(@NonNull LoadSourcesCallback callback) {
        mSourceDao.getAllSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Source>>() {
                    @Override
                    public void onSuccess(List<Source> sources) {
                        Log.d("Database", "Sources has been loaded");
                        callback.onSourcesLoaded(sources);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Database", "Sources not loaded");
                        callback.onSourcesLoaded(new ArrayList<>());
                    }
                });
    }

    @Override
    public void addSource(@NonNull Source source, @NonNull SourceSavedCallback callback) {

        Completable.fromAction(() -> mSourceDao.insert(source))
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d("Database", "Source has been saved");
                        callback.onSaved();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Database", "Source not been saved");
                        callback.onError();
                    }
                });
    }

    @Override
    public void addSources(@NonNull SourceSavedCallback callback, @NonNull Source... sources) {
        Completable.fromAction(() -> mSourceDao.insert(sources))
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d("Database", "Sources has been saved");
                        callback.onSaved();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Database", "Sources not been saved");
                        callback.onError();
                    }
                });
    }

    @Override
    public void deleteSource(@NonNull Source source) {

    }
}
