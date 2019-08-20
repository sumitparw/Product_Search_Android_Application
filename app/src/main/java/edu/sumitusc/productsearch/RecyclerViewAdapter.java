package edu.sumitusc.productsearch;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<SearchItem> mData;
    private RequestQueue mQueue;

    public RecyclerViewAdapter(Context mContext, List<SearchItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.layout_search_item,viewGroup,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        mQueue = Volley.newRequestQueue(mContext);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i  = new Intent(mContext, ProductDetails.class);


                try{
                    String url1 = "http://www.gcpproductsearch.appspot.com/api?id="+mData.get(viewHolder.getAdapterPosition()).getProductId();
                    Log.d("url", url1);
                    JsonObjectRequest req1 = new JsonObjectRequest(Request.Method.GET, url1, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    i.putExtra("product",response.toString());
                                    Log.d("product",response.toString());
//                                    JSONObject first = response.getJSONObject("id");
//                                    JSONObject specific_product = response;
                                i.putExtra("shipping",mData.get(viewHolder.getAdapterPosition()).getShipping());
                                i.putExtra("images","images data");
                                i.putExtra("artistName", "artist name");
                                i.putExtra("artist","artist data");
                                i.putExtra("venue","venue data");
                                i.putExtra("venueName","venue name data");
                                i.putExtra("upcoming","upcoming data");
                                mContext.startActivity(i);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
//


//

                    String url2 = "http://www.gcpproductsearch.appspot.com/api/simprod?key="+mData.get(viewHolder.getAdapterPosition()).getProductId();
                    Log.d("url", url2);
                    JsonArrayRequest req2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("similar",response.toString());
//                                   try{
//                                       JSONObject similar = response.getJSONObject(0);
//                                       String name = similar.getString("name");
//                                       Log.d("similar",name);
//                                   }catch (Exception e){
//                                       e.printStackTrace();
//                                   }


                                    i.putExtra("similar",response.toString());
                                mContext.startActivity(i);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    URI uri2 = new URI(mData.get(viewHolder.getAdapterPosition()).getTitle().replace(" ", "%20"));
                    String url4 = "https://www.gcpproductsearch.appspot.com/api/google-search-img?key="+uri2;
                    Log.d("urllll",url4);
                    JsonArrayRequest req4 = new JsonArrayRequest(Request.Method.GET, url4, null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("imagecustom",response.toString());
                                    i.putExtra("images",response.toString());
                                mContext.startActivity(i);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
//
//
//                    String url5 = "http://csci-hw8-222600.appspot.com/upcoming/"+uri4;
//                    JsonObjectRequest req5 = new JsonObjectRequest(Request.Method.GET, url5, null,
//                            new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    i.putExtra("upcoming",response.toString());
//                                    mContext.startActivity(i);
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            error.printStackTrace();
//                        }
//                    });
//                    //Started making changes...Undo Point!
//
                    mQueue.add(req1);
                    mQueue.add(req2);
//                    if(mData.get(viewHolder.getAdapterPosition()).getArtist2()!="")
//                    {
//                        mQueue.add(req10);
//                        mQueue.add(req11);
//                    }
//
//                    mQueue.add(req3);
                    mQueue.add(req4);
//                    mQueue.add(req5);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


          myViewHolder.title.setText(mData.get(i).getShortname());
          myViewHolder.price.setText(mData.get(i).getPrice());
          myViewHolder.productId.setText(mData.get(i).getProductId());
          myViewHolder.zip.setText(mData.get(i).getZip());
          myViewHolder.shipping.setText(mData.get(i).getShipping());
          myViewHolder.condition.setText(mData.get(i).getCondition());
//          Log.d("print",mData.get(i).getImg());
          Picasso.get().load(mData.get(i).getImg()).into(myViewHolder.image);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView shipping;
        TextView condition;
        TextView price;
        TextView productId;
        TextView zip;
        ImageView image;
        ImageView thumbnail;
        LinearLayout view_container;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            zip=itemView.findViewById(R.id.zip);
            shipping = itemView.findViewById(R.id.shipping);
            image = itemView.findViewById(R.id.image);
            view_container = itemView.findViewById(R.id.itemContainer);
            condition = itemView.findViewById(R.id.condition);
            productId = itemView.findViewById(R.id.productId);
        }
    }
}

