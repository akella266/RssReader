package by.intervale.akella266.rssreader.data.api;

import by.intervale.akella266.rssreader.data.api.models.Channel;
import by.intervale.akella266.rssreader.data.api.models.Rss;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Observable<Rss> getRss(@Url String url);
}
