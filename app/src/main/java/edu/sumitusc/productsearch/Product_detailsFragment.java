package edu.sumitusc.productsearch;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class Product_detailsFragment extends Fragment {


    TextView titleText;
    TextView priceText;
    TextView price1Text;
    TextView shippingText;
    TextView brandText;
    TextView item_specifics1Text;
    TextView item_specifics2Text;
    TextView  subtitleText;
    ImageView image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);


//        image = view.findViewById(R.id.image);
        titleText = view.findViewById(R.id.title);
        priceText = view.findViewById(R.id.price);
        price1Text = view.findViewById(R.id.price1);
        shippingText = view.findViewById(R.id.shipping);
        brandText = view.findViewById(R.id.brand);
//        item_specifics2Text = view.findViewById(R.id.item_specifics2);
        subtitleText= view.findViewById(R.id.subtitle);
        String name = getActivity().getIntent().getStringExtra("product");
        try {
            JSONObject product = new JSONObject(name);
                JSONArray imgArr = product.getJSONArray("product_images");
                String Images= (product.getJSONArray("product_images")).getString(0);
           Log.d("image", Images);
//                Picasso.get().load(Images).into(image);

            titleText.setText((product.getString("title")));
//            Log.d("title", (product.getString("title")));
            Log.d("price",(product.getString("price")));
            price1Text.setText((product.getString("price")));
            priceText.setText((product.getString("price")));
//            shippingText.setText((product.getString("shipping")));

            LinearLayout mGallery;

            mGallery = (LinearLayout) view.findViewById(R.id.id_gallery);


            ArrayList<TextView> itemSpecificViews = new ArrayList<TextView>();
            String brand = "";
            JSONArray itemSpecifics = product.getJSONArray("itemspecifics");
            LinearLayout lLayout = (LinearLayout) view.findViewById(R.id.t1l6);

            for(int i=0;i<itemSpecifics.length();i++){

                String name1=itemSpecifics.getJSONObject(i).getJSONArray("Value").getString(0);
                Log.d("mess", "hereeeeeeeeee");

                String items="• "+ Character.toUpperCase(name1.charAt(0)) + name1.substring(1, name1.length());

                TextView tv = new TextView(view.getContext()); // Prepare textview object programmatically
                tv.setText(items);
                tv.setId(i);
                tv.setTextSize(18);

                if(itemSpecifics.getJSONObject(i).getString("Name").equals("Brand")) {
                    itemSpecificViews.add(0, tv);
                    brand = itemSpecifics.getJSONObject(i).getJSONArray("Value").getString(0);
                }

                else
                    itemSpecificViews.add(tv);
            }

            for(int i=0; i<itemSpecificViews.size(); i++)
                lLayout.addView(itemSpecificViews.get(i));

            for (int i = 0; i < imgArr.length(); i++)
            {

                View imageView = inflater.inflate(R.layout.activity_gallery_item,
                        mGallery, false);
                ImageView img = (ImageView) imageView
                        .findViewById(R.id.id_index_gallery_item_image);
                Log.d("mess", imgArr.get(i).toString()+ "   oooooooooo");
                Picasso.get().load(imgArr.get(i).toString()).into(img);


//                img.setImageResource(mImgIds[i]);
//                TextView txt = (TextView) imageView
//                        .findViewById(R.id.id_index_gallery_item_text);
//                txt.setText("info "+i);
                mGallery.addView(imageView);
            }

            try {
                subtitleText.setText((product.getString("Subtitle")));
                LinearLayout lt = view.findViewById(R.id.div_subtitle);
                lt.setVisibility(View.VISIBLE);
            }
            catch (Exception e)
            {
                LinearLayout lt = view.findViewById(R.id.div_subtitle);
                lt.setVisibility(View.GONE);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}

