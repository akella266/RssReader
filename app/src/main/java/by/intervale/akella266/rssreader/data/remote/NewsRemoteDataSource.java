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

import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.data.NewsDataSource;

@Singleton
public class NewsRemoteDataSource implements NewsDataSource {

    @Inject
    NewsRemoteDataSource(){}

    @SuppressLint("CheckResult")
    @Override
    public void getNews(@NonNull String source, @NonNull final LoadNewsCallback callback) {
        Parser parser = new Parser();
        parser.execute(source);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                callback.onNewsLoaded(convertArticleToNews(source.split("/")[2], list));
            }

            @Override
            public void onError() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getNews(@NonNull String source, @NonNull String id, @NonNull GetNewsCallback callback) {

    }

    @Override
    public void saveNews(@NonNull String source, News news) {

    }

    @Override
    public void refreshNews() {

    }

    @Override
    public void clearNews() {

    }

    private List<News> convertArticleToNews(String source, ArrayList<Article> articles){
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
                    source,
                    article.getLink(),
                    article.getCategories()
            ));
        }
        return news;
    }
}
