package by.intervale.akella266.rssreader.views.main.nav;

import by.intervale.akella266.rssreader.di.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class NavModule {

    @FragmentScoped
    @Binds abstract NavFragment provideNavFragment(NavFragment navFragment);
}
