package by.intervale.akella266.rssreader.data;

import android.support.annotation.NonNull;

import java.util.List;

public interface NewsDataSource {

    interface LoadNewsCallback {

        void onNewsLoaded(List<News> news);

        void onDataNotAvailable();
    }

    interface GetNewsCallback {

        void onNewsLoaded(News news);

        void onDataNotAvailable();
    }

    interface OnClearingCompleteCallback{
        void onComplete();
    }

    void getNews(@NonNull String source, @NonNull LoadNewsCallback callback);
    void getNews(@NonNull String id, @NonNull GetNewsCallback callback);
    void saveNews(List<News> news);
    void refreshNews();
    void clearNews(@NonNull String source, @NonNull OnClearingCompleteCallback callback);
}
