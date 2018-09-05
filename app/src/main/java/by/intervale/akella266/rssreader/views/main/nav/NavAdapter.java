package by.intervale.akella266.rssreader.views.main.nav;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.Source;

public class NavAdapter extends RecyclerView.Adapter {

    private List<Source> mSources;
    private OnSourceItemClickListener mListener;

    public NavAdapter(List<Source> mSources, OnSourceItemClickListener mListener) {
        this.mSources = mSources;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_item, parent, false);
        NavViewHolder holder = new NavViewHolder(view);
        view.setOnClickListener(view1 -> mListener.onItemClick(
                mSources.get(holder.getAdapterPosition()),
                holder.getAdapterPosition()));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NavViewHolder navHolder = (NavViewHolder)holder;
        navHolder.mTextViewSource.setText(mSources.get(navHolder.getAdapterPosition()).getName());
    }

    @Override
    public int getItemCount() {
        return mSources.size();
    }

    public void setSources(List<Source> mSources) {
        this.mSources = mSources;
        notifyDataSetChanged();
    }

    class NavViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text_view_source)
        TextView mTextViewSource;

        public NavViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface OnSourceItemClickListener{
        void onItemClick(Source source, int position);
    }
}
