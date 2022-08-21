package com.example.tfm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public class Search extends AppCompatActivity {

    private SearchView searchView;
    private EditText editText;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        getSupportActionBar().hide();
        editText = findViewById(R.id.number);
        searchView = findViewById(R.id.searchView);
        radioGroup = findViewById(R.id.type);
        radioGroup1 = findViewById(R.id.sort);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String type = "", sort = "";

                if(radioGroup.getCheckedRadioButtonId() == R.id.newsRadio)
                    type = "1";
                else
                    type = "0";


                if(radioGroup1.getCheckedRadioButtonId() == R.id.relevancyRadio)
                    sort = "relevancy";
                else
                    sort = "recency";

                Context context = getApplicationContext();
                DialogSearching dialogFragment = new DialogSearching();
                dialogFragment.show(getSupportFragmentManager(), "");
                Fetch fetch = new Fetch(context);
                fetch.getTerm(context, s, getWindow(), editText.getText().toString(), sort, type);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
