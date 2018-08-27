package by.intervale.akella266.rssreader.data.api.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "media:thumbnail", strict = false)
public class Thumbnail {

    @Attribute(name = "url")
    private String url;

    public Thumbnail() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
