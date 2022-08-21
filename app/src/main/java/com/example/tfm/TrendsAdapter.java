package com.example.tfm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.ViewHolder> {

    private List<String> trends;
    private FragmentManager fragmentManager;

    public TrendsAdapter(List<String> trends, FragmentManager fragmentManager) {
        this.trends = trends;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_article, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return trends.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String trend = trends.get(position);

        holder.trend.setText(trend);

        holder.trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSearching dialogFragment = new DialogSearching();
                dialogFragment.show(fragmentManager, "");
                Fetch fetch = new Fetch(holder.trend.getContext());
                fetch.getTerm(holder.trend.getContext(), trend, null, String.valueOf(20), "relevancy", "1");
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView trend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trend = itemView.findViewById(R.id.trend);
        }
    }
}