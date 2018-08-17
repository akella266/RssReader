package by.intervale.akella266.rssreader.views.main;

import javax.inject.Inject;

import by.intervale.akella266.rssreader.di.ActivityScoped;

@ActivityScoped
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    @Inject
    MainPresenter(){

    }


    @Override
    public void takeView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
