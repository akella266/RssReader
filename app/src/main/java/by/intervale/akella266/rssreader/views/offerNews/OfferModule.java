package by.intervale.akella266.rssreader.views.offerNews;

import by.intervale.akella266.rssreader.di.ActivityScoped;
import by.intervale.akella266.rssreader.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OfferModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract OfferFragment offerFragment();

    @ActivityScoped
    @Binds abstract OfferContract.Presenter offerPresenter(OfferPresenter offerPresenter);
}
