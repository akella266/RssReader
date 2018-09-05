package by.intervale.akella266.rssreader.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.sql.SQLOutput;
import java.util.List;

import by.intervale.akella266.rssreader.data.Source;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface SourceDao {

    @Insert
    void insert(Source source);

    @Insert
    void insert(Source... sources);

    @Delete
    void delete(Source source);

    @Query("select * from sources")
    Single<List<Source>> getAllSources();
}
