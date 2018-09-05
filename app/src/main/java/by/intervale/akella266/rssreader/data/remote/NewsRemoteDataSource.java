package by.intervale.akella266.rssreader.data.remote;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.data.NewsDataSource;
import by.intervale.akella266.rssreader.data.Source;
import by.intervale.akella266.rssreader.data.api.ApiService;
import by.intervale.akella266.rssreader.data.api.models.Channel;
import by.intervale.akella266.rssreader.data.api.models.Item;
import by.intervale.akella266.rssreader.data.callbacks.GetNewsCallback;
import by.intervale.akella266.rssreader.data.callbacks.LoadNewsCallback;
import by.intervale.akella266.rssreader.data.callbacks.LoadSourcesCallback;
import by.intervale.akella266.rssreader.data.callbacks.OnClearingCompleteCallback;
import by.intervale.akella266.rssreader.data.callbacks.SourceSavedCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class NewsRemoteDataSource implements NewsDataSource {

    private ApiService mApiService;

    @Inject
    public NewsRemoteDataSource(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getNews(@NonNull String source, @NonNull final LoadNewsCallback callback) {
        mApiService.getRss(source)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rss -> {
                            List<Item> items = rss.getChannel().getItems();
                            callback.onNewsLoaded(convertItemToNews(source.split("/")[2], items));
                        },
                        throwable -> {
                            callback.onDataNotAvailable();
                        });
    }

    @Override
    public void getNews(@NonNull String id, @NonNull GetNewsCallback callback) {

    }

    @Override
    public void saveNews(List<News> news) {

    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void clearNews(@NonNull String source,@NonNull OnClearingCompleteCallback callback) {

    }

    @Override
    public void getSources(@NonNull LoadSourcesCallback callback) {

    }

    @Override
    public void addSource(@NonNull Source source, @NonNull SourceSavedCallback callback) {

    }

    @Override
    public void deleteSource(@NonNull Source source) {

    }

    @Override
    public void addSources(@NonNull SourceSavedCallback callback, @NonNull Source... sources) {

    }

    private List<News> convertItemToNews(String source, List<Item> items){
        List<News> news = new ArrayList<>();
        for(Item item : items){
            String descr = Html.fromHtml(item.getDescription()).toString();
            descr = descr.replaceAll("\n", "").replaceAll("￼", "");
            String image;
            if (item.getEnclosure() != null) image = item.getEnclosure().getUrl();
            else if (item.getThumbnail() != null) image = item.getThumbnail().getUrl();
            else image = getImageFromDescr(descr);
            news.add(new News(
                    UUID.randomUUID().toString(),
                    item.getTitle(),
                    image,
                    descr,
                    new Date(item.getPubDate()),
                    source,
                    item.getLink(),
                    parseCategories(item.getCategory())
            ));
        }
        return news;
    }

    private String getImageFromDescr(String descr){
        String url = null;
        Pattern patternImg = Pattern.compile("(<img .*?>)");
        Matcher matcherImg = patternImg.matcher(descr);
        if (matcherImg.find()) {
            String imgTag = matcherImg.group(1);
            Pattern patternLink = Pattern.compile("src\\s*=\\s*\"(.+?)\"");
            Matcher matcherLink = patternLink.matcher(imgTag);
            if (matcherLink.find()) {
                url = matcherLink.group(1);
            }
        }
        return url;
    }
    private List<String> parseCategories(String category){
        List<String> cats = new ArrayList<>();
        cats.add(category);
        return cats;
    }
}
