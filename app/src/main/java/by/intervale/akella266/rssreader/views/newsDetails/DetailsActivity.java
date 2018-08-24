package by.intervale.akella266.rssreader.views.newsDetails;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.rssreader.R;
import dagger.android.support.DaggerAppCompatActivity;

public class DetailsActivity extends DaggerAppCompatActivity {

    public static final String EXTRA_NEWS_ID = "NEWS_ID";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    DetailsFragment injectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DetailsFragment fragment = (DetailsFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = injectedFragment;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
