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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.NewsRepository;
import by.intervale.akella266.rssreader.data.Source;
import by.intervale.akella266.rssreader.data.callbacks.SourceSavedCallback;
import by.intervale.akella266.rssreader.di.FragmentScoped;
import by.intervale.akella266.rssreader.util.DrawerCloseEvent;
import by.intervale.akella266.rssreader.util.SourceAddedEvent;
import by.intervale.akella266.rssreader.util.SourceChangedEvent;
import by.intervale.akella266.rssreader.views.main.nav.NavAdapter;
import by.intervale.akella266.rssreader.views.main.nav.NavFragment;
import by.intervale.akella266.rssreader.views.offerNews.OfferFragment;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String CURRENT_SOURCE = "CURRENT_SOURCE";
    private final String SHARED_FIRST_LAUNCH = "first_launch";


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @Inject
    Lazy<MainFragment> mMainFragmentProvider;
    @Inject
    Lazy<OfferFragment> mOfferFragmentProvider;
    @Inject
    Lazy<NavFragment> mNavFragmentProvider;
    @Inject
    NewsRepository mRepository;

    private int mLastSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        checkOnFirstLaunch();
        mLastSelectedItem = 0;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setOnClickListener((view) -> mDrawer.closeDrawers());

        Fragment fragment =
                getSupportFragmentManager().findFragmentById(R.id.fragment_nav_container);
        if(fragment == null){
            fragment = mNavFragmentProvider.get();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_nav_container, fragment, "NAV").commit();
        }

        if (savedInstanceState != null){
            mLastSelectedItem = (int)savedInstanceState.getSerializable(CURRENT_SOURCE);
            MenuItem menuItem = mNavigationView.getMenu().getItem(mLastSelectedItem);
            mNavigationView.setCheckedItem(menuItem.getItemId());
        }
        else loadMainFragment();
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_SOURCE, mLastSelectedItem);
        super.onSaveInstanceState(outState);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_offer_news:{
                mLastSelectedItem = 3;
                Fragment offerFragment
                        = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (offerFragment == null || !(offerFragment instanceof OfferFragment)) {
                    offerFragment = mOfferFragmentProvider.get();
                    loadFragment(offerFragment);
                }
                break;
            }
            case R.id.nav_settings:{
                Snackbar.make(mDrawer,R.string.nav_item_settings, Snackbar.LENGTH_SHORT).show();
                break;
            }
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadMainFragment(){
        Fragment mainFragment
                = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (mainFragment == null || !(mainFragment instanceof MainFragment)){
            mainFragment = mMainFragmentProvider.get();
            loadFragment(mainFragment);
        }
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void checkOnFirstLaunch(){
        SharedPreferences sp = getSharedPreferences("SharedFirst", MODE_PRIVATE);
        if (sp.getBoolean(SHARED_FIRST_LAUNCH, true)) {
            SharedPreferences.Editor ed = sp.edit();
            ed.putBoolean(SHARED_FIRST_LAUNCH, false);
            ed.commit();
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
