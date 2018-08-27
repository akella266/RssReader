package by.intervale.akella266.rssreader.views.offerNews;

import android.content.Context;

import javax.inject.Inject;

public class OfferPresenter implements OfferContract.Presenter {

    private Context mContext;
    private OfferContract.View mView;

    @Inject
    public OfferPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void offerNews() {

    }

    @Override
    public void takeView(OfferContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        this.mView = null;
    }
}
