package com.freetravelapp.register.registertype.rider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.Utils;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.freetravelapp.PHPServiceHandler;
import com.freetravelapp.R;
import com.freetravelapp.Utills;
import com.freetravelapp.placeautocomplete.Location_Screen;
import com.freetravelapp.placeautocomplete.PlaceJSONParser;
import com.freetravelapp.register.registertype.rideseeker.RideSeekerActivity;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.sleepbot.datetimepicker.time.TimePickerDialog.OnTimeSetListener;

/**
 * 
 * @author Mukesh Thakur
 *
 */
public class RiderActivity extends FragmentActivity implements OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


	//TextView
	private TextView txtRideSeeker;
	private TextView txtVehicalRegister;

	//EditText
	private EditText editVehicalRegister;
	private EditText editVehicalName;
	private EditText editVehicalType;
	private EditText editStartPoint;

	private EditText editAreaName;
	private EditText editEndPoint;
	private TextView editCityName;
	private TextView editEndPointCityName;
	private EditText editEndPointAreaName;
	private EditText editDate;
	private EditText editTime;
	private EditText editContactNo;

	//Button
	private Button btnSave;

	public static final String DATEPICKER_TAG = "datepicker";
	public static final String TIMEPICKER_TAG = "timepicker";
	private DatePickerDialog datePickerDialog;
	private TimePickerDialog timePickerDialog;
	private Calendar calendar = Calendar.getInstance();
	
	private int YEAR, MONTH, DAY;
    int success = 0;
    private int selectedMonth, selectedYear, selectedDay;
    private int selectedStartMonth, selectedStartYear, selectedStartDay;
    String selPickDate = "";

	private int AMPM;

	boolean statusStart = false;
	boolean statusEnd = false;

	Date currentDateToCompare, selectedDateToCompare;
	Date currentTimeToCompare, selectedTimeToCompare;
	private int hourFromSelection, mintFromSelection;
	private String amOrPMFromSelection = "";

	private String currentDate = "", currentTime = "";

	Date dt;
	SimpleDateFormat sdf, sdfTime;


	String response;
	String addressFrom,addressTo;
	int isCommingFrom=0;
	int To=1,From=2;

	private RadioGroup radioGender,radioType;

	String selectedGender="M";
	String selectedType="2";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rider);

		datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
		timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);


		//TextView 
		txtRideSeeker=(TextView) findViewById(R.id.txtRideSeeker);

		//EditText
		editVehicalRegister=(EditText) findViewById(R.id.editVehicalRegister);
		editVehicalName=(EditText) findViewById(R.id.editVehicalName);
		editCityName=(TextView) findViewById(R.id.editCityName);
		editAreaName=(EditText) findViewById(R.id.editAreaName);
		editEndPointCityName=(TextView) findViewById(R.id.editEndPointCityName);
		editEndPointAreaName=(EditText) findViewById(R.id.editEndPointAreaName);

		editDate=(EditText) findViewById(R.id.editDate);
		editTime=(EditText) findViewById(R.id.editTime);
		editContactNo=(EditText) findViewById(R.id.editContactNo);

		btnSave=(Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		editTime.setOnClickListener(this);
		editDate.setOnClickListener(this);
		editCityName.setOnClickListener(this);
		editEndPointCityName.setOnClickListener(this);


		radioGender = (RadioGroup) findViewById(R.id.radioGroupGender);

		radioType = (RadioGroup) findViewById(R.id.radioGroupType);

		radioType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if(checkedId == R.id.radioBike) {

					selectedType="2";

					//	Toast.makeText(getApplicationContext(), "choice: male",Toast.LENGTH_SHORT).show();
				} else if(checkedId == R.id.radioCar) {

					selectedType="4";

					//	Toast.makeText(getApplicationContext(), "choice: female",Toast.LENGTH_SHORT).show();
				} 
			}

		});

		radioGender.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if(checkedId == R.id.radio_male) {

					selectedGender="M";

					//	Toast.makeText(getApplicationContext(), "choice: male",Toast.LENGTH_SHORT).show();
				} else if(checkedId == R.id.radio_female) {

					selectedGender="F";

					//	Toast.makeText(getApplicationContext(), "choice: female",Toast.LENGTH_SHORT).show();
				} 
			}

		});

	}//End of OnCreate

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnSave:

			//			if(Utills.checkConnectivity(RiderActivity.this))
			//			{
			//				
			//				
			//				new RegisterRiderTask().execute();
			//			}else{
			//				
			//				Utills.showOneButtonDialog(RiderActivity.this,"No Internet Connection found");
			//			}

			Validation();

			break;

		case R.id.editTime:
//			timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
			 timePickerDialog.setStartTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
             timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
			editTime.setError(null);
			break;

		case R.id.editDate:
//			datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
			showDate(1);
			editDate.setError(null);
			break;

		case R.id.editCityName:

			Intent i =new Intent(RiderActivity.this,Location_Screen.class);
			i.putExtra("BackgroundColor", "2");
			startActivityForResult(i, 2);

			break;

		case R.id.editEndPointCityName:

			Intent i2 =new Intent(RiderActivity.this,Location_Screen.class);
			i2.putExtra("BackgroundColor", "2");
			startActivityForResult(i2, 3);

			break;


		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);

		try {

			if (requestCode == 2) {
				// Make sure the request was successful
				//   if (resultCode == RESULT_OK) {
				String str=data.getStringExtra("place");
				editCityName.setText(str);
				//   }
			}else if (requestCode == 3) {

				String str=data.getStringExtra("place");
				editEndPointCityName.setText(str);

			}
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public void Validation()
	{

		if(editVehicalName.getText().toString().trim().length()<=0){

//			Toast.makeText(RiderActivity.this,"Please insert Your name", 1).show();
//			Utills.showOneButtonDialog(RiderActivity.this,"Please insert Your name");
			Utills.CustomLDilogeYes(RiderActivity.this, "Alert", "OK", "Please insert Your name");

		}else if(editVehicalRegister.getText().toString().trim().length()<=0){

//			Toast.makeText(RiderActivity.this,"Please insert your vehical number", 1).show();
			
			Utills.CustomLDilogeYes(RiderActivity.this, "Alert", "OK", "Please insert your vehical number");

		}else if(editCityName.getText().toString().trim().length()<=0){

//			Toast.makeText(RiderActivity.this,"Please insert Your start point location", 1).show();
			
			Utills.CustomLDilogeYes(RiderActivity.this, "Alert", "OK", "Please insert Your start point location");

		}else if(editEndPointCityName.getText().toString().trim().length()<=0){

//			Toast.makeText(RiderActivity.this,"Please insert your end point location", 1).show();
			
			Utills.CustomLDilogeYes(RiderActivity.this, "Alert", "OK", "Please insert your end point location");

		}else if(editDate.getText().toString().trim().length()<=0){

//			Toast.makeText(RiderActivity.this,"Please insert your prieferred date", 1).show();
			
			Utills.CustomLDilogeYes(RiderActivity.this, "Alert", "OK", "Please insert your prieferred date");

		}else if(editTime.getText().toString().trim().length()<=0){

//			Toast.makeText(RiderActivity.this,"Please insert Your prieferred time", 1).show();
			
			Utills.CustomLDilogeYes(RiderActivity.this, "Alert", "OK", "Please insert Your prieferred time");

		}else if(editContactNo.getText().toString().trim().length()<=0){

//			Toast.makeText(RiderActivity.this,"Please insert Your ContactNo", 1).show();
			
			Utills.CustomLDilogeYes(RiderActivity.this, "Alert", "OK", "Please insert Your ContactNo");

		}else{

			if(Utills.checkConnectivity(RiderActivity.this))
			{

				new RegisterRiderTask().execute();
			}else{

//				Utills.showOneButtonDialog(RiderActivity.this,"No Internet Connection found");
				Utills.CustomLDilogeYes(RiderActivity.this, "Alert", "OK", "No Internet Connection found");
			}

		}
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		try {
			String timeString = "" + hourOfDay + ":" + minute;
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
			Date dateObj = sdfTime.parse(timeString);

			SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
			String s = sdf1.format(dateObj);
			//			currentTime = sdfTime.format(dt);

			editTime.setText(s);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

		@SuppressWarnings("deprecation")
		Date d = new Date(year - 1900, month, day);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String dateString = format.format(d);

		editDate.setText(dateString);

	}
	
	
	
	 private void showDate(final int Mode) {
	        final Calendar c = Calendar.getInstance();
	        YEAR = c.get(Calendar.YEAR);
	        MONTH = c.get(Calendar.MONTH);
	        DAY = c.get(Calendar.DAY_OF_MONTH);

	        DatePickerDialog date = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
	            @Override
	            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

	                Calendar selectedCal = Calendar.getInstance();
	                selectedCal.set(year, month, day);

	              //  long selectedMilli = selectedCal.getTimeInMillis();

	                //Date datePickerDate = new Date(selectedMilli);
	                DateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("English"));
	                df.setTimeZone(TimeZone.getTimeZone(Utills.getTimeZone(RiderActivity.this)));
	                String date = df.format(new Date());
	                String selectedDate = df.format(selectedCal.getTime());
	                try {
	                    Date current = df.parse(date);
	                    Date selected = df.parse(selectedDate);

	                    if (selected.before(current)) {
	                        Toast.makeText(RiderActivity.this, "Can't select previous dates", Toast.LENGTH_SHORT).show();
	                        showDate(Mode);
	                    } else {
	                        selectedMonth = month;
	                        selectedYear = year;
	                        selectedDay = day;

	                        selPickDate = selectedDate;
	                        editDate.setText(selPickDate);

	                    }
	                } catch (ParseException e) {
	                    e.printStackTrace();
	                }
	            }
	        }, YEAR, MONTH, DAY + 1);

	        date.setDateRange(YEAR, MONTH, DAY + 1, YEAR + 1, MONTH, DAY);
	        date.show(getSupportFragmentManager(), "Select Date");
	    }



	public class RegisterRiderTask extends AsyncTask<Void, Void, String> {

		String Url ="http://www.orangetechnosoft.com/freetravelapp/?json=insert_rider";

		@Override
		protected void onPreExecute() {

			Utills.showProgressDialog(RiderActivity.this,"Please wait");
		}

		@Override
		protected String doInBackground(Void... params) {
			try {

				String reqParam = "";
				reqParam += "&rider_name="+ PHPServiceHandler.getEncode(editVehicalName.getText().toString().trim());//name
				reqParam += "&venicle_no="+ PHPServiceHandler.getEncode(editVehicalRegister.getText().toString().trim());//number
				reqParam += "&vehicle_type="+ PHPServiceHandler.getEncode(selectedType);//Vehicle type
				reqParam += "&start_point="+ PHPServiceHandler.getEncode(editCityName.getText().toString().trim());//start location
				reqParam += "&end_point="+ PHPServiceHandler.getEncode(editEndPointCityName.getText().toString().trim());//end location
				reqParam += "&date="+ PHPServiceHandler.getEncode(editDate.getText().toString().trim());//date
				reqParam += "&time="+ PHPServiceHandler.getEncode(editTime.getText().toString().trim());//time
				reqParam += "&contact_no="+ PHPServiceHandler.getEncode(editContactNo.getText().toString().trim());//contact number
				reqParam += "&partner="+ PHPServiceHandler.getEncode(selectedGender);// gender

				Log.e("URL==>", Url);
				Log.e("NameValue==> ", reqParam + "");

				response = PHPServiceHandler.makeServiceCall(Url,reqParam,PHPServiceHandler.GET);

				// Log.e("respose==>", response);

				return response.toString();

			} catch (Exception e) {
				e.printStackTrace();

				response = null;
			}
			return response.toString();
		}

		@Override
		protected void onPostExecute(String result) {
			try {

				Log.e("result==>", response.toString());

				Toast.makeText(RiderActivity.this,"Your ride  posted successfully  ",Toast.LENGTH_LONG).show();

				Utills.dismissDialog();

				finish();

			} catch (Exception e) {
				Utills.dismissDialog();
				e.printStackTrace();
				Log.e("Warning in BackGroundTask==> PriorNotice", e + "");
			}

		}// end of postFetcher

	}

}//End Of Class





