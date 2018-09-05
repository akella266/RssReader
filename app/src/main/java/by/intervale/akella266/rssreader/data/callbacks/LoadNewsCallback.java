package by.intervale.akella266.rssreader.data.callbacks;

import java.util.List;

import by.intervale.akella266.rssreader.data.News;

public interface LoadNewsCallback {
    void onNewsLoaded(List<News> news);

    void onDataNotAvailable();
}
