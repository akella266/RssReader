package by.intervale.akella266.rssreader.views.main;

import by.intervale.akella266.rssreader.BasePresenter;
import by.intervale.akella266.rssreader.BaseView;

public interface MainContract {
    interface View extends BaseView<Presenter>{}
    interface Presenter extends BasePresenter<View>{}
}
