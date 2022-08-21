package com.example.tfm.ui.trends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfm.DialogSearching;
import com.example.tfm.Fetch;
import com.example.tfm.R;
import com.example.tfm.TrendsAdapter;
import com.example.tfm.Tweets;

import java.util.ArrayList;
import java.util.List;


public class TrendsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<String> trends;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.trends_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTrends);
        recyclerView.setHasFixedSize(true);

        this.trends = new ArrayList<String>();

        initializeTrends();

        TrendsAdapter trendsAdapter = new TrendsAdapter(trends, getFragmentManager());
        recyclerView.setAdapter(trendsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void initializeTrends() {
        Intent intent = getActivity().getIntent();
        List<String> trendsNew = intent.getStringArrayListExtra("trends");
        DialogSearching dialogFragment = new DialogSearching();
        dialogFragment.show(getFragmentManager(), "");

        if(trendsNew != null) {
            this.trends = trendsNew;
            dialogFragment.dismiss();
        }

    }
}