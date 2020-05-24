package com.freetravelapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

public class PHPServiceHandler {

	static String response = null;
	public final static String GET = "GET";
	public final static String POST = "POST";

	public PHPServiceHandler() {
	}

	/**
	 * Making service call
	 * 
	 * @url - url to make request
	 * @method - http request method
	 * */
	@SuppressWarnings("static-access")
	public String makeServiceCall() {
		return this.makeServiceCall(null, null, null);
	}

	/**
	 * Making service call
	 * This is method to calls web services it must be called in doInBackground
	 * it is return the JSON string
	 * @url - url to make request
	 * @reqParam - request params
	 * @method - http request method get or post.
	 * */
	@SuppressWarnings("deprecation")
	public static String makeServiceCall(String webUrl, String reqParam, String method) {
		// Response variable
		String jsonString = "";
		HttpURLConnection httpURLConnection = null;
		InputStream is =null;

		try {
			// URL & HttpURLConnection to send the request to the server
			System.setProperty("http.keepAlive", "false");
			URL url = new URL(webUrl);
			
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setFixedLengthStreamingMode(reqParam.getBytes().length);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
				httpURLConnection.setRequestProperty("Connection", "close");
			}
			httpURLConnection.setRequestMethod(method);
			httpURLConnection.setDoOutput(true);
			// out : object for setup the parameter values from the @reqParam
			PrintWriter out = new PrintWriter(httpURLConnection.getOutputStream());
			out.print(reqParam);
			out.close();

			is = httpURLConnection.getInputStream();
			if (is != null) {
				String line = null;
				StringBuffer stringBuffer = new StringBuffer();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
				while ((line = reader.readLine()) != null) {
					stringBuffer.append(line);
				}
				jsonString = stringBuffer.toString();

				
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(httpURLConnection!=null)
			{
				httpURLConnection.disconnect();
				
			}
			
			/* Close Stream */
			if (is != null) {
				try {
					
					is.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return jsonString;
	}

	// This method return the encoded format of given input
	public static String getEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	private static InputStream OpenHttpConnection(String urlString) throws IOException {
		InputStream in = null;

		int response = -1;

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (Exception ex) {
			throw new IOException("Error connecting");
		}
		return in;
	}

	public static Bitmap DownloadImage(String URL) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = OpenHttpConnection(URL);
			bitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return bitmap;
	}
}