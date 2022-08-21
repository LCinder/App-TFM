package com.example.tfm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private Tweets tweets;
    private Context context;

    public TweetsAdapter(Tweets tweets, Context context) {

        this.tweets = tweets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        boolean fake = (tweet.getFake() < 0.5) ? false : true;

        String imageTweetDefault = "https://about.twitter.com/content/dam/about-twitter/en/brand-toolkit/brand-download-img-1.jpg.twimg.1920.jpg";

        if (tweet.getTitle() == "")
            holder.title.setText("Tweet");
        else
            holder.title.setText(tweet.getTitle());

        holder.text.setText(tweet.getText());
        holder.interactions.setText("Interacciones: " + tweet.getInteractions());

        if (tweet.getImage() == "")
            Picasso.with(holder.image.getContext()).load(imageTweetDefault).into(holder.image);
        else
            Picasso.with(holder.image.getContext()).load(tweet.getImage()).into(holder.image);

        if (!fake) {
            holder.banner.setCardBackgroundColor(ContextCompat.getColor(holder.banner.getContext(), R.color.real));
            holder.classification.setText("Real (" + String.format("%.2f", (1.0 - tweet.getFake()) * 100.0) + ")%");
        }
        else {
            holder.banner.setCardBackgroundColor(ContextCompat.getColor(holder.banner.getContext(), R.color.fake));
            holder.classification.setText("Fake (" + String.format("%.2f", tweet.getFake()*100.0) + ")%");
        }

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tweet.getBody().isEmpty()) {
                    ViewCompat.setTransitionName(holder.text, "text");
                    ActivityOptionsCompat activityOptionsCompat
                            = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                            holder.text, "text");

                    Intent article = new Intent(view.getContext(), Article.class);
                    article.putExtra("tweet", tweet);
                    holder.text.getContext().startActivity(article, activityOptionsCompat.toBundle());
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView text;
        public TextView interactions;
        public CardView banner;
        public TextView classification;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTweet);
            text = itemView.findViewById(R.id.textTweet);
            interactions = itemView.findViewById(R.id.interactionsTweet);
            banner = itemView.findViewById(R.id.banner);
            classification = itemView.findViewById(R.id.classificationTweet);
            image = itemView.findViewById(R.id.image);
        }
    }
}