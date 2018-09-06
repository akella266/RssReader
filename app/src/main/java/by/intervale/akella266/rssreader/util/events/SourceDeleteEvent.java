package by.intervale.akella266.rssreader.util.events;

import by.intervale.akella266.rssreader.data.Source;

public class SourceDeleteEvent {

    private Source source;

    public SourceDeleteEvent(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return source;
    }
}
