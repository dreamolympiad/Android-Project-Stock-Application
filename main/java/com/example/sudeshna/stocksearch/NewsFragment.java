package com.example.sudeshna.stocksearch;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_news, container, false);
        //return LayoutInflater.from(getContext()).inflate(R.layout.fragment_news,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //Log.d(ResultActivity.LOG_TAG, "NewsFragment:onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        //try {
          //  Log.i(ResultActivity.LOG_TAG, "NewsFragment : query_symbol : " + SMainActivity.json.getString("Symbol"));
        //} catch (JSONException e) {
          //  e.printStackTrace();
        //}
        try {
            new GetNews().execute(SMainActivity.json.getString("Symbol"), "get_bingNews");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class GetNews extends GetAsync {
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);
            Log.i(ResultActivity.LOG_TAG, "GetNews : jsonData : " + jsonData);
            List<News> newsList = new ArrayList<News>();
            if (jsonData != null) {
                try {
                    JSONObject mainObj = new JSONObject(jsonData);
                    JSONArray jsonArray =
                            (mainObj.getJSONObject("d")).getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        Log.i("newsInfo : ", jsonObj.getString("Url")
                                + "  " + jsonObj.getString("Title") + " " +
                                jsonObj.getString("Description") + " "  + jsonObj.getString("Source")
                                + " "  + jsonObj.getString("Date"));
                        News newsItem = new
                                News(jsonObj.getString("Url"), jsonObj.getString("Title"),
                                jsonObj.getString("Description"),
                                jsonObj.getString("Source"),jsonObj.getString("Date")  );
                        newsList.add(newsItem);
                    }
                    ListView listView = (ListView) getActivity().findViewById(R.id.newsListView);
                    NewsListAdapter newsListAdapter = new NewsListAdapter(getActivity(), R.layout.news_item, newsList);
                    listView.setAdapter(newsListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
