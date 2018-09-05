package by.intervale.akella266.rssreader.data.callbacks;

import by.intervale.akella266.rssreader.data.News;

public interface GetNewsCallback {
    void onNewsLoaded(News news);

    void onDataNotAvailable();
}
