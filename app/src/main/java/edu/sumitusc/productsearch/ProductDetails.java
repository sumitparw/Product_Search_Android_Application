package edu.sumitusc.productsearch;

import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetails extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    String productName ="";
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        String product = getIntent().getStringExtra("product");
//        Log.d("debug",product);
        try {
            JSONObject json = new JSONObject(product);

            productName = json.getString("title");
//            url = json.getString("fburl");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ActionBar ab = getSupportActionBar();
        ab.setTitle(productName);

        TabLayout tabLayout = findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.information_variant);
        tabLayout.getTabAt(0).setText("PRODUCT");
        tabLayout.getTabAt(1).setIcon(R.drawable.truck_delivery);
        tabLayout.getTabAt(1).setText("SHIPPING");
        tabLayout.getTabAt(2).setIcon(R.drawable.google);
        tabLayout.getTabAt(2).setText("PHOTOS");
        tabLayout.getTabAt(3).setIcon(R.drawable.similar);
        tabLayout.getTabAt(3).setText("SIMILAR");
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Product_detailsFragment(),"TAB1");
        adapter.addFragment(new ShippingFragment(),"TAB2");
        adapter.addFragment(new Tab3Fragment(),"TAB3");
        adapter.addFragment(new Tab4Fragment(),"TAB4");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.favorite:

                return true;

//            case R.id.tweet:
//                Uri uri = Uri.parse("https://twitter.com/intent/tweet?text=Check out "+eventName+" located at "+getIntent().getStringExtra("venueName")+". Website: "+url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        getMenuInflater().inflate( R.menu.menu_product_details, menu );
        return true;
    }

}
