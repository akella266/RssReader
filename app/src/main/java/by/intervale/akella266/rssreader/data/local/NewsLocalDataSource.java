package by.intervale.akella266.rssreader.data.local;

import android.arch.persistence.room.Insert;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.data.NewsDataSource;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsLocalDataSource implements NewsDataSource {

    private NewsDao mNewsDao;

    @Inject
    public NewsLocalDataSource(@NonNull NewsDao mNewsDao) {
        this.mNewsDao = mNewsDao;
    }

    @Override
    public void getNews(@NonNull String source, @NonNull LoadNewsCallback callback) {
        String s = source.split("/")[2];
        Log.i("Database", "getNews(all) Time:" + Calendar.getInstance().getTimeInMillis());
        mNewsDao.getAllNews(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<News>>() {
                    @Override
                    public void onSuccess(List<News> news) {
                        Log.i("Database", "News has been loaded");
                        callback.onNewsLoaded(news);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Database", "News not loaded");
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getNews(@NonNull String id, @NonNull GetNewsCallback callback) {
        Log.i("Database", "getNews(id) Time:" + Calendar.getInstance().getTimeInMillis());
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
        Log.i("Database", "saveNews Time:" + Calendar.getInstance().getTimeInMillis());
        Completable.fromAction(() -> mNewsDao.addNews(news))
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("Database", "News has been saved");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("Database", "News not saved\n" + e.toString());
                    }
                });
    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void clearNews(@NonNull String source, @NonNull OnClearingCompleteCallback callback) {
        Log.i("Database", "clearNews Time:" + Calendar.getInstance().getTimeInMillis());
        Completable.fromAction(() -> mNewsDao.removeNewsFromSource(source.split("/")[2]))
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("Database", "News has been cleared");
                        callback.onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Database", "News not cleared");
                    }
                });
    }
}
