package by.intervale.akella266.rssreader;


import by.intervale.akella266.rssreader.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {

//    @Inject
//    NewsRepository mNewsRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

//    public NewsRepository getNewsRepository(){
//        return mNewsRepository;
//    }
}
