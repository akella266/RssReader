package by.intervale.akella266.rssreader.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.data.Source;

@Database(entities = {News.class, Source.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase{

    public abstract NewsDao newsDao();
    public abstract SourceDao sourceDao();
}
