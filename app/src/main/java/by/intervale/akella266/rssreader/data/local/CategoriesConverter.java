package by.intervale.akella266.rssreader.data.local;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.util.StringUtil;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CategoriesConverter {

    @TypeConverter
    public static List<String> toCategories(String categs) {
        return Arrays.asList(categs.split(";"));
    }

    @TypeConverter
    public static String toString(List<String> categs) {
        return TextUtils.join(";", categs);
    }
}
