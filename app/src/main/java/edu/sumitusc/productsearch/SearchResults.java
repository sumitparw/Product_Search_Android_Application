package edu.sumitusc.productsearch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchResults extends AppCompatActivity {

    String jsonResult="";
    private List<SearchItem> lstItem;
    private RecyclerView recyclerView;
    private LinearLayout progress;
    private ProgressBar mProgressBar;
    private Handler mHandler = new Handler();
    private int mProgressStatus = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        RelativeLayout myrel = findViewById(R.id.searchResult);
        final TextView txt = findViewById(R.id.noResultError);
        recyclerView = findViewById(R.id.recyclerView);
        progress = findViewById(R.id.progressLayout);
        mProgressBar = findViewById(R.id.progressBar1);
        progress = findViewById(R.id.progressLayout);

        progress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mProgressStatus<100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(10);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONArray searchResult = new JSONArray(jsonResult);
//                            JSONArray products = searchResult.getJSONArray("name");
                            if(searchResult!=null)
                                //Log.d("debug","hi");
                                recyclerView.setVisibility(View.VISIBLE);
                            if(searchResult==null ||searchResult.length()<1 || searchResult.length()==0)
                                txt.setVisibility(View.VISIBLE);
                        }
                        catch (Exception e)
                        {
                            txt.setVisibility(View.VISIBLE);
                        }

                        progress.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();

        lstItem = new ArrayList<>();

        jsonResult = getIntent().getStringExtra("jsonResult");

        try {

            txt.setVisibility(View.GONE);

            JSONArray searchResult = new JSONArray(jsonResult);
            if(searchResult != null) {
                for (int i = 0; i < searchResult.length(); i++) {
                    SearchItem item = new SearchItem();

                    JSONObject first = searchResult.getJSONObject(i);
                    item.setTitle(first.getString("name"));
                    item.setProductId(first.getString("id"));
                    item.setShortname(first.getString("shortName"));
                    item.setPrice(first.getString("price"));
                    item.setImg(first.getString("image"));
                    item.setZip(first.getString("zip"));
                    item.setCondition(first.getString("condition"));
                    item.setShipping(first.getString("shipping"));
//                    Log.d("shortname", item.getShortname());
//                    Log.d("title", item.getTitle());
//                    Log.d("price", item.getPrice());
//                    Log.d("id", item.getProductId());
//                    Log.d("shipping", item.getShipping());
//                    Log.d("condition", item.getCondition());
//                    Log.d("zip", item.getZip());
//                    Log.d("image", item.getImg());
                    lstItem.add(item);
                }
            }
        }
        catch (JSONException e) {


        }
        setupRecyclerView(lstItem);
    }


    private void setupRecyclerView(List<SearchItem> lstItem){

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstItem);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myAdapter);

    }


}
