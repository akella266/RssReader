package by.intervale.akella266.rssreader.views.offerNews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.intervale.akella266.rssreader.R;

public class OfferFragment extends Fragment
        implements OfferContract.View{

    Unbinder unbinder;

    @BindView(R.id.edit_text_news)
    EditText mEditTextNews;
    @BindView(R.id.edit_text_phone)
    EditText mEditTextPhone;
    @BindView(R.id.edit_text_email)
    EditText mEditTextEmail;
    @Inject
    OfferContract.Presenter mPresenter;

    @Inject
    public OfferFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        unbinder = ButterKnife.bind(this, view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.dropView();
    }

    @OnClick(R.id.button_send)
    void buttonClick(){

    }

    @Override
    public void showSending() {

    }
}
