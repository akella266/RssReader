package by.intervale.akella266.rssreader.data;

import android.support.annotation.NonNull;

import java.util.List;

import by.intervale.akella266.rssreader.data.callbacks.GetNewsCallback;
import by.intervale.akella266.rssreader.data.callbacks.LoadNewsCallback;
import by.intervale.akella266.rssreader.data.callbacks.LoadSourcesCallback;
import by.intervale.akella266.rssreader.data.callbacks.OnClearingCompleteCallback;
import by.intervale.akella266.rssreader.data.callbacks.SourceSavedCallback;

public interface NewsDataSource {

    void getNews(@NonNull String source, @NonNull LoadNewsCallback callback);
    void getNews(@NonNull String id, @NonNull GetNewsCallback callback);
    void saveNews(List<News> news);
    void refreshNews();
    void clearNews(@NonNull String source, @NonNull OnClearingCompleteCallback callback);
    void getSources(@NonNull LoadSourcesCallback callback);
    void addSource(@NonNull Source source, @NonNull SourceSavedCallback callback);
    void addSources(@NonNull SourceSavedCallback callback, @NonNull Source... sources);
    void deleteSource(@NonNull Source source);

}
