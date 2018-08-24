package by.intervale.akella266.rssreader.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import by.intervale.akella266.rssreader.data.News;
import by.intervale.akella266.rssreader.views.main.NewsFilterType;

public class CategoriesUtil {

    public static List<String> getCategories(Context context, NewsFilterType type){
        String filename = "";
        switch (type){
            case IN_WORLD:{
                filename = "in_world.json";
                break;
            }
            case SOCIETY:{
                filename = "society.json";
                break;
            }
            case REALITY:{
                filename = "reality.json";
                break;
            }
            case AUTO:{
                filename = "auto.json";
                break;
            }
            case TECH:{
                filename = "tech.json";
                break;
            }
            case FINANCE:{
                filename = "finance.json";
                break;
            }
            case SPORT:{
                filename = "sport.json";
                break;
            }
        }

        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(filename);
            Reader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            String[] categs = gson.fromJson(reader, String[].class);
            return Arrays.asList(categs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void loadCategories(List<News> news){
        HashSet<String> cats = new HashSet<>();
        for(News n : news){
            cats.addAll(n.getCategories());
        }
        String json = new Gson().toJson(cats);
        Log.i("Cats", json);
    }
}
