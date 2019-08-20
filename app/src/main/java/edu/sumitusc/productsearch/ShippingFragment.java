package edu.sumitusc.productsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShippingFragment extends Fragment {

    TextView nameStore,feedbackScoreText,feedbackStarText,popularityText,shippingCostText,globalShippingText,handlingTimeText,conditionText,policyText,returnWithinText,refunModeText,shippedByText;
//    TextView nameText2,followersText2,popularityText2,checkAtText2,headingTitle2;
//
//    ImageView artistImage1,artistImage2,artistImage3,artistImage4,artistImage5,artistImage6,artistImage7,artistImage8;
//    ImageView artistImage12,artistImage22,artistImage32,artistImage42,artistImage52,artistImage62,artistImage72,artistImage82;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);

        nameStore = view.findViewById(R.id.storename);
        feedbackScoreText = view.findViewById(R.id.feedbackscore);
        feedbackStarText = view.findViewById(R.id.feedbackstar);
        popularityText = view.findViewById(R.id.popularity);
        shippingCostText = view.findViewById(R.id.shippingCost);
        globalShippingText = view.findViewById(R.id.globalShipping);
        handlingTimeText = view.findViewById(R.id.handlingTime);
        conditionText = view.findViewById(R.id.condition);
        policyText = view.findViewById(R.id.policy);
        returnWithinText = view.findViewById(R.id.returnWithin);
        refunModeText = view.findViewById(R.id.refundMode);
        shippedByText = view.findViewById(R.id.shippedBy);


        String name = getActivity().getIntent().getStringExtra("product");
//        JSONObject shipping = new JSONObject(first);

        try {
            JSONObject product = new JSONObject(name);
            String storeName= product.getString("storeName");
            String storeURL= product.getString("storeURL");
            Log.d("store",storeURL);
            if(storeName!=null && storeURL!=null){
                LinearLayout lt = view.findViewById(R.id.div_storename);
                lt.setVisibility(View.VISIBLE);
                nameStore.setClickable(true);
                nameStore.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='"+storeURL+"'>"+storeName+"</a>";
                nameStore.setText(Html.fromHtml(text));
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_storename);
                lt.setVisibility(View.VISIBLE);
                nameStore.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_storename);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }

        try {
            JSONObject product = new JSONObject(name);
            JSONObject sellerInfo= product.getJSONObject("seller");
            String feedbackStar=sellerInfo.getString("FeedbackRatingStar");
//            Log.d("store",feedbackScore);
            if(feedbackStar!=null){
                LinearLayout lt = view.findViewById(R.id.div_feedbackstar);
                lt.setVisibility(View.VISIBLE);
                 feedbackStarText.setText(feedbackStar);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_feedbackstar);
                lt.setVisibility(View.VISIBLE);
                feedbackStarText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_feedbackstar);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }
        try {
            JSONObject product = new JSONObject(name);
            JSONObject sellerInfo= product.getJSONObject("seller");
            String feedbackScore=sellerInfo.getString("FeedbackScore");
//            Log.d("store",feedbackScore);
            if(feedbackScore!=null){
                LinearLayout lt = view.findViewById(R.id.div_feedbackscore);
                lt.setVisibility(View.VISIBLE);
                feedbackScoreText.setText(feedbackScore);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_feedbackscore);
                lt.setVisibility(View.VISIBLE);
                feedbackScoreText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_feedbackscore);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }
        try {
            JSONObject product = new JSONObject(name);
            JSONObject sellerInfo= product.getJSONObject("seller");
            String popularity=sellerInfo.getString("PositiveFeedbackPercent");
            Log.d("store",popularity);
            if(popularity!=null){
                LinearLayout lt = view.findViewById(R.id.div_popularity);
                lt.setVisibility(View.VISIBLE);
                popularityText.setText(popularity);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_popularity);
                lt.setVisibility(View.VISIBLE);
                popularityText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_popularity);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }
        try {
            JSONObject product = new JSONObject(name);
            String globalShipping= product.getString("globalShipping");
            if(globalShipping!=null){
                LinearLayout lt = view.findViewById(R.id.div_globalShipping);
                lt.setVisibility(View.VISIBLE);
                globalShippingText.setText(globalShipping);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_globalShipping);
                lt.setVisibility(View.VISIBLE);
                globalShippingText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_globalShipping);
            lt.setVisibility(View.GONE);

        }
        try {
            JSONObject product = new JSONObject(name);
            String handlingTime= product.getString("handlingTime");
            if(handlingTime!=null){
                LinearLayout lt = view.findViewById(R.id.div_handlingTime);
                lt.setVisibility(View.VISIBLE);
                handlingTimeText.setText(handlingTime);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_handlingTime);
                lt.setVisibility(View.VISIBLE);
                handlingTimeText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_handlingTime);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }
        try {
            JSONObject product = new JSONObject(name);
            String condition= product.getString("condition");
            if(condition!=null){
                LinearLayout lt = view.findViewById(R.id.div_condition);
                lt.setVisibility(View.VISIBLE);
                conditionText.setText(condition);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_condition);
                lt.setVisibility(View.VISIBLE);
                conditionText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_condition);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }

        String shippingCost= getActivity().getIntent().getStringExtra("shipping");
        if(shippingCost!=null){
            LinearLayout lt = view.findViewById(R.id.div_shipCost);
            lt.setVisibility(View.VISIBLE);
            Log.d("debug",shippingCost);
            shippingCostText.setText(shippingCost);
        }
        else{
            LinearLayout lt = view.findViewById(R.id.div_shipCost);
            lt.setVisibility(View.GONE);
        }

        try {
            JSONObject product = new JSONObject(name);
            String returnPolicy= product.getString("returnPolicy");
            if(returnPolicy!=null){
                LinearLayout lt = view.findViewById(R.id.div_policy);
                lt.setVisibility(View.VISIBLE);
                policyText.setText(returnPolicy);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_policy);
                lt.setVisibility(View.VISIBLE);
                policyText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_condition);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }
        try {
            JSONObject product = new JSONObject(name);
            String returnWithin= product.getString("returnWithin");
            if(returnWithin!=null){
                LinearLayout lt = view.findViewById(R.id.div_returnWithin);
                lt.setVisibility(View.VISIBLE);
                returnWithinText.setText(returnWithin);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_returnWithin);
                lt.setVisibility(View.VISIBLE);
                returnWithinText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_returnWithin);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }
        try {
            JSONObject product = new JSONObject(name);
            String refund= product.getString("Refund");
            if(refund!=null){
                LinearLayout lt = view.findViewById(R.id.div_refundMode);
                lt.setVisibility(View.VISIBLE);
                refunModeText.setText(refund);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_refundMode);
                lt.setVisibility(View.VISIBLE);
                refunModeText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_policy);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }
        try {
            JSONObject product = new JSONObject(name);
            String shippingBy= product.getString("shippingBy");
            if(shippingBy!=null){
                LinearLayout lt = view.findViewById(R.id.div_shippedBy);
                lt.setVisibility(View.VISIBLE);
                shippedByText.setText(shippingBy);
            }
            else{
                LinearLayout lt = view.findViewById(R.id.div_shippedBy);
                lt.setVisibility(View.VISIBLE);
                shippedByText.setText("N/A");
            }

        } catch (JSONException e) {
            LinearLayout lt = view.findViewById(R.id.div_shippedBy);
            lt.setVisibility(View.GONE);
//            e.printStackTrace();
        }


        return view;
    }
}
