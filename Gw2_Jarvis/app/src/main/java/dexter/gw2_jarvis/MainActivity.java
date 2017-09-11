package dexter.gw2_jarvis;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    protected class MyListViewActivity extends Activity{

        ListView mListView = (ListView) findViewById(R.id.map_listview);

        JSONArray mJsonArray = new JSONArray();
        String mlistOfMapName[] = new String[] {};

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mlistOfMapName);

            mListView.setAdapter(adapter);
        }

        protected class DownloadInitialMapData extends AsyncTask<String, Void, JSONArray> {

            @Override
            protected JSONArray doInBackground(String... params) {

                HttpsURLConnection httpsURLConnection = null;

                try {
                    URL url = new URL(params[0]);
                    httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.setReadTimeout(1000); // 1 second
                    httpsURLConnection.setRequestMethod("GET");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(JSONArray jsonArray) {
                super.onPostExecute(jsonArray);

                // Store result to local buffer
                mJsonArray = jsonArray;

                // Grab adapter to add elements into listview
                ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) mListView.getAdapter();

                for (int i = 0; i < jsonArray.length(); i++){
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        arrayAdapter.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
