package by.intervale.akella266.rssreader.views.offerNews;

import by.intervale.akella266.rssreader.BasePresenter;
import by.intervale.akella266.rssreader.BaseView;

public interface OfferContract {
    interface View extends BaseView<Presenter>{
        void showSending();
        void closeSending();
        void showComplete();
        void resetFields();
        void showMessage(String message);
    }
    interface Presenter extends BasePresenter<View> {
        void offerNews();
        void openSending();
        void closeSending();
        void openComplete();
        void initReset();
    }
}
