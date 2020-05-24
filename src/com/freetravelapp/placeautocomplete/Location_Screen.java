package com.freetravelapp.placeautocomplete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.freetravelapp.R;
import com.freetravelapp.Utills;
import com.freetravelapp.register.registertype.rideseeker.RideSeekerActivity;
import com.freetravelapp.register.registertype.rideseeker.RideSeekerActivity.RegisterRideSeekerTask;

/**
 * 
 * @author Mukesh Thakur
 * 
 */
public class Location_Screen extends Activity {

	CustomAutoCompleteTextView editAdress;
	ProgressBar pBar;

	String addressFrom;
	boolean isCity;

	private RelativeLayout relativeLocationMain;
	String[] Country;
	List<HashMap<String, String>> list;
	List<HashMap<String, String>> CountryList;
	SimpleAdapter adapter;
	String[] from = { "description" };
	int[] to = { R.id.txtName };
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_);

		relativeLocationMain = (RelativeLayout) findViewById(R.id.relativeLocationMain);
		String newString;
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				newString = null;
			} else {
				newString = extras.getString("BackgroundColor");
			}
		} else {
			newString = (String) savedInstanceState
					.getSerializable("BackgroundColor");
		}

		Country = getResources().getStringArray(R.array.country);
		list = new ArrayList<HashMap<String, String>>();
		CountryList= new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < Country.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("description", Country[i]);
			CountryList.add(map);
		}
		
		

		if (newString.equalsIgnoreCase("1")) {
			relativeLocationMain.setBackgroundResource(R.drawable.background);
		} else {
			relativeLocationMain
					.setBackgroundResource(R.drawable.background_blue);
		}

		editAdress = (CustomAutoCompleteTextView) findViewById(R.id.editAdress);
		editAdress.setThreshold(1);
		
		isCity = true;
		

		adapter = new SimpleAdapter(getBaseContext(),CountryList, R.layout.custom_location, from, to);
		
		editAdress.setAdapter(adapter);
		
		
		pBar = (ProgressBar) findViewById(R.id.custom_progress);

		editAdress.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String str = editAdress.getText().toString();
				
				if(str.length()==0)
				{
					adapter = new SimpleAdapter(getBaseContext(),CountryList, R.layout.custom_location, from, to);
					
					editAdress.setAdapter(adapter);
					
				}else if (str.length() > 2) {

					isCity = false;

					if (Utills.checkConnectivity(Location_Screen.this)) {
						new PlacesTask().execute(str);
					} else {
						Utills.showOneButtonDialog(Location_Screen.this,
								"No Internet Connection found");
					}

				}
			}
		});

		editAdress.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (isCity) {

					HashMap<String, String> map = new HashMap<String, String>();
//					String s = arg0.getAdapter().getItem(arg2).toString();
					
//					addressFrom = arg0.getAdapter().getItem(arg2)+"";
					
					map = (HashMap<String, String>) arg0.getItemAtPosition(arg2);

					Toast.makeText(getApplicationContext(), "Clicked: " + map,Toast.LENGTH_SHORT).show();

				} else {
					addressFrom = arg0.getItemAtPosition(arg2) + "";
					int index = addressFrom.lastIndexOf('=');
					addressFrom = addressFrom.substring(index + 1).replace("}","");
					Log.e("addressFrom", addressFrom);

					Bundle bundle = new Bundle();
					bundle.putString("place", addressFrom);

					Intent in = new Intent();
					in.putExtras(bundle);
					setResult(1, in);
					finish();

				}
				//
				// Toast.makeText(getApplicationContext(),
				// "Clicked",Toast.LENGTH_SHORT).show();

			}

		});

	}// End of OnCreate

	// Fetches all places from GooglePlaces AutoComplete Web Service
	private class PlacesTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {

			pBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... place) {
			// For storing data from web service
			String data = "";

			// Obtain browser key from https://code.google.com/apis/console
			String key = "key=AIzaSyDBcTXAe-30bidwsYs-LHkZ6nVtIxUoPfw";

			String input = "";

			try {
				input = "input=" + URLEncoder.encode(place[0], "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			// place type to be searched
			String types = "trpes=locality";

			// Sensor enabled
			String sensor = "sensor=false";

			// String mode = "mode=driving";

			// Building the parameters to the web service
			String parameters = input + "&" + types + "&" + sensor + "&" + key /*
																				 * +
																				 * "&"
																				 * +
																				 * mode
																				 */;

			// Output format
			String output = "json";

			// Building the url to the web service
			String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
					+ output + "?" + parameters;

			Log.e("Full URL==>", url + "");
			try {
				// Fetching the data from web service in background
				data = downloadUrl(url);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Log.d("result Task", result);
			// Creating ParserTask
			ParserTask parserTask = new ParserTask();

			// Starting Parsing the JSON string returned by Web Service
			parserTask.execute(result);
		}
	} // End of PlacesTask.

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<HashMap<String, String>>> {

		JSONObject jObject;

		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {

			List<HashMap<String, String>> places = null;

			PlaceJSONParser placeJsonParser = new PlaceJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);

				// Getting the parsed data as a List construct
				places = placeJsonParser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) {

			list.clear();
			list=result;
			pBar.setVisibility(View.GONE);
			

			// Creating a SimpleAdapter for the AutoCompleteTextView
			adapter = new SimpleAdapter(getBaseContext(), list,R.layout.custom_location, from, to);

			editAdress.setAdapter(adapter);
		}

	} // End of ParserTask.

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	} // End of downloadUrl.

}// End Of Class
