package com.network.network671;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    //visit all company list URL
    public static final String companyURL = "http://10.0.0.145:8080/network/JsonAction?action_flag=companys";
    //visit one certain company image
    public static final String companyIMG = "http://10.0.0.145:8080/network/";

    private ListView listView;
    private ProgressDialog dialog;
    private MyAdapter adapter;

    TextView coName;
    TextView coLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listView);
        listView.setDividerHeight(20);
        listView.setOnItemClickListener(new OnItemClickListener());
        adapter = new MyAdapter(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle("indication");
        dialog.setMessage("loading, please wait.....");
        new MyTask().execute(companyURL);
    }

    public class OnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(ListActivity.this, InfoActivity.class);
           // String name= (String) coName.getText();
            //String companyName=name.substring(0,name.length()-4);
            Map<String, Object> objectMap=(Map<String, Object>)adapter.getItem(position);
            Object name=(String)objectMap.get("coName");
            Object imgPath=(String)objectMap.get("webImageAddress");
            //TextView name= (TextView) view.findViewById(R.id.compyName);
            Log.d("linlin","name>>>>>>>"+name);
            intent.putExtra("name", String.valueOf(name));
            intent.putExtra("imgPath",String.valueOf(imgPath));
            startActivity(intent);
        }
    }

    public class MyTask extends AsyncTask<String, Void, List<Map<String, Object>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> result) {
            super.onPostExecute(result);
            adapter.setData(result);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        }

        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {
            URL companyurl = null;
            URL imageurl = null;
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            //connect network, obtain data and parse data
            //use json to parse data
            try {
                companyurl = new URL(companyURL);
                Log.d("Lichao", "ListActivity  >>>>" + companyurl.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) companyurl.openConnection();
                urlConnection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                Log.d("Lichao", "ListActivity complete reading");

                String jsonString = "";
                String line = "";
                Log.d("Lichao", "result>>>>>>>" + jsonString);
                while ((line = reader.readLine()) != null) {
                    jsonString = jsonString + line;
                }
                Log.d("Lichao", jsonString);

                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("companys");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Map<String, Object> map = new HashMap<String, Object>();
                    Iterator<String> iterator = jsonObject1.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        Object value = jsonObject1.get(key);
                        map.put(key, value);
                    }
                    list.add(map);
                }

                reader.close();
                urlConnection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }
    }

    public class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String, Object>> list = null;

        public MyAdapter(Context context) {
            this.context = context;
            layoutInflater = layoutInflater.from(context);
        }

        public void setData(List<Map<String, Object>> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = null;
            if (convertView == null)
            {
                view = layoutInflater.inflate(R.layout.custom_listcell, null);
            }
            else
            {
                view = convertView;
            }

            coName = (TextView) view.findViewById(R.id.iconName);
            coLoc = (TextView) view.findViewById(R.id.iconLoc);

            coName.setText(list.get(position).get("coName").toString());
            coLoc.setText(list.get(position).get("location").toString());
            ////downloading image
            final ImageView coIcon = (ImageView) view.findViewById(R.id.companyLogo);
            DownLoadImage downloadImage = new DownLoadImage(companyIMG + list.get(position).get("webImageAddress").toString());
            downloadImage.loadImage(new DownLoadImage.ImageCallBack()
            {
                @Override
                public void getDrawable(Drawable drawable)
                {
                    coIcon.setImageDrawable(drawable);
                }
            });
            return view;
        }
    }


}
