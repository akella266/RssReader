package by.intervale.akella266.rssreader.di;

import android.app.Application;

import javax.inject.Singleton;

import by.intervale.akella266.rssreader.BaseApplication;
import by.intervale.akella266.rssreader.data.NewsRepository;
import by.intervale.akella266.rssreader.data.remote.NewsRepositoryModule;
import by.intervale.akella266.rssreader.data.remote.api.ApiModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {NewsRepositoryModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        ApiModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    NewsRepository getNewsRepository();

    @Component.Builder
    interface Builder{

        @BindsInstance
        AppComponent.Builder application(Application application);
        AppComponent build();
    }
}
