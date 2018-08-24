package by.intervale.akella266.rssreader.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import by.intervale.akella266.rssreader.data.News;
import io.reactivex.Single;

@Dao
public interface NewsDao {

    @Insert
    void addNews(List<News> news);

    @Update
    void updateNews(News news);

    @Delete
    void removeNews(News news);

    @Query("select * from news where source = :source")
    Single<List<News>> getAllNews(String source);

    @Query("select * from news")
    Single<List<News>> getAllNews();

    @Query("select * from news where id = :newsId")
    Single<News> getNews(String newsId);

    @Query("delete from news where source = :source")
    void removeNewsFromSource(String source);
}
