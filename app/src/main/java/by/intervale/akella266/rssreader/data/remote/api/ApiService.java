package by.intervale.akella266.rssreader.data.remote.api;

import io.reactivex.Observable;
import me.toptas.rssconverter.RssFeed;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Observable<RssFeed> getNews(@Url String url);
}
