package by.intervale.akella266.rssreader.di;

import by.intervale.akella266.rssreader.views.main.MainActivity;
import by.intervale.akella266.rssreader.views.main.MainModule;
import by.intervale.akella266.rssreader.views.newsDetails.DetailsActivity;
import by.intervale.akella266.rssreader.views.newsDetails.DetailsModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = DetailsModule.class)
    abstract DetailsActivity detailsActivity();
}
