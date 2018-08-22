package by.intervale.akella266.rssreader.data.remote;

import javax.inject.Singleton;

import by.intervale.akella266.rssreader.data.NewsDataSource;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class NewsRepositoryModule {

    @Singleton
    @Remote
    @Binds abstract NewsDataSource provideNewsRemoteDataSource(NewsRemoteDataSource newsRemoteDataSource);
}
