package by.intervale.akella266.rssreader.data.api;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    ApiService providesApiService(){
        return new Retrofit.Builder()
                .baseUrl("http://google.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
//                        new Persister(new AnnotationStrategy())))
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build().create(ApiService.class);
    }
}
