package by.intervale.akella266.rssreader.util;

import by.intervale.akella266.rssreader.views.main.NewsFilterType;

public class SourceChangedEvent {
    private String sourceUrl;

    public SourceChangedEvent(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
}
