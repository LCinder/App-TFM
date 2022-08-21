package com.example.tfm;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Fetch {

    private JsonObjectRequest jsonTerm;
    private JsonArrayRequest jsonTrends;
    private JsonArrayRequest jsonCounts;
    private RequestQueue queue;
    final private Integer TIMEOUT;

    public Fetch(Context context) {
        queue = Volley.newRequestQueue(context);
        TIMEOUT = 30000;
    }


    public void getTerm(Context context, String term, Window window, String number, String sort, String type) {
        if (window != null)
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        String URL_TERM = context.getResources().getString(R.string.term_ip);
        String url = URL_TERM + "/" + term + "/" +  number + "?order=" + sort + "&links=" + type;

        Intent intent = new Intent(context, MainActivity.class);

        getCounts(context, intent, term);

        this.jsonTerm = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            private Tweets tweets;

            @Override
            public void onResponse(JSONObject response) {
                JSONArray tweetsJson = null;
                this.tweets = new Tweets();

                try {
                    tweetsJson = response.getJSONArray("tweets");

                    for(int i=0; i < tweetsJson.length(); i++) {
                        double fake = 0.0;

                        String title = tweetsJson.getJSONObject(i).getString("title");
                        String body = tweetsJson.getJSONObject(i).getString("body");
                        String text = tweetsJson.getJSONObject(i).getString("text");
                        String interactions = tweetsJson.getJSONObject(i).getString("interactions");
                        String image = tweetsJson.getJSONObject(i).getString("image");
                        String truthfulnessString = tweetsJson.getJSONObject(i).getString("truthfulness");
                        String date = tweetsJson.getJSONObject(i).getString("date");
                        String domain = tweetsJson.getJSONObject(i).getString("domain");

                        fake = Double.valueOf(truthfulnessString);

                        Tweet tweet = new Tweet(title, body, text, interactions, image, fake, date, domain);
                        this.tweets.push(tweet);
                    }

                    intent.putExtra("tweets", this.tweets);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TweetsError", error.getStackTrace().toString());
                if (window != null)
                    window.setFlags(WindowManager.LayoutParams.FLAGS_CHANGED, WindowManager.LayoutParams.FLAGS_CHANGED);
                Toast.makeText(context, "Ha habido un error, prueba de nuevo", Toast.LENGTH_LONG);
            }
        });


        this.jsonTerm.setRetryPolicy(
                new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(this.jsonTerm);
    }


    public void getCounts(Context context, Intent intent, String term) {
        String URL_COUNTS = context.getResources().getString(R.string.counts_ip);
        String url = URL_COUNTS + "/" + term + "/" +  "24";

        this.jsonCounts = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            private ArrayList<String> counts;
            private ArrayList<String> countsName;

            @Override
            public void onResponse(JSONArray response) {
                this.counts = new ArrayList<>();
                this.countsName = new ArrayList<>();

                for(int i=0; i < response.length(); i++) {
                    try {
                        String name = response.getJSONObject(i).getString("hour");
                        Integer tweet_volume = Integer.valueOf(response.getJSONObject(i).getString("tweets"));
                        this.counts.add(String.valueOf(tweet_volume));
                        this.countsName.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                intent.putStringArrayListExtra("counts", this.counts);
                intent.putStringArrayListExtra("countsName", this.countsName);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TweetsError", error.toString());
            }
        });

        this.jsonCounts.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(this.jsonCounts);
    }


    public void getTrends(Context context, Intent intent) {
        String URL_TRENDS = context.getResources().getString(R.string.trends_ip);

        this.jsonTrends = new JsonArrayRequest(URL_TRENDS, new Response.Listener<JSONArray>() {
            private List<String> trends;

            @Override
            public void onResponse(JSONArray response) {
                this.trends = new ArrayList<String>();

                for(int i=0; i < response.length(); i++) {
                    try {
                        String name = response.getJSONObject(i).getString("name");
                        String tweet_volume = response.getJSONObject(i).getString("tweet_volume");
                        this.trends.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                intent.putStringArrayListExtra("trends", (ArrayList<String>) this.trends);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TweetsError", error.toString());
            }
        });

        this.jsonTrends.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(this.jsonTrends);
    }

}
