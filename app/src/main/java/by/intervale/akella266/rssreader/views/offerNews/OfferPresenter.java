package by.intervale.akella266.rssreader.views.offerNews;

import android.content.Context;
import android.util.Patterns;

import javax.inject.Inject;

public class OfferPresenter implements OfferContract.Presenter {

    private Context mContext;
    private OfferContract.View mView;
    private static final String EMAIL_PATTERN = "(?:[az0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

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

    @Override
    public void openSending() {
        mView.showSending();
    }

    @Override
    public void closeSending() {
        mView.closeSending();
    }

    @Override
    public void openComplete() {
        mView.showComplete();
    }

    @Override
    public void initReset() {
        mView.resetFields();
    }

    @Override
    public boolean isValideEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean isValidePhoneNumber(String phoneNumer) {
        return Patterns.PHONE.matcher(phoneNumer).matches() &&
                (phoneNumer.length() < 6 || phoneNumer.length() > 13);
    }
}
