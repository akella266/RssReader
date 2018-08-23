package by.intervale.akella266.rssreader.views.main;

import java.util.List;

import by.intervale.akella266.rssreader.BasePresenter;
import by.intervale.akella266.rssreader.BaseView;
import by.intervale.akella266.rssreader.data.News;

public interface MainContract {
    interface View extends BaseView<Presenter>{
        void showNews(List<News> news);
        void showLoadingIndicator(boolean active);
        void showNewsDetails(News news);
        void showError(String message);
    }
    interface Presenter extends BasePresenter<View>{
        void loadNews(final boolean showLoadingUI);
        void setFiltering(NewsFilterType type);
        boolean checkNetworkAvailable();
        void setSource(String source);
        void openNewsDetails(News news);
    }
}
