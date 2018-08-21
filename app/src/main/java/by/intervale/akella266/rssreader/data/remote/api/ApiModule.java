package by.intervale.akella266.rssreader.data.remote.api;

import javax.inject.Singleton;

import by.intervale.akella266.rssreader.BuildConfig;
import dagger.Module;
import dagger.Provides;
import me.toptas.rssconverter.RssConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    ApiService providesApiService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(RssConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }
}
