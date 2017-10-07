package com.network.network671;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageLogo;
    TextView nameText;
    TextView pwdText;
    EditText IDText;
    EditText password;
    ImageButton loginButton;
   // String url="http://127.0.0.1:8080/network/JsonAction?action_flag=";
    //public static final String url = "http://www.baidu.com";
    public URL loginURL;
    ProgressDialog dialog;

    String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageLogo = (ImageView) findViewById(R.id.logoImage);
        nameText = (TextView) findViewById(R.id.nameText);
        pwdText = (TextView) findViewById(R.id.pwdText);
        IDText = (EditText) findViewById(R.id.IDText);
        password = (EditText) findViewById(R.id.password);
        loginButton = (ImageButton) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new ButtonListener());
        dialog = new ProgressDialog(this);
        dialog.setTitle("indication");
        dialog.setMessage("log in, please wait.....");
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = IDText.getText().toString();
            String pwd = password.getText().toString();
            dialog.show();
/*            if (name.equals("network")&&pwd.equals("2015"))
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setMessage("log in succeed").setPositiveButton("YES", new turnButtonListener());
            }*/

            VisitUrlThread visitUrlThread = new VisitUrlThread(name, pwd);
            visitUrlThread.start();

           /* Intent intent = new Intent();
            intent.setClass(MainActivity.this, ListActivity.class);
            startActivity(intent);*/

        }

    }

    class VisitUrlThread extends Thread {
        String url = "";
        BufferedReader reader;

        public VisitUrlThread(String userName, String userPwd) {
            url= "http://10.0.0.145:8080/network/JsonAction?action_flag="+userName+userPwd;

            Log.d("Lichao", "userName>>>>>>" + userName);
            Log.d("Lichao", "userPwd>>>>>>>" + userPwd);

        }

        @Override
        public void run() {

            try {


                loginURL = new URL(url);
                Log.d("Lichao", "mainActivity URL >>>>>>" + loginURL.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) loginURL.openConnection();
                urlConnection.connect();
                int returnCode=urlConnection.getResponseCode();
                if(returnCode==HttpURLConnection.HTTP_OK){
                    Log.d("Lichao", String.valueOf(returnCode));
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    Log.d("Lichao", "Main Activity complete reading >>>>>>>");

                    String line = "";
                    Log.d("Lichao", "result>>>>>>>" + result);
                    while ((line = reader.readLine()) != null) {
                        result = result + line;
                    }

                    Log.d("Lichao", "mainActivity result>>>>>>>" + result);

                    if (result.equals("login succeed"))
                    {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                    else{
                        Log.d("Lichao", "mainActivity result>>>>>>>     login fails");
                        dialog.dismiss();
                    }
                }
                else{
                    Log.d("Lichao", "mainActivity urlconnection failes"+returnCode);
                }


                reader.close();
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*class turnButtonListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,ListActivity.class);
            startActivity(intent);
        }
    }*/
}
