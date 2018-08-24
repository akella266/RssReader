package by.intervale.akella266.rssreader.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import by.intervale.akella266.rssreader.data.News;

@Database(entities = {News.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase{

    public abstract NewsDao newsDao();
}
