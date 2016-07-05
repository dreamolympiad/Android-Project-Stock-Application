package com.example.sudeshna.stocksearch;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.view.MenuInflater;
import android.view.Menu;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * Created by Sindhu Mukunda on 4/17/2016.
 */
public class SMainActivity2 extends AppCompatActivity {
    Button button;
    private FragmentTabHost mTabHost;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabHost tabhost;
    public Menu menu;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    ShareDialog shareDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smain2);
        SharedPreferences settings = getSharedPreferences(SMainActivity.PREFS_NAME, 0);
        String symbs = settings.getString("symbs", null);
        SMainActivity.valid = new LinkedList<String>();
        if (symbs != null) {
            SMainActivity.multistring=symbs.split(",");
          //  SMainActivity.multistring = symbs.split(",");
            SMainActivity.valid = new LinkedList<String>(Arrays.asList(SMainActivity.multistring));
        }
        FacebookSdk.sdkInitialize(getApplicationContext());

        CallbackManager callbackmanager= CallbackManager.Factory.create();

        shareDialog = new ShareDialog(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        try {
            setTitle(SMainActivity.json.getString("Name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Button fb= (Button)findViewById(R.id.fbnew);
      //  fb.setOnClickListener(new View.OnClickListener() {

          //  public void onClick(View v) {
               // String name=SMainActivity.json.getString("Symbol");
             //   String url = null;

                     /*ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();
                ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
                shareButton.setShareContent(content);*/
               /* try{
                    String fb = SMainActivity.json.getString("Symbol").toString();
               *//* URI uri = Uri.parse("http://chart.finance.yahoo.com/t?s=aapl&lang=en-US&width=400&height=300");*//*
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("Current Stock Price of "+SMainActivity.json.getString("Name").toString()+","+SMainActivity.json.getString("LastPrice").toString())
                                .setContentDescription("Stock Information of "+SMainActivity.json.getString("Name").toString())
                                .setImageUrl(Uri.parse("http://chart.finance.yahoo.com/t?s="+fb+"&lang=en-US&width=200&height=150"))
                                .build();
                        shareDialog.show(linkContent);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }*/



            /*    if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Current Stock Price of ")
                            .setContentDescription( "Stock Information of ")
                            .setImageUrl(Uri.parse("http://chart.finance.yahoo.com/t?s=AAPL&lang=en-US&width=200&height=150"))
                            .build();
                    shareDialog.show(linkContent);
                }*/

          /*  }
        });*/
        /*
        Creating Adapter and setting that adapter to the viewPager
        setSupportActionBar method takes the toolbar and sets it as
        the default action bar thus making the toolbar work like a normal
        action bar.
         */
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        //setSupportActionBar(toolbar);

        /*
        TabLayout.newTab() method creates a tab view, Now a Tab view is not the view
        which is below the tabs, its the tab itself.
         */
        if (tabLayout != null) {
            final TabLayout.Tab home = tabLayout.newTab();
            final TabLayout.Tab inbox = tabLayout.newTab();
            final TabLayout.Tab star = tabLayout.newTab();

        /*
        Setting Title text for our tabs respectively
         */

            home.setText("CURRENT");
            inbox.setText("HISTORICAL");
            star.setText("NEWS");

        /*
        Adding the tab view to our tablayout at appropriate positions
        As I want home at first position I am passing home and 0 as argument to
        the tablayout and like wise for other tabs as well
         */
            tabLayout.addTab(home, 0);
            tabLayout.addTab(inbox, 1);
            tabLayout.addTab(star, 2);

        /*
        TabTextColor sets the color for the title of the tabs, passing a ColorStateList here makes
        tab change colors in different situations such as selected, active, inactive etc

        TabIndicatorColor sets the color for the indiactor below the tabs
         */

            //tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
            //tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));

        /*
        Adding a onPageChangeListener to the viewPager
        1st we add the PageChangeListener and pass a TabLayoutPageChangeListener so that Tabs Selection
        changes when a viewpager page changes.
         */


            //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(onTabSelectedListener(viewPager));
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        this.menu=menu;
        try {
            if(checkSymbol(SMainActivity.json.getString("Symbol")))
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.star));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.facebook_click:
                String url = null;

                     /*ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();
                ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
                shareButton.setShareContent(content);*/
                try{
                    String fb = SMainActivity.json.getString("Symbol").toString();
               /* URI uri = Uri.parse("http://chart.finance.yahoo.com/t?s=aapl&lang=en-US&width=400&height=300");*/
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("Current Stock Price of "+SMainActivity.json.getString("Name").toString()+","+SMainActivity.json.getString("LastPrice").toString())
                                .setContentDescription("Stock Information of "+SMainActivity.json.getString("Name").toString())
                                .setImageUrl(Uri.parse("http://chart.finance.yahoo.com/t?s="+fb+"&lang=en-US&width=200&height=150"))
                                .build();
                        shareDialog.show(linkContent);
                    }
                    return true;
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    return true;
                }
                case R.id.action_search:

                try {
                    if (checkSymbol(SMainActivity.json.getString("Symbol"))) {
                        SMainActivity.valid.remove(SMainActivity.json.getString("Symbol"));
                        SharedPreferences settings = getSharedPreferences(SMainActivity.PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        String result = android.text.TextUtils.join(",", SMainActivity.valid);
                        editor.putString("symbs", result);
                        editor.commit();
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.empty));

                    } else {
                        SMainActivity.valid.add(SMainActivity.json.getString("Symbol"));
                        SharedPreferences settings = getSharedPreferences(SMainActivity.PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        String result = android.text.TextUtils.join(",", SMainActivity.valid);
                        editor.putString("symbs", result);
                        editor.commit();
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.star));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean checkSymbol(String str) {
        if (SMainActivity.valid.contains(str))
        {
            return true;
        }
        else
            return false;
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SMainActivity2 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.sudeshna.stocksearch/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SMainActivity2 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.sudeshna.stocksearch/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
