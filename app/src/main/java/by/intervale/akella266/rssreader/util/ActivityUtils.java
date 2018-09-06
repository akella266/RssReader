package by.intervale.akella266.rssreader.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fm,
                                             @NonNull Fragment fragment, int containerId){
        fm.beginTransaction().add(containerId, fragment).commit();
    }

    public static void replaceFragmentInActivity(@NonNull FragmentManager fm,
                                                 @NonNull Fragment fragment, int containerId){
        fm.beginTransaction().replace(containerId, fragment).commit();
    }
}
