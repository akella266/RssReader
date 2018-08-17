package by.intervale.akella266.rssreader.views.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.rssreader.R;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @Inject
    Lazy<MainFragment> mMainFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        MainFragment mainFragment
                = (MainFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (mainFragment == null){
            mainFragment = mMainFragmentProvider.get();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mainFragment).commit();
        }
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
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_tut_by:{
                Snackbar.make(mDrawer,R.string.nav_item_tut_by, Snackbar.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_onliner_by:{
                Snackbar.make(mDrawer,R.string.nav_item_onliner, Snackbar.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_lenta_ru:{
                Snackbar.make(mDrawer,R.string.nav_item_lenta, Snackbar.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_offer_news:{
                Snackbar.make(mDrawer,R.string.nav_item_offer_news, Snackbar.LENGTH_SHORT).show();
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
}
