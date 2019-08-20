package edu.sumitusc.productsearch;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tab3Fragment extends Fragment {

    TextView venueNameText;
    TextView addressText;
    TextView cityText;
    TextView phoneNumberText;
    TextView openHoursText;
    TextView generalRuleText;
    TextView childRuleText;

    ImageView productImage1,productImage2,productImage3,productImage4,productImage5,productImage6,productImage7,productImage8;

    double lat = 34.0266;
    double lon = -118.283;
    String name ="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);

//        venueNameText = view.findViewById(R.id.venueNameText);
//        addressText = view.findViewById(R.id.addressText);
//        cityText = view.findViewById(R.id.cityText);
//        phoneNumberText = view.findViewById(R.id.phoneNumberText);
//        openHoursText = view.findViewById(R.id.openHoursText);
//        generalRuleText = view.findViewById(R.id.generalRuleText);
//        childRuleText = view.findViewById(R.id.childRuleText);

        productImage1 = view.findViewById(R.id.productImage1);
        productImage2 = view.findViewById(R.id.productImage2);
        productImage3 = view.findViewById(R.id.productImage3);
        productImage4 = view.findViewById(R.id.productImage4);
        productImage5 = view.findViewById(R.id.productImage5);
        productImage6 = view.findViewById(R.id.productImage6);
        productImage7 = view.findViewById(R.id.productImage7);
        productImage8 = view.findViewById(R.id.productImage8);




        String photosStr = getActivity().getIntent().getStringExtra("images");
//        Log.d("ppppppppppppp", photosStr.toString());

        try {
//            JSONObject venue = new JSONObject(name);
            JSONArray photos = new JSONArray(photosStr);
            Log.d("array", photos.toString());
            Log.d("ppppppppppppp", Integer.toString(photos.length()));
//            for(int i=0; i<photos.length();i++) {
//                Picasso.get().load(photos.getString(i).toString()).into(img);
//            }

//            Log.d("rrrrrrrrr", photos.getJSONObject(0).getString("link"));
            if(photos.getString(0)!=null){
                Picasso.get().load(photos.getString(0)).into(productImage1);
            }
            if(photos.getString(1)!=null){
                Picasso.get().load(photos.getString(1)).into(productImage2);
            }
            if(photos.getString(2)!=null){
                Picasso.get().load(photos.getString(2)).into(productImage3);
            }
            if(photos.getString(3)!=null){
                Picasso.get().load(photos.getString(3)).into(productImage4);
            }
            if(photos.getString(4)!=null){
                Picasso.get().load(photos.getString(4)).into(productImage5);
            }
            if(photos.getString(5)!=null){
                Picasso.get().load(photos.getString(5)).into(productImage6);
            }
            if(photos.getString(6)!=null){
                Picasso.get().load(photos.getString(6)).into(productImage7);
            }
            if(photos.getString(7)!=null){
                Picasso.get().load(photos.getString(7)).into(productImage8);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }

}