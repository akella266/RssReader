package by.intervale.akella266.rssreader.views.main.nav;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.NewsRepository;
import by.intervale.akella266.rssreader.data.Source;
import by.intervale.akella266.rssreader.util.ActivityUtils;
import by.intervale.akella266.rssreader.util.events.DrawerCloseEvent;
import by.intervale.akella266.rssreader.util.events.SourceAddedEvent;
import by.intervale.akella266.rssreader.util.events.SourceChangedEvent;
import by.intervale.akella266.rssreader.util.events.SourceDeleteEvent;
import by.intervale.akella266.rssreader.views.main.MainFragment;
import by.intervale.akella266.rssreader.views.offerNews.OfferFragment;
import dagger.Lazy;

public class NavFragment extends Fragment
        implements NavAdapter.OnSourceItemClickListener {

    private Unbinder unbinder;

    @BindView(R.id.text_view_offer)
    TextView mOffer;
    @BindView(R.id.recycler_drawer)
    RecyclerView mRecycler;
    NavAdapter mAdapter;
    List<Source> mSources;

    @Inject
    Lazy<MainFragment> mMainFragmentProvider;
    @Inject
    Lazy<OfferFragment> mOfferFragmentProvider;
    @Inject
    NewsRepository mRepository;

    @Inject
    public NavFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_bar, container, false);
        unbinder = ButterKnife.bind(this, view);
        mSources = new ArrayList<>();
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NavAdapter(mSources, this);
        mRecycler.setAdapter(mAdapter);
        loadSources();
        loadMainFragment();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onItemClick(Source source, int position) {
        Log.d("Nav", "ItemClick");
        loadMainFragment();
        EventBus.getDefault().postSticky(new SourceChangedEvent(source.getUrl()));
        EventBus.getDefault().post(new DrawerCloseEvent());
    }

    @OnClick(R.id.text_view_offer)
    public void offerClick(){
        EventBus.getDefault().post(new DrawerCloseEvent());
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            Fragment offerFragment
                    = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (offerFragment == null || !(offerFragment instanceof OfferFragment)) {
                offerFragment = mOfferFragmentProvider.get();
                ActivityUtils.replaceFragmentInActivity(activity.getSupportFragmentManager(),
                        offerFragment, R.id.fragment_container);
            }
        }
        else Log.e("NavFragment", "activity null");
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSourceAdded(SourceAddedEvent event){
        loadSources();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSourceDelete(SourceDeleteEvent event){
        if (event.getSource() != null){
            mRepository.deleteSource(event.getSource());
            mAdapter.removeSource(event.getSource());
        }
    }

    private void loadMainFragment(){
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        if (activity != null) {
            Fragment mainFragment
                    = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (mainFragment == null || !(mainFragment instanceof MainFragment)) {
                mainFragment = mMainFragmentProvider.get();
                ActivityUtils.replaceFragmentInActivity(activity.getSupportFragmentManager(),
                        mainFragment, R.id.fragment_container);
            }
        }
    }

    private void loadSources(){
        mRepository.getSources(sources -> mAdapter.setSources(sources));
    }

}
