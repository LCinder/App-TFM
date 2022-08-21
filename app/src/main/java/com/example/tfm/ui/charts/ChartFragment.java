package com.example.tfm.ui.charts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfm.R;
import com.example.tfm.Tweets;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {

    private Tweets tweets;
    private PieChart pieChart;
    private LineChart lineChart;


    private void addLineData(List<Integer> data) {
        Intent intent = getActivity().getIntent();
        List<String> countsNew = intent.getStringArrayListExtra("counts");

        if(countsNew != null) {
            for(String s: countsNew)
                data.add(Integer.valueOf(s));
        }

    }

    private List<String> getDescription() {
        Intent intent = getActivity().getIntent();
        List<String> countsName = intent.getStringArrayListExtra("countsName");
        return countsName;
    }

    private void addPieData(List<PieEntry> pieData, Integer realData) {
        int total = (this.tweets != null) ? this.tweets.size() : 0;
        double real = ((double)realData / (double)total)*100.0;
        double fake = ((double)(total - realData) / (double)total)*100.0;

        pieData.add(new PieEntry((float) real, getResources().getString(R.string.real)));
        pieData.add(new PieEntry((float) fake, getResources().getString(R.string.fake)));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.charts, container, false);

        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        lineChart = (LineChart) view.findViewById(R.id.barChart);

        Intent intent = getActivity().getIntent();
        this.tweets = (Tweets) intent.getSerializableExtra("tweets");

        TextView pieTitle = (TextView) view.findViewById(R.id.textPieChart);
        TextView barTitle = (TextView) view.findViewById(R.id.textBarChart);

        if (this.tweets != null) {
            pieTitle.setText(getResources().getString(R.string.proportion));
            barTitle.setText(getResources().getString(R.string.evolution));

            pieChartProperties(view);
            lineChartProperties(view);

            pieChart.setVisibility(View.VISIBLE);
            lineChart.setVisibility(View.VISIBLE);
        } else {
            barTitle.setText(getResources().getString(R.string.charts_null));
            pieTitle.setPadding(0, 700, 0, 0);
            pieTitle.setText("");

            pieChart.setVisibility(View.GONE);
            lineChart.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void pieChartProperties(View view) {
        Integer real = 0;
        int total_size = (this.tweets != null) ? this.tweets.size() : 0;
        boolean fake;

        for(int i=0; i < total_size; i++) {
            fake = (this.tweets.get(i).getFake() < 0.5) ? false : true;

            if (!fake)
                real++;
        }

        List<PieEntry> pieData = setPieEntries(real);

        PieDataSet pieDataSet = new PieDataSet(pieData, "");
        pieChart.setData(new PieData(pieDataSet));
        pieChart.setUsePercentValues(true);
        Description description = new Description();
        description.setEnabled(false);
        pieChart.setDescription(description);

        pieDataSet.setColors(Color.parseColor(getResources().getString(R.color.primary_variant)),
        Color.parseColor(getResources().getString(R.color.primary_light)));

        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleRadius(0);
        pieChart.setEntryLabelTextSize(20);
        pieDataSet.setValueTextSize(20);
        pieDataSet.setValueTextColor(R.color.black);

        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }

    @SuppressLint("ResourceType")
    public void lineChartProperties(View view) {
        List<Entry> entries = setLineEntries();
        List<String> descriptions = getDescription();

        LineDataSet lineDataSet = new LineDataSet(entries, getResources().getString(R.string.label));
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        //lineChart.getXAxis().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(45);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(descriptions));

        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);

        lineDataSet.setDrawFilled(true);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setFillColor(Color.parseColor(getResources().getString(R.color.primary)));
        lineDataSet.setCircleColor(Color.parseColor(getResources().getString(R.color.primary_variant)));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColors(Color.parseColor(getResources().getString(R.color.primary_variant)));
    }

    public List<Entry> setLineEntries() {
        List<Integer> data = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();

        addLineData(data);

        for(Integer i=0; i < data.size(); i++)
            entries.add(new Entry(i, data.get(i).floatValue()));

        return entries;
    }

    public List<PieEntry> setPieEntries(Integer realData) {
        List<PieEntry> pieData = new ArrayList<>();

        addPieData(pieData, realData);

        return pieData;
    }
}