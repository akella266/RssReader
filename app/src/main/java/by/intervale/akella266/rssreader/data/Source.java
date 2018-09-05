package by.intervale.akella266.rssreader.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "sources")
public class Source {

    @PrimaryKey
    @NonNull
    private String id;
    private String url;
    @Ignore
    private String name;

    public Source() {
        this.id = UUID.randomUUID().toString();
    }

    public Source(@NonNull String id, String url) {
        this.id = url.hashCode() + "";
        this.url = url;
    }

    public Source(String url) {
        this(UUID.randomUUID().toString(), url);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        name = url.split("/")[2];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
