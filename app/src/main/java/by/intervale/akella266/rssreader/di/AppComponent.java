package by.intervale.akella266.rssreader.di;

import android.app.Application;

import javax.inject.Singleton;

import by.intervale.akella266.rssreader.BaseApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {AppModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

//    NewsRepository getNewSRepository();

    @Component.Builder
    interface Builder{

        @BindsInstance
        AppComponent.Builder application(Application application);
        AppComponent build();
    }
}
