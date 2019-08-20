package edu.sumitusc.productsearch;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;


public class Tab4Fragment extends Fragment {

    private Spinner categorySpinner;
    private Spinner orderSpinner;
    private TextView titleText,priceText,shippingText,daysleftText;
    private ImageView image1;
    private int x;
    public View view;


    String data[][] = new String[20][6];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab4_fragment, container, false);

        String name1 = getActivity().getIntent().getStringExtra("similar");
//        Log.d("mess", name1);
        titleText = view.findViewById(R.id.title);
        priceText = view.findViewById(R.id.daysLeft);
        shippingText = view.findViewById(R.id.shippingCost);
        daysleftText = view.findViewById(R.id.price);
        image1 = view.findViewById(R.id.image);

        try{
            JSONArray similar = new JSONArray(name1);
            x =similar.length();
            Log.d("length", Integer.toString(x));
            Log.d("length", Integer.toString(data[0].length));
            for(int i=0;i<similar.length();i++){
                if(similar.getJSONObject(i).getString("url")!=null){
                    data[i][1]=similar.getJSONObject(i).getString("url");

                }
                else{
                    data[i][1]="N/A";
                }
                if(similar.getJSONObject(i).getString("name")!=null){
                    data[i][0]="<a href='"+data[i][1]+"'>"+similar.getJSONObject(i).getString("name")+"</a>";

                }
                else{
                    data[i][0]="N/A";
                }
                if(similar.getJSONObject(i).getString("image")!=null){
                    data[i][2]=similar.getJSONObject(i).getString("image");

                }
                else{
                    data[i][2]="N/A";
                }
                if(similar.getJSONObject(i).getString("shipping")!=null){
                    data[i][3]=similar.getJSONObject(i).getString("shipping");

                }
                else{
                    data[i][3]="N/A";
                }
                if(similar.getJSONObject(i).getString("daysLeft")!=null){
                    data[i][4]=similar.getJSONObject(i).getString("daysLeft");

                }
                else{
                    data[i][4]="N/A";
                }
                if(similar.getJSONObject(i).getString("price")!=null){
                    data[i][5]=similar.getJSONObject(i).getString("price");

                }
                else{
                    data[i][5]="N/A";
                }

            }


            Log.d("sim",data[5][0]);
            setView();
        }catch (Exception e){
            e.printStackTrace();
           Log.d("debugffff","hi");
        }
        return view;
    }


    public static void removeUnderlines(Spannable p_Text) {
        URLSpan[] spans = p_Text.getSpans(0, p_Text.length(), URLSpan.class);

        for(URLSpan span:spans) {
            int start = p_Text.getSpanStart(span);
            int end = p_Text.getSpanEnd(span);
            p_Text.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            p_Text.setSpan(span, start, end, 0);
        }
    }



    private void setView() {
        Log.d("mess", Integer.toString(data.length));
        Log.d("mess", data[0].toString());
        Log.d("mess", data[0][0].toString());

        LinearLayout l = (LinearLayout) view.findViewById(R.id.cardLayout);

        for(int i=0;i<x;i++){
            Log.d("messsss  ", ""+i);

//            LinearLayout div = new LinearLayout(this.getActivity());
//            LinearLayout.LayoutParams paramsdiv = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
//            );
//            div.setLayoutParams(paramsdiv);
//            div.setOrientation(LinearLayout.VERTICAL);

            CardView card = new CardView(this.getActivity());
//            card.setLayoutParams(l.getLayoutParams());

            CardView.LayoutParams paramscard = new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT, 140
            );
            paramscard.setMargins(0, 0, 0, 10);
            card.setLayoutParams(paramscard);





            LinearLayout horiLayout = new LinearLayout(this.getActivity());
            LinearLayout.LayoutParams paramsh = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            );
            card.setLayoutParams(paramsh);
            horiLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView cartImage = new ImageView(this.getActivity());
            ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(100, 100);
            cartImage.setLayoutParams(p);


            LinearLayout horiLayout1 = new LinearLayout(this.getActivity());
            horiLayout1.setOrientation(LinearLayout.VERTICAL);
            horiLayout1.setLayoutParams(paramsh);

            TextView titleText = new TextView(this.getActivity());
            titleText.setClickable(true);
            titleText.setMovementMethod(LinkMovementMethod.getInstance());
            titleText.setText(Html.fromHtml(data[i][0]));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    280, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 7, 0, 7);
            titleText.setLayoutParams(params);

            LinearLayout horiLayout2 = new LinearLayout(this.getActivity());
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            horiLayout2.setLayoutParams(params2);

            TextView shippingText = new TextView(this.getActivity());
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params3.setMargins(0, 0, 0, 7);
            shippingText.setLayoutParams(params3);

            TextView daysLeft = new TextView(this.getActivity());
            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params4.setMargins(120, 0, 0, 7);
            shippingText.setLayoutParams(params3);

            TextView priceText = new TextView(this.getActivity());
            LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params5.setMargins(200, 0, 0, 7);
            shippingText.setLayoutParams(params5);
            priceText.setTextColor(Color.blue(0));





            LinearLayout horiLayout3 = new LinearLayout(this.getActivity());


//            div.setOrientation(LinearLayout.HORIZONTAL);
//            horiLayout3.setOrientation(LinearLayout.VERTICAL);
            horiLayout2.setOrientation(LinearLayout.VERTICAL);



            cartImage.setId(i);
            titleText.setId(i);
            priceText.setId(i);
            shippingText.setId(i);
            daysLeft.setId(i);


            Picasso.get().load(data[i][2]).into(cartImage);
            cartImage.setBackground(ContextCompat.getDrawable(this.getActivity(), R.drawable.cart_higlighted));

            removeUnderlines((Spannable)titleText.getText());
            shippingText.setText(data[i][3]);
            daysLeft.setText(data[i][4]);
            priceText.setText(data[i][5]);

            horiLayout2.addView(shippingText);
            horiLayout2.addView(daysLeft);
            horiLayout2.addView(priceText);
            horiLayout1.addView(titleText);
            horiLayout1.addView(horiLayout2);
            horiLayout.addView(cartImage);

//            div.addView(horiLayout3);


//            div.addView(horiLayout);
//            div.addView(horiLayout2);
            horiLayout.addView(horiLayout1);


            card.addView(horiLayout);
            l.addView(card);
//            l.addView(div);
        }



    }
}

