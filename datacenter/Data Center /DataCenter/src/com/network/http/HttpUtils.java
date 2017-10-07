package com.network.http;

import java.io.*;
import java.net.*;

public class HttpUtils {
	
	private static String URL_PATH_HEAD="http://10.0.0.145:8080/network/";
	private static String URL;
	private static String URL_PATH = "http://10.0.0.145:8080/network/netbeans.png";
	//private static String URL_PATH = "http://10.0.0.145:8080/network/3.png";
	public HttpUtils() {
		
	}
	
	public static InputStream getInputStream() {
		InputStream inputstream = null;
		HttpURLConnection httpURLConnection = null;

			try {
				URL url = new URL(URL_PATH);
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setDoInput(true);
				httpURLConnection.setRequestMethod("GET");
				int repsonseCode =  httpURLConnection.getResponseCode();
				if(repsonseCode==200){
					inputstream = httpURLConnection.getInputStream();
					
				}
			}catch (MalformedURLException e){
				e.printStackTrace();	
			}catch (IOException e) {
				e.printStackTrace();
			}
			return inputstream;
	} 
	public static void main(String[] args){
		InputStream inputStream = getInputStream();
	}
}
