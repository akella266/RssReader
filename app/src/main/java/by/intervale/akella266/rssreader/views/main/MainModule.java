package by.intervale.akella266.rssreader.views.main;

import by.intervale.akella266.rssreader.di.ActivityScoped;
import by.intervale.akella266.rssreader.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @ActivityScoped
    @Binds abstract MainContract.Presenter mainPresenter(MainPresenter mainPresenter);
}
