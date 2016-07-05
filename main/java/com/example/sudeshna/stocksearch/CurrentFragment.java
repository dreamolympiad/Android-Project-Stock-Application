package com.example.sudeshna.stocksearch;



        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

public class CurrentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(ResultActivity.LOG_TAG, "CurrentFragment:onCreateView");
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_current,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(ResultActivity.LOG_TAG, "CurrentFragment:onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        Log.i(ResultActivity.LOG_TAG, "CurrentFragment : query_symbol : " + ResultActivity.query_symbol);
        new GetCurrentStock().execute(ResultActivity.query_symbol, "get_quote");
    }

    class GetCurrentStock extends GetAsync {
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);
            Log.i(ResultActivity.LOG_TAG, "GetCurrentStock : jsonData : " + jsonData);
        }
    }
}
