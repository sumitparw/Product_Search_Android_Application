package edu.sumitusc.productsearch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.AppCompatAutoCompleteTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import android.widget.AdapterView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SearchProductFragment extends Fragment {
    private Button searchButton, clearButton;
    private EditText keywordText, distanceText;
    private TextView otherLocationText;
    private Spinner categorySpinner;
    private RadioGroup fromGroup;
    private CheckBox shipping1,shipping2,check1,check2,check3;
    private RequestQueue mQueue;
    private TextView error1, error2;
    private TextView testText;
    private RadioButton radioHere, radioOther;
    private CheckBox enable;
    FusedLocationProviderClient fusedLocationProviderClient;
    AutoSuggestAdapter autoSuggestAdapter;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    boolean checks1=false,checks2=false,checks3=false,shippings1=false,shippings2=false;
    Handler handler;
    int myZip=90007;
    double mylatitude = 34.0294;
    double mylongitude = -118.2871;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;

    String url1;
    String distance;
    URI keyword;
    String segmentId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_page_layout, container, false);

        searchButton = view.findViewById(R.id.searchBtn);
        clearButton = view.findViewById(R.id.clearBtn);
        categorySpinner = view.findViewById(R.id.category);
        keywordText = view.findViewById(R.id.keywords);
        distanceText = view.findViewById(R.id.distance);
        otherLocationText = view.findViewById(R.id.otherLocation);
        fromGroup = view.findViewById(R.id.fromRadioGroup);
        check1=view.findViewById(R.id.check1);
        check2=view.findViewById(R.id.check2);
        check3=view.findViewById(R.id.check3);
        shipping1=view.findViewById(R.id.shipping1);
        shipping2=view.findViewById(R.id.shipping2);
        radioHere = view.findViewById(R.id.radioHere);
        radioOther = view.findViewById(R.id.radioOther);
        enable = view.findViewById(R.id.enable_check);
        error1 = view.findViewById(R.id.error1);
        error2 = view.findViewById(R.id.error2);

        testText = view.findViewById(R.id.testText);

        mQueue = Volley.newRequestQueue(getActivity());

        final AppCompatAutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.auto_complete_edit_text);
        final TextView selectedText = view.findViewById(R.id.otherLocation);

        autoSuggestAdapter = new AutoSuggestAdapter(this.getActivity(), android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        selectedText.setText(autoSuggestAdapter.getObject(position));
                    }
                });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, android.R.layout.simple_spinner_item);
//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.units, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);
//        unitSpinner.setAdapter(adapter2);


        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        } else {
            buildLocationRequest();
            buildLocationCallback();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        check1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isValid())
                            if(check1.isChecked()){
                                checks1=true;
                            }
                    }
                }
        );
        check2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isValid())
                            if(check2.isChecked()){
                                checks2=true;
                            }
                    }
                }
        );
        check3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isValid())
                            if(check3.isChecked()){
                                checks3=true;
                            }
                    }
                }
        );
        shipping1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isValid())
                            if(shipping1.isChecked()){
                                shippings1=true;
                            }
                    }
                }
        );
        shipping2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isValid())
                            if(shipping2.isChecked()){
                                shippings2=true;
                            }
                    }
                }
        );
//        checks1 =check1.isChecked();
//        checks2=check2.isChecked();
//        checks3=check3.isChecked();
//        shippings1=shipping1 .isChecked();
//        shippings2=shipping2.isChecked();


        searchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isValid())
                            jsonParse();
                    }
                }
        );

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                keywordText.setText(null);
                otherLocationText.setText(null);
                otherLocationText.setEnabled(false);
                radioHere.setChecked(true);
                categorySpinner.setSelection(0);

                distanceText.setText(null);
                error2.setVisibility(View.GONE);
                error1.setVisibility(View.GONE);
            }
        });

        radioOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherLocationText.setEnabled(true);
            }
        });

        radioHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherLocationText.setEnabled(false);
                error2.setVisibility(View.GONE);
                otherLocationText.setText(null);
            }
        });

        return view;
    }

    private void makeApiCall(String text) {
        ApiCall.make(this.getActivity(), text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parsing logic, please change it as per your requirement
                List<String> stringList = new ArrayList<>();
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray array = responseObject.getJSONArray("postalCodes");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        stringList.add(row.getString("postalCode"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private boolean isValid() {
        if (keywordText.getText().toString().replaceAll(" ", "") == "") {
            error1.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Please fix all fields with errors", Toast.LENGTH_SHORT).show();
            if (radioOther.isChecked()) {
                if (otherLocationText.getText().toString().replaceAll(" ", "") == "") {
                    error2.setVisibility(View.VISIBLE);
                    return false;
                }
            }
            return false;
        } else if (keywordText.getText().toString().replaceAll(" ", "") != "") {
            error1.setVisibility(View.GONE);
        }
        if (radioOther.isChecked()) {
            if (otherLocationText.getText().toString().replaceAll(" ", "") == "") {
                error2.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Please fix all fields with errors", Toast.LENGTH_SHORT).show();
                return false;
            } else if (otherLocationText.getText().toString().replaceAll(" ", "") != "") {
                error2.setVisibility(View.GONE);
            }
        }
        return true;
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    mylatitude = location.getLatitude();
                    mylongitude = location.getLongitude();
                }
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(6000);
        locationRequest.setFastestInterval(4000);
        locationRequest.setSmallestDisplacement(10);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    }
                }
            }
        }
    }

    private void jsonParse() {
        try {

            String category = categorySpinner.getSelectedItem().toString();
            if (category == "")
                segmentId = "";
            else if (category == "Art")
                segmentId = "550";
            else if (category == "Baby")
                segmentId = "2984";
            else if (category == "Books")
                segmentId = "267";
            else if (category == "Clothing, Shoes and Accessories")
                segmentId = " 11450";
            else if (category == "Computers/Tablets and Networking")
                segmentId = " 58058";
            else if (category == "Health and Beauty")
                segmentId = " 26395";
            else if (category == "Music")
                segmentId = " 11233";
            else if (category == "Video Games and Consoles")
                segmentId = "1249";


            if (distanceText.getText().toString().isEmpty())
                distance = "10";
            else
                distance = distanceText.getText().toString();

            keyword = new URI(keywordText.getText().toString().replace(" ", "%20"));

            if (radioOther.isChecked()) {
                URI loc = new URI(otherLocationText.getText().toString().replace(" ", "%20"));
                String url2 = "http://csci-hw8-222600.appspot.com/location/" + loc;
                JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mylatitude = Double.parseDouble(response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                            mylongitude = Double.parseDouble(response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));
//                            testText.append("MYLAT:"+mylatitude+"MYLONG:"+mylongitude);
                            url1 = "http://csci-hw8-222600.appspot.com/search/" + keyword + "/" + distance + "/" + "/" + mylatitude + "/" + mylongitude + "?segmentId=" + segmentId;
                            search();
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            testText.append("error");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                mQueue.add(request2);
            } else {
                search();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void search() {

        if (url1 == null)
            url1 = "http://www.gcpproductsearch.appspot.com/api/product?keyword="+keywordText.getText()+"&zip="+myZip+"&distance="+distance+"&category="+segmentId+"&check1="+checks1+"&check2="+checks2+"&check3="+checks3+"&shipping1="+shippings1+"&shipping2="+shippings2;
//            Log.d("myTag", "This is my message");
//            Log.d("myTag", url1);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url1, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Log.d("myTag", "This is my message");
//                Log.d("myTag", response.toString());

//                try {
//                    JSONObject first = response.getJSONObject(0);
////                    JSONArray temp = response.getJSONArray("findItemsAdvancedResponse");
////                    JSONArray temp2 = temp.getJSONObject(0).getJSONArray("searchResult");
////                    JSONArray items = temp2.getJSONObject(0).getJSONArray("item");
//                    Log.d("myTag", first.toString());
//                    String seller = first.getString("seller");
//                    Log.d("myTag", seller.toString());
//                } catch(Exception e) {
//                    Log.d("myTag", "errrrrr   " + e);
//                }

                Intent toResults = new Intent(getActivity(), SearchResults.class);
                toResults.putExtra("jsonResult", response.toString());
                startActivity(toResults);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }


}
