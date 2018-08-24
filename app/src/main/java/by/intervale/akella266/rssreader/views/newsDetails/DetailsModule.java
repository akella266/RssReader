package by.intervale.akella266.rssreader.views.newsDetails;

import by.intervale.akella266.rssreader.di.ActivityScoped;
import by.intervale.akella266.rssreader.di.FragmentScoped;
import by.intervale.akella266.rssreader.views.main.MainContract;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static by.intervale.akella266.rssreader.views.newsDetails.DetailsActivity.EXTRA_NEWS_ID;

@Module
public abstract class DetailsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DetailsFragment detailsFragment();

    @ActivityScoped
    @Binds abstract DetailsContract.Presenter detailsPresenter(DetailsPresenter detailsPresenter);

    @Provides
    @ActivityScoped
    static String provideNewsId(DetailsActivity detailsActivity){
        return detailsActivity.getIntent().getStringExtra(EXTRA_NEWS_ID);
    }
}
