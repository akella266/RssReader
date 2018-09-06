package by.intervale.akella266.rssreader.views.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.NewsRepository;
import by.intervale.akella266.rssreader.data.Source;
import by.intervale.akella266.rssreader.data.callbacks.SourceSavedCallback;
import by.intervale.akella266.rssreader.util.ActivityUtils;
import by.intervale.akella266.rssreader.util.events.DrawerCloseEvent;
import by.intervale.akella266.rssreader.util.events.SourceAddedEvent;
import by.intervale.akella266.rssreader.views.main.nav.NavFragment;
import by.intervale.akella266.rssreader.views.offerNews.OfferFragment;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity{

    private final String SHARED_FIRST_LAUNCH = "first_launch";


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @Inject
    Lazy<NavFragment> mNavFragmentProvider;
    @Inject
    NewsRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        checkOnFirstLaunch();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setOnClickListener((view) -> mDrawer.closeDrawers());

        Fragment fragment =
                getSupportFragmentManager().findFragmentById(R.id.fragment_nav_container);
        if(fragment == null){
            fragment = mNavFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.fragment_nav_container);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private void checkOnFirstLaunch(){
        SharedPreferences sp = getSharedPreferences("SharedFirst", MODE_PRIVATE);
        if (sp.getBoolean(SHARED_FIRST_LAUNCH, true)) {
            SharedPreferences.Editor ed = sp.edit();
            ed.putBoolean(SHARED_FIRST_LAUNCH, false);
            ed.apply();
            mRepository.addSources(new SourceSavedCallback() {
                @Override
                public void onSaved() {
                    EventBus.getDefault().postSticky(new SourceAddedEvent());
                }

                @Override
                public void onError() {

                }
            },
                    new Source(getString(R.string.source_tutby)),
                    new Source(getString(R.string.source_onliner)),
                    new Source(getString(R.string.source_lenta)));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void closeDrawer(DrawerCloseEvent event){
        mDrawer.closeDrawers();
    }
}
