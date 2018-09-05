package by.intervale.akella266.rssreader.views.offerNews;

import android.support.v4.app.DialogFragment;

import by.intervale.akella266.rssreader.di.ActivityScoped;
import by.intervale.akella266.rssreader.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OfferModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract OfferFragment offerFragment();

    @ActivityScoped
    @Binds abstract OfferContract.Presenter offerPresenter(OfferPresenter offerPresenter);

    @ActivityScoped
    @Binds abstract DialogFragment dialogFragment(LoadingDialogFragment loadingDialogFragment);
}
