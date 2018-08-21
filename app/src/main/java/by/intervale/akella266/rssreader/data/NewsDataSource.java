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

    void getNews(@NonNull LoadNewsCallback callback);
    void getNews(@NonNull String id, @NonNull GetNewsCallback callback);
    void saveNews(News news);
    void refreshNews();
    void clearNews();
}
