package by.intervale.akella266.rssreader.views.main.nav;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.NewsRepository;
import by.intervale.akella266.rssreader.data.Source;
import by.intervale.akella266.rssreader.util.DrawerCloseEvent;
import by.intervale.akella266.rssreader.util.SourceAddedEvent;
import by.intervale.akella266.rssreader.util.SourceChangedEvent;

public class NavFragment extends Fragment
        implements NavAdapter.OnSourceItemClickListener {

    private Unbinder unbinder;
    @BindView(R.id.recycler_drawer)
    RecyclerView mRecycler;
    NavAdapter mAdapter;
    List<Source> mSources;

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
        EventBus.getDefault().postSticky(new SourceChangedEvent(source.getUrl()));
        EventBus.getDefault().post(new DrawerCloseEvent());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSourceAdded(SourceAddedEvent event){
        loadSources();
    }

    private void loadSources(){
        mRepository.getSources(sources -> mAdapter.setSources(sources));
    }

}
