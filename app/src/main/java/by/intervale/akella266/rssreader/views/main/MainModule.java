package by.intervale.akella266.rssreader.views.main;

import android.app.DialogFragment;
import android.support.v7.widget.RecyclerView;

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
