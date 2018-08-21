package by.intervale.akella266.rssreader.data;

import java.util.Date;
import java.util.List;

public class News {

    private String id;
    private String title;
    private String image;
    private String description;
    private Date date;
    private String source;
    private String link;
    private List<String> mCategories;

    public News() { }

    public News(String id, String title, String image, String description, Date date, String source, String link, List<String> mCategories) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.date = date;
        this.source = source;
        this.link = link;
        this.mCategories = mCategories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<String> getmCategories() {
        return mCategories;
    }

    public void setmCategories(List<String> mCategories) {
        this.mCategories = mCategories;
    }
}
