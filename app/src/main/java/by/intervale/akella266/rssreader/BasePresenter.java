package by.intervale.akella266.rssreader;

public interface BasePresenter<T> {

    void takeView(T view);
    void dropView();
}
