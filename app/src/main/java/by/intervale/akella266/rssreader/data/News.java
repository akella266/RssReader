package by.intervale.akella266.rssreader.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import by.intervale.akella266.rssreader.data.local.CategoriesConverter;
import by.intervale.akella266.rssreader.data.local.DateConverter;

@Entity(tableName = "news")
public class News {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String image;
    private String description;
    @TypeConverters(DateConverter.class)
    private Date date;
    private String source;
    private String link;
    @TypeConverters(CategoriesConverter.class)
    private List<String> mCategories;

    public News() {
        id = UUID.randomUUID().toString();
    }

    public News(@NonNull String id, String title, String image, String description, Date date, String source, String link, List<String> mCategories) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.date = date;
        this.source = source;
        this.link = link;
        this.mCategories = mCategories;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getCategories() {
        return mCategories;
    }

    public void setCategories(List<String> mCategories) {
        this.mCategories = mCategories;
    }
}
