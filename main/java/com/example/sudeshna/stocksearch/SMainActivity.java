
package com.example.sudeshna.stocksearch;

        import android.app.Activity;
        import android.app.ActionBar;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.NavUtils;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.MenuInflater;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.R.*;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.w3c.dom.Text;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.lang.reflect.Array;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.LinkedList;
        import java.util.List;


public class SMainActivity extends AppCompatActivity {

    public Button button1;
    //  public ImageView yahoo;
    public Button clr;
    public Bitmap bit;
    public static String a;
    public static JSONObject json;
    public Toolbar toolbar;
    public static final String SYMBOL="SYMBOL";
    public static final String LOG_TAG="MainActivity";
    public static List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
    public static List valid;
    public static String PREFS_NAME="sindhu";
    public static String[] multistring;
    private ArrayList<String> data;
    private TextView tdata;
    AutoCompleteTextView auto;
    AlertDialog.Builder alertDialog;
    String [] appendUrl= new String[3];
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smain);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.snippet);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        SharedPreferences settings = getSharedPreferences(SMainActivity.PREFS_NAME, 0);
        String symbs = settings.getString("symbs", null);
        data = new ArrayList<String>();
        ListView lv = (ListView) findViewById(R.id.listView);
        if (symbs.length()>0) {
            SMainActivity.multistring = symbs.split(",");
            data = new ArrayList<String>(Arrays.asList(SMainActivity.multistring));


            lv.setAdapter(new MyListAdaper(this, R.layout.favourite, data));
        }
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(SMainActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
                }
            });

        setTitle("Stock Market Viewer");
        context = getBaseContext();
        auto = (AutoCompleteTextView)findViewById(R.id.autoComplete);
        auto.setThreshold(3);
        auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = auto.getText().toString();
                if (text == "") return;
                String appendUrl = "http://webstock-env.us-west-2.elasticbeanstalk.com/?input=" + text;
                new lookupTask().execute(appendUrl);
            }
        });

        auto = (AutoCompleteTextView)findViewById(R.id.autoComplete);
        auto.setThreshold(3);
        auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = auto.getText().toString();
                if (text == "") return;
                String appendUrl = "http://webstock-env.us-west-2.elasticbeanstalk.com/?input=" + text;

                new lookupTask().execute(appendUrl);
            }
        });
        auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                String[] separated = selection.split(" ");
                //EditText txtbox1=(EditText) findViewById(R.id.editText);

                auto.setText(separated[0]);
            }
        });
        tdata = (TextView)findViewById(R.id.textView);
        button1 = (Button) findViewById(R.id.button2);
        assert button1 != null;


        clr = (Button) findViewById(R.id.button);
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=auto.getText().toString();
                auto.setText("");
            }
        });



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // EditText txtbox1=(EditText) findViewById(R.id.autoComplete);
              //  a = txtbox1.getText().toString();
                String a = auto.getText().toString();
                if(a.isEmpty())
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SMainActivity.this);
                    AlertDialog showit=alertDialog.create();
                    showit.setMessage("Please enter a Stock Name/Symbol");
                    showit.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        }
                    });

                    showit.show();
                }
                else {


                    new AsyncTaskParseJson().execute();
                }


            }
        });


    }

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();

                viewHolder.symbol = (TextView) convertView.findViewById(R.id.symbol);
                viewHolder.name = (TextView) convertView.findViewById(R.id.companyname);
                viewHolder.lastprice = (TextView) convertView.findViewById(R.id.stockprice);
                viewHolder.changeper=(TextView) convertView.findViewById(R.id.ChangePercentage);
                viewHolder.market=(TextView) convertView.findViewById(R.id.marketCap);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            JsonParse jParse=new JsonParse();
            URL url=null;
            try {
                url=new URL("http://sindhu2-1273.appspot.com/?stock="+getItem(position));
                JSONObject symbolData=jParse.getJSONFromUrl(url);
                String StockName=symbolData.getString("Name");

                mainViewholder.name.setText(StockName);
                String lastPrice=symbolData.getString("LastPrice");
                mainViewholder.lastprice.setText("$ "+lastPrice);
                Float marketCap=Float.valueOf(symbolData.getString("MarketCap"));

                if(marketCap>1000000000)
                {

                    mainViewholder.market.setText("Market Cap:"+Math.round(marketCap/1000000000*100)/100+" Billion");
                }
                else if(marketCap>1000000)
                {

                    mainViewholder.market.setText("Market Cap:"+Math.round(marketCap/1000000*100)/100+" Million");
                }
                else
                {
                    mainViewholder.market.setText("Market Cap:"+marketCap+"");
                }
               Float pc= Float.valueOf(symbolData.getString("PercentChange"));
                if(pc>0) {
                    mainViewholder.changeper.setText("+"+Math.round(Float.valueOf(symbolData.getString("PercentChange")) * 100) + "%");
                    mainViewholder.changeper.setBackgroundColor(Color.GREEN);
                }
                else
                {
                    mainViewholder.changeper.setText(Math.round(Float.valueOf(symbolData.getString("PercentChange")) * 100) + "%");
                    mainViewholder.changeper.setBackgroundColor(Color.RED);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mainViewholder.symbol.setText(getItem(position));
            return convertView;
        }
    }
    public class ViewHolder {
        TextView symbol;
        TextView name;
        TextView lastprice;
        TextView changeper;
        TextView market;
    }


    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        final String TAG = "AsyncTaskParseJson.java";
        String a=auto.getText().toString();
        JSONArray dataJsonArr = null;
        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... arg0) {

            try {

                Context context = getApplicationContext();

                JsonParse jParser = new JsonParse();
                URL url=new URL("http://sindhu2-1273.appspot.com/?stock="+a);

                json = jParser.getJSONFromUrl(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {

            if(json.has("Message"))
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SMainActivity.this);
                AlertDialog showit=alertDialog.create();

                showit.setMessage("Invalid Symbol");
                showit.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                });

                showit.show();
            }
            else{
                Intent intent = new Intent(SMainActivity.this, SMainActivity2.class);
                startActivity(intent);

            }


        }
    }

    public class lookupTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String []params) {
            HttpURLConnection connection=null;
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);

                connection = (HttpURLConnection)url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                String line="";

                while((line=reader.readLine())!=null)
                {
                    buffer.append(line);

                }
                String finalJson=buffer.toString();

                return finalJson;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(connection!=null){
                    connection.disconnect();
                }

                try {
                    if(reader!=null)
                    {reader.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }



            return "hi";
        }

        @Override
        protected void onPostExecute(String result) {
            if(result==null)return;
            super.onPostExecute(result);
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(jsonArray==null)return;
            String[] strArr = new String[jsonArray.length()];
            String[] autoString = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    strArr[i] = jsonArray.getString(i);
                    autoString = strArr[i].split("-");
                    strArr[i]=autoString[0]+"\n"+autoString[1].trim();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.select_dialog_item, strArr);

            auto.setAdapter(new ArrayAdapter<String>(context,
                    android.R.layout.select_dialog_item, strArr));

        }
    }



}