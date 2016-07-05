package com.example.sudeshna.stocksearch;

import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sindhu Mukunda on 4/21/2016.
 */
public class TabFragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_tab1, container, false);
        TextView tv = (TextView) v.findViewById(R.id.StockName1);
        TextView stocksymbol = (TextView) v.findViewById(R.id.StockSymbol);
        TextView stockslastprice = (TextView) v.findViewById(R.id.StockLastPrice);
        TextView cPer = (TextView) v.findViewById(R.id.ChangePercent);
        TextView tStamp = (TextView) v.findViewById(R.id.TimeStamp);
        TextView market = (TextView) v.findViewById(R.id.Market);
        TextView vol = (TextView) v.findViewById(R.id.Volume);
        TextView cytd = (TextView) v.findViewById(R.id.ChangeYTD);
        TextView high = (TextView) v.findViewById(R.id.High);
        TextView low = (TextView) v.findViewById(R.id.Low);
        TextView open = (TextView) v.findViewById(R.id.Open);
        try {
            tv.setText(SMainActivity.json.getString("Name"));
            stocksymbol.setText(SMainActivity.json.getString("Symbol"));
            stockslastprice.setText("$"+SMainActivity.json.getString("LastPrice"));

             if(Double.parseDouble(SMainActivity.json.getString("ChangePercent"))>0)
             {
                 cPer.setText(String.format("%.2f",Double.parseDouble(SMainActivity.json.getString("Change")))+"(+"+String.format("%.2f",Double.parseDouble(SMainActivity.json.getString("ChangePercent")))+"%)");
cPer.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.up),null);
             }
            else
             {
                 cPer.setText(String.format("%.2f",Double.parseDouble(SMainActivity.json.getString("Change")))+"("+String.format("%.2f",Double.parseDouble(SMainActivity.json.getString("ChangePercent")))+"%)");
                 cPer.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.down),null);

             }
            // cPer.setText(SMainActivity.json.getString("ChangePercent"));
            tStamp.setText(convertTime(SMainActivity.json.getString("Timestamp")));

            if(Double.parseDouble(SMainActivity.json.getString("MarketCap"))<1000000)
            {
                market.setText(SMainActivity.json.getString("MarketCap"));
            }
            else
            {
                if(Double.parseDouble(SMainActivity.json.getString("MarketCap"))<1000000000)
                {
                   market.setText (String.format("%.2f",(Double.parseDouble((SMainActivity.json.getString("MarketCap")))/1000000))+"Million");
                }
                else
                {
                    market.setText (String.format("%.2f",(Double.parseDouble((SMainActivity.json.getString("MarketCap")))/1000000000))+"Billion");
                }
            }

          //  market.setText(SMainActivity.json.getString("MarketCap"));
            vol.setText(SMainActivity.json.getString("Volume"));

            if(Double.parseDouble(SMainActivity.json.getString("ChangePercentYTD"))>0)
            {
                cytd.setText(String.format("%.2f",Double.parseDouble(SMainActivity.json.getString("ChangeYTD")))+"(+"+String.format("%.2f",Double.parseDouble(SMainActivity.json.getString("ChangePercentYTD")))+"%)");
                cytd.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.up),null);
            }
            else
            {
                cytd.setText(String.format("%.2f",Double.parseDouble(SMainActivity.json.getString("ChangeYTD")))+"("+String.format("%.2f",Double.parseDouble(SMainActivity.json.getString("ChangePercentYTD")))+"%)");
                cytd.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.down),null);
            }
         //   cytd.setText(SMainActivity.json.getString("ChangePercentYTD"));
            high.setText(SMainActivity.json.getString("High"));
            low.setText(SMainActivity.json.getString("Low"));
            open.setText(SMainActivity.json.getString("Open"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }

    public static String convertTime(String dt)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("E MMM d k:m:s Z");
        String op ="";
        try
        {
            Date date  = sdf.parse(dt);
            SimpleDateFormat d = new SimpleDateFormat("dd MMM yyyy, kk:mm:ss");
            op=d.format(date);
        }
        catch(ParseException e)
        {
            op=dt;
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return op;
    }

}