package com.network.network671;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class InfoActivity extends AppCompatActivity {

    ImageView compyLogo;
    TextView compyName;
    TextView compyIDText;
    TextView compyLocationText;
    TextView compyDepartmentText;
    TextView summaryText;

    public static String itemURL;
    public static String  imgURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        compyLogo= (ImageView) findViewById(R.id.compyLogo);
        compyName= (TextView) findViewById(R.id.compyName);
        compyIDText= (TextView) findViewById(R.id.compyIDText);
        compyLocationText= (TextView) findViewById(R.id.compyLocationText);
        compyDepartmentText= (TextView) findViewById(R.id.DepartmentText);
        summaryText= (TextView) findViewById(R.id.summaryText);

        Intent intent=getIntent();
        String companyName=intent.getStringExtra("name");
        String companyImgPath=intent.getStringExtra("imgPath");
        itemURL="http://10.0.0.145:8080/network/JsonAction?action_flag="+companyName;
        Log.d("linlin",itemURL);
        imgURL="http://10.0.0.145:8080/network/"+companyImgPath;
        Log.d("linlin",imgURL);

        DownLoadImage downloadImage=new DownLoadImage(imgURL);
        downloadImage.loadImage(new DownLoadImage.ImageCallBack() {
            @Override
            public void getDrawable(Drawable drawable) {
                compyLogo.setImageDrawable(drawable);
            }
        });

        new ItemTask().execute(itemURL);


    }

    public class ItemTask extends AsyncTask <String, Void, Company>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Company doInBackground(String... params) {
            URL companyurl;
            //URL companyImgUrl;
            Company company=new Company();

            try {
                companyurl=new URL(itemURL);
                Log.d("Lichao", "InfoActivity  >>>>" + companyurl.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) companyurl.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                Log.d("Lichao", "InfoActivity complete reading");

                String jsonString = "";
                String line = "";
                Log.d("Lichao", "result>>>>>>>" + jsonString);
                while ((line = reader.readLine()) != null) {
                    jsonString = jsonString + line;
                }
                Log.d("Lichao", "infoActivity jsonString>>>>>>>>"+jsonString);

                JSONObject jsonObject=new JSONObject(jsonString);
                JSONObject companyObject=jsonObject.getJSONObject("company");
                company.setId(companyObject.getString("coId"));
                company.setName(companyObject.getString("coName"));
                company.setLocation(companyObject.getString("location"));
                company.setDepartment(companyObject.getString("department"));
                company.setImagePath(companyObject.getString("webImageAddress"));
                company.setSummary(companyObject.getString("summary"));


            } catch (Exception e) {
                e.printStackTrace();
            }


            return company;
        }

        @Override
        protected void onPostExecute(Company company) {
            super.onPostExecute(company);

            compyName.setText(company.getName());
            compyIDText.setText(company.getId());
            compyLocationText.setText(company.getLocation());
            compyDepartmentText.setText(company.getDepartment());
            summaryText.setText(company.getSummary());





        }
    }




}
