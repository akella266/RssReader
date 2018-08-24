package by.intervale.akella266.rssreader.data;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import by.intervale.akella266.rssreader.data.NewsDataSource;
import by.intervale.akella266.rssreader.data.local.Local;
import by.intervale.akella266.rssreader.data.local.NewsDao;
import by.intervale.akella266.rssreader.data.local.NewsDatabase;
import by.intervale.akella266.rssreader.data.local.NewsLocalDataSource;
import by.intervale.akella266.rssreader.data.remote.NewsRemoteDataSource;
import by.intervale.akella266.rssreader.data.remote.Remote;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class NewsRepositoryModule {

    @Singleton
    @Provides
    NewsDatabase provideNewsDatabase(Application context){
        return Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "news.db")
                .build();
    }

    @Singleton
    @Provides
    NewsDao provideNewsDao(NewsDatabase newsDatabase){
        return newsDatabase.newsDao();
    }

    @Singleton
    @Provides
    @Remote
    NewsDataSource provideNewsRemoteDataSource(){
        return new NewsRemoteDataSource();
    }

    @Singleton
    @Provides
    @Local
    NewsDataSource provideNewsLocalDataSource(NewsDao dao){
        return new NewsLocalDataSource(dao);
    }
}
