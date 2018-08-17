package by.intervale.akella266.rssreader.di;

import by.intervale.akella266.rssreader.views.main.MainActivity;
import by.intervale.akella266.rssreader.views.main.MainModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
