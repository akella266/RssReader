package by.intervale.akella266.rssreader.views.main;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.util.SourceChangedEvent;
import by.intervale.akella266.rssreader.views.offerNews.OfferFragment;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String CURRENT_SOURCE = "CURRENT_SOURCE";

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
    private int mLastSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mLastSelectedItem = 0;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null){
            mLastSelectedItem = (int)savedInstanceState.getSerializable(CURRENT_SOURCE);
            MenuItem menuItem = mNavigationView.getMenu().getItem(mLastSelectedItem);
            mNavigationView.setCheckedItem(menuItem.getItemId());
        }
        else loadMainFragment();

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
            case R.id.nav_tut_by:{
                loadMainFragment();
                EventBus.getDefault().postSticky(new SourceChangedEvent(getString(R.string.source_tutby)));
                mLastSelectedItem = 0;
                break;
            }
            case R.id.nav_onliner_by:{
                loadMainFragment();
                EventBus.getDefault().postSticky(new SourceChangedEvent(getString(R.string.source_onliner)));
                mLastSelectedItem = 1;
                break;
            }
            case R.id.nav_lenta_ru:{
                loadMainFragment();
                EventBus.getDefault().postSticky(new SourceChangedEvent(getString(R.string.source_lenta)));
                mLastSelectedItem = 2;
                break;
            }
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
                mLastSelectedItem = 4;
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
}
