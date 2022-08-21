package com.example.tfm.ui.home;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfm.Fetch;
import com.example.tfm.R;
import com.example.tfm.Search;
import com.example.tfm.Tweet;
import com.example.tfm.Tweets;
import com.example.tfm.TweetsAdapter;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private Tweets tweets;

    public View onCreateView(@NonNull LayoutInflater inflater,
    ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recyclerview, container, false);
        setHasOptionsMenu(true);

        ImageView logo = view.findViewById(R.id.logo);
        Picasso.with(logo.getContext()).load(R.drawable.logo_twitter).into(logo);
        logo.setVisibility(View.VISIBLE);

        this.tweets = new Tweets();

        Intent intent = getActivity().getIntent();
        Tweets tweetsNews = (Tweets) intent.getSerializableExtra("tweets");

        if(tweetsNews != null) {
            this.tweets = tweetsNews;
            logo.setVisibility(View.GONE);
        }

        Context context = getActivity().getApplicationContext();
        Fetch fetch = new Fetch(context);
        fetch.getTrends(context, intent);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        TweetsAdapter tweetsAdapter = new TweetsAdapter(tweets, getActivity());
        recyclerView.setAdapter(tweetsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.searchBar);

        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.searchBar) {
            startActivity(new Intent(getContext(), Search.class));
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}