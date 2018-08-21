package by.intervale.akella266.rssreader.data.remote;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.Html;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.intervale.akella266.rssreader.BuildConfig;
import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.data.NewsDataSource;
import by.intervale.akella266.rssreader.data.remote.api.ApiService;

@Singleton
public class NewsRemoteDataSource implements NewsDataSource {

    private ApiService mApiService;

    @Inject
    NewsRemoteDataSource(ApiService apiService){
        this.mApiService = apiService;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getNews(@NonNull final LoadNewsCallback callback) {
        Parser parser = new Parser();
        parser.execute(BuildConfig.API_URL);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                callback.onNewsLoaded(convertArticleToNews(list));
            }

            @Override
            public void onError() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getNews(@NonNull String id, @NonNull GetNewsCallback callback) {

    }

    @Override
    public void saveNews(News news) {

    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void clearNews() {

    }

    private List<News> convertArticleToNews(ArrayList<Article> articles){
        List<News> news = new ArrayList<>();
        for(Article article : articles){
            String descr = Html.fromHtml(article.getDescription()).toString();
            descr = descr.substring(1).replaceAll("\n", "");
            news.add(new News(
                    UUID.randomUUID().toString(),
                    article.getTitle(),
                    article.getImage(),
                    descr,
                    article.getPubDate(),
                    "onliner.by",
                    article.getLink(),
                    article.getCategories()
            ));
        }
        return news;
    }
}
