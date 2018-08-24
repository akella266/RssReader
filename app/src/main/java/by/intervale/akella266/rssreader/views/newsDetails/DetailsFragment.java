package by.intervale.akella266.rssreader.views.newsDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.rssreader.R;

public class DetailsFragment extends Fragment
        implements DetailsContract.View{

    private Unbinder unbinder;

    @Inject
    DetailsContract.Presenter mPresenter;
    @BindView(R.id.web_view)
    WebView mWebView;

    @Inject
    public DetailsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        mWebView.setWebViewClient(new WebViewClient());
        return view;
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void showPage(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mWebView, message, Snackbar.LENGTH_SHORT).show();
    }
}
