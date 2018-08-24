package by.intervale.akella266.rssreader.views.main;

public enum NewsFilterType {
    IN_WORLD(0),
    SOCIETY(1),
    REALITY(2),
    AUTO(3),
    TECH(4),
    FINANCE(5),
    SPORT(6);

    private int index;

    NewsFilterType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
