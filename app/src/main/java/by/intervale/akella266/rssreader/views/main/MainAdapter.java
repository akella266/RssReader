package by.intervale.akella266.rssreader.views.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.intervale.akella266.rssreader.R;
import by.intervale.akella266.rssreader.data.News;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context mContext;
    private List<News> mNews;
    private OnNewsItemClickListener mListener;

    public MainAdapter(Context mContext, OnNewsItemClickListener listener) {
        this.mContext = mContext;
        this.mNews = new ArrayList<>();
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        MainViewHolder holder = new MainViewHolder(view);
        view.setOnClickListener(view1 -> mListener.onItemClick(mNews.get(holder.getAdapterPosition())));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        News news = mNews.get(position);
        holder.mTitle.setText(news.getTitle());
        holder.mDescription.setText(news.getDescription());
        holder.mDate.setText(
                new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(news.getDate())
        );
        holder.mSource.setText(news.getSource());

        if (news.getImage() != null) {
            if (holder.mImageView.getVisibility() == View.GONE)
                holder.mImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(news.getImage())
                    .into(holder.mImageView);
        }
        else{
            holder.mImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public void setNews(List<News> mNews) {
        this.mNews = mNews;
        notifyDataSetChanged();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image_view)
        ImageView mImageView;
        @BindView(R.id.text_view_title)
        TextView mTitle;
        @BindView(R.id.text_view_description)
        TextView mDescription;
        @BindView(R.id.text_view_date)
        TextView mDate;
        @BindView(R.id.text_view_source)
        TextView mSource;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
