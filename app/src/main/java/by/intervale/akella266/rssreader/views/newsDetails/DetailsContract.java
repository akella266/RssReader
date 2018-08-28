package by.intervale.akella266.rssreader.views.newsDetails;

import by.intervale.akella266.rssreader.BasePresenter;
import by.intervale.akella266.rssreader.BaseView;

public interface DetailsContract {

    interface View extends BaseView<Presenter>{
        void showPage(String url);
        void showError(String message);
    }

    interface Presenter extends BasePresenter<View>{
        void loadPage();
        String getNewsId();
        void setNewsId(String newsId);
    }
}
