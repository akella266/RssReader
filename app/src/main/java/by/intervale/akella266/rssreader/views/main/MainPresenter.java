package by.intervale.akella266.rssreader.views.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.data.NewsDataSource;
import by.intervale.akella266.rssreader.data.NewsRepository;
import by.intervale.akella266.rssreader.data.Source;
import by.intervale.akella266.rssreader.data.callbacks.LoadNewsCallback;
import by.intervale.akella266.rssreader.di.ActivityScoped;
import by.intervale.akella266.rssreader.util.CategoriesUtil;
import by.intervale.akella266.rssreader.util.SourceChangedEvent;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

@ActivityScoped
public class MainPresenter implements MainContract.Presenter {

    private Context mContext;
    private MainContract.View mView;
    private final NewsRepository mNewsRepository;
    private boolean isFirstLoad = true;
    private String mSourceUrl;
    private NewsFilterType mFilteringType;

    @Inject
    MainPresenter(Context context, NewsRepository newsRepository){
        this.mContext = context;
        this.mNewsRepository = newsRepository;
        mFilteringType = NewsFilterType.IN_WORLD;
    }

    @Override
    public void loadNews(final boolean showLoadingUI, boolean filteringUpdate){
        if (showLoadingUI && mView != null){
            mView.showLoadingIndicator(true);
        }

        if (!checkNetworkAvailable() || filteringUpdate){
            if (!filteringUpdate)
                mView.showError(mContext.getString(R.string.error_no_connection));

            if (mSourceUrl != null && !mSourceUrl.isEmpty()) {
                mNewsRepository.getTasksFromLocalDataSource(mSourceUrl, new LoadNewsCallback() {
                    @Override
                    public void onNewsLoaded(List<News> news) {
                        Collections.sort(news, (news1, t1) -> (-1) * news1.getDate().compareTo(t1.getDate()));
                        passNews(news);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mView.showError(mContext.getString(R.string.error_read_from_database));
                    }
                });
                return;
            }
        }

        mNewsRepository.getNews(mSourceUrl, new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(List<News> news) {
                passNews(news);
            }

            @Override
            public void onDataNotAvailable() {
                if (mView != null)
                    mView.showError(mContext.getString(R.string.error_server_not_respond));
            }
        });
    }

    private void passNews(List<News> news){
        String category = mContext.getResources()
                .getStringArray(R.array.categories)[mFilteringType.getIndex()];
        List<String> cats = CategoriesUtil.getCategories(mContext, mFilteringType);
        for(int i = 0; i < news.size(); i++){
            if (!cats.contains(news.get(i).getCategories().get(0))){
                news.remove(i);
                i--;
            }
        }
        mView.showTitle(category);
        if (news.isEmpty())
            mView.showNoNews();
        else {
            mView.showNews(news);
        }
        mView.showLoadingIndicator(false);
    }

    @Override
    public void takeView(MainContract.View view) {
        mView = view;
        if(isFirstLoad) firstLoad();
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void setFiltering(NewsFilterType type) {
        this.mFilteringType = type;
        loadNews(false, true);
    }

    @Override
    public boolean checkNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;

    }

    @Override
    public void setSource(String source) {
        mSourceUrl = source;
    }

    @Override
    public void openNewsDetails(News news) {
        mView.showNewsDetails(news);
    }

    @Override
    public void openDialog() {
        mView.showDialog();
    }

    private void firstLoad(){
        mNewsRepository.getSources(sources -> {
            if (sources.isEmpty()) mView.showNoNews();
            else {
                mSourceUrl = sources.get(0).getUrl();
                isFirstLoad = false;
                loadNews(true, false);
            }
        });
    }
}
