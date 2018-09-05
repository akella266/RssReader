package by.intervale.akella266.rssreader.data.callbacks;

import java.util.List;

import by.intervale.akella266.rssreader.data.Source;

public interface LoadSourcesCallback {
    void onSourcesLoaded(List<Source> sources);
}
