package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rkjc.news_app_2.database.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private static final String TAG = "NewsRecyclerViewAdapter";
    private List<NewsItem> newsItems;
    private Context context;

    public NewsRecyclerViewAdapter(Context context, NewsItemViewModel viewModel) {
        this.newsItems = newsItems;
        this.context = context;
    }

    public NewsRecyclerViewAdapter(List<NewsItem> newsItems, Context context) {
        this.newsItems = newsItems;
        this.context = context;
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    @Override
    public NewsRecyclerViewAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);

        return newsViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {

        holder.bind(position);

    }

    public void setNewsItems(List<NewsItem> items){
        this.newsItems = items;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return newsItems.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView description;
        TextView date;
        ImageView imageView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);
            imageView = (ImageView) itemView.findViewById(R.id.news_img);
        }

        void bind(int position){
            // Get NewsItem Object
            NewsItem item = newsItems.get(position);
            title.setText("Title: " + item.getTitle());
            description.setText("Description: " + item.getDescription());
            date.setText("Date: " + item.getPublishedAt());
            String imageUrl = newsItems.get(position).getUrlToImage();
            if(imageUrl != null){
                Picasso.get()
                        .load(imageUrl)
                        .into(imageView);
            }
            // set on click listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPos = getAdapterPosition();
            NewsItem item = newsItems.get(adapterPos);
            String url = item.getUrl();
            Log.i(TAG,  url);
            Uri webUri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webUri);
            context.startActivity(intent);
        }
    } /* NewsViewHolder InnerClass */

}
