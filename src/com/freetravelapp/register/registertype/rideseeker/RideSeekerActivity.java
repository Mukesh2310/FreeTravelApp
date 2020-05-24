package com.freetravelapp.register.registertype.rideseeker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.freetravelapp.PHPServiceHandler;
import com.freetravelapp.R;
import com.freetravelapp.Utills;
import com.freetravelapp.placeautocomplete.Location_Screen;
import com.freetravelapp.register.registertype.rider.RiderActivity;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.sleepbot.datetimepicker.time.TimePickerDialog.OnTimeSetListener;

/**
 * 
 * @author Mukesh Thakur
 * 
 */
public class RideSeekerActivity extends FragmentActivity implements
OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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

	String DATEPICKER_TAG, TIMEPICKER_TAG;

	private EditText editRideSeekerDate;
	private EditText editRideSeekerTime;
	private Object response;
	Button btnSave;
	private EditText editName;
	private EditText editNo;
	private TextView tvFrom;
	private TextView tvTo;
	private RadioGroup radioGender;

	String selectedGender="M";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ride_seeker);

		datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
		timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);


		editRideSeekerDate = (EditText) findViewById(R.id.editRiderDate);
		editRideSeekerDate.setOnClickListener(this);

		editRideSeekerTime = (EditText) findViewById(R.id.editRiderTime);
		editRideSeekerTime.setOnClickListener(this);

		editName = (EditText) findViewById(R.id.editName);
		editNo = (EditText) findViewById(R.id.editNo);
		tvFrom = (TextView) findViewById(R.id.editFrom);
		tvTo = (TextView) findViewById(R.id.editTo);
		tvFrom.setOnClickListener(this);
		tvTo.setOnClickListener(this);

		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);


		radioGender = (RadioGroup) findViewById(R.id.radioGroupGender);

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


	}// End of OnCreate

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.editRiderTime:
			//			timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
			timePickerDialog.setStartTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
			timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
			editRideSeekerTime.setError(null);
			break;

		case R.id.editRiderDate:
			//			datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
			showDate(1);
			editRideSeekerDate.setError(null);
			break;

		case R.id.editTo:

			Intent i = new Intent(RideSeekerActivity.this,Location_Screen.class);
			i.putExtra("BackgroundColor", "1");
			startActivityForResult(i, 2);

			break;

		case R.id.editFrom:

			Intent i2 = new Intent(RideSeekerActivity.this,Location_Screen.class);
			i2.putExtra("BackgroundColor", "1");
			startActivityForResult(i2, 3);

			break;

		case R.id.btnSave:
			Validation();
			break;

		default:
			break;
		}

	}

	@SuppressWarnings("unused")
	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		try {
			String timeString = "" + hourOfDay + ":" + minute;
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
			Date dateObj = sdfTime.parse(timeString);

			SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
			String s = sdf1.format(dateObj);
			//			currentTime = sdfTime.format(dt);

			editRideSeekerTime.setText(s);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {

		Date d = new Date(year - 1900, month, day);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String dateString = format.format(d);

		editRideSeekerDate.setText(dateString);

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
				df.setTimeZone(TimeZone.getTimeZone(Utills.getTimeZone(RideSeekerActivity.this)));
				String date = df.format(new Date());
				String selectedDate = df.format(selectedCal.getTime());
				try {
					Date current = df.parse(date);
					Date selected = df.parse(selectedDate);

					if (selected.before(current)) {
						Toast.makeText(RideSeekerActivity.this, "Can't select previous dates", Toast.LENGTH_SHORT).show();
						showDate(Mode);
					} else {
						selectedMonth = month;
						selectedYear = year;
						selectedDay = day;

						selPickDate = selectedDate;
						editRideSeekerDate.setText(selPickDate);

					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}, YEAR, MONTH, DAY + 1);

		date.setDateRange(YEAR, MONTH, DAY + 1, YEAR + 1, MONTH, DAY);
		date.show(getSupportFragmentManager(), "Select Date");
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);

		try {

			if (requestCode == 2) {
				// Make sure the request was successful
				// if (resultCode == RESULT_OK) {
				String str = data.getStringExtra("place");
				tvTo.setText(str);
				// }
			} else if (requestCode == 3) {

				String str = data.getStringExtra("place");
				tvFrom.setText(str);

			}
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void Validation()
	{

		if(editName.getText().toString().trim().length()<=0){

//			Toast.makeText(RideSeekerActivity.this,"Please insert Your name", 1).show();
			
			Utills.CustomLDilogeYes(RideSeekerActivity.this, "Alert", "OK", "Please insert Your name");

		}else if(tvTo.getText().toString().trim().length()<=0){

//			Toast.makeText(RideSeekerActivity.this,"Please insert Your start point location", 1).show();
			
			Utills.CustomLDilogeYes(RideSeekerActivity.this, "Alert", "OK", "Please insert Your start point location");

		}else if(tvFrom.getText().toString().trim().length()<=0){

//			Toast.makeText(RideSeekerActivity.this,"Please insert your end point location",1).show();
			
			Utills.CustomLDilogeYes(RideSeekerActivity.this, "Alert", "OK", "Please insert your end point location");

		}else if(editRideSeekerDate.getText().toString().trim().length()<=0){

//			Toast.makeText(RideSeekerActivity.this,"Please insert your prieferred date",1).show();
			
			Utills.CustomLDilogeYes(RideSeekerActivity.this, "Alert", "OK", "Please insert your prieferred date");

		}else if(editRideSeekerTime.getText().toString().trim().length()<=0){

//			Toast.makeText(RideSeekerActivity.this,"Please insert Your prieferred time",1).show();
			
			Utills.CustomLDilogeYes(RideSeekerActivity.this, "Alert", "OK", "Please insert Your prieferred time");


		}else if(editNo.getText().toString().trim().length()<=0){

//			Toast.makeText(RideSeekerActivity.this,"Please insert Your ContactNo",1).show();
			
			Utills.CustomLDilogeYes(RideSeekerActivity.this, "Alert", "OK", "Please insert Your ContactNo");

		}else{

			if (Utills.checkConnectivity(RideSeekerActivity.this)) {

				new RegisterRideSeekerTask().execute();

			} else {

//				Utills.showOneButtonDialog(RideSeekerActivity.this,"No Internet Connection found");
				
				Utills.CustomLDilogeYes(RideSeekerActivity.this, "Alert", "OK", "No Internet Connection found");

			}

		}
	}

	/*
	 * private void getDataInList() { for (int i = 0; i < title.length; i++) {
	 * ListData ld = new ListData(); ld.setTitle(title[i]);
	 * ld.setDescription(Disc[i]); ld.setShow(false); myList.add(ld); } }
	 */

	public class RegisterRideSeekerTask extends AsyncTask<Void, Void, String> {

		String Url = "http://www.orangetechnosoft.com/freetravelapp/?json=insert_ride_seeker";

		@Override
		protected void onPreExecute() {

			Utills.showProgressDialog(RideSeekerActivity.this, "Please wait");
		}

		@Override
		protected String doInBackground(Void... params) {
			try {

				String reqParam = "";
				reqParam += "&seeker_name="+ PHPServiceHandler.getEncode("parth");
				reqParam += "&start_point="+ PHPServiceHandler.getEncode("Ahmedabad");
				reqParam += "&end_point=" + PHPServiceHandler.getEncode("surt");
				reqParam += "&date=" + PHPServiceHandler.getEncode("9-10-2015");
				reqParam += "&time=" + PHPServiceHandler.getEncode("9:15am");
				reqParam += "&contact_no="+ PHPServiceHandler.getEncode("2514251452");
				reqParam += "&partner" + PHPServiceHandler.getEncode("m");


				reqParam += "&seeker_name="+ PHPServiceHandler.getEncode(editName.getText().toString().trim());//name
				reqParam += "&start_point="+ PHPServiceHandler.getEncode(tvTo.getText().toString().trim());//start location
				reqParam += "&end_point="+ PHPServiceHandler.getEncode(tvFrom.getText().toString().trim());//end location
				reqParam += "&date="+ PHPServiceHandler.getEncode(editRideSeekerDate.getText().toString().trim());//date
				reqParam += "&time="+ PHPServiceHandler.getEncode(editRideSeekerTime.getText().toString().trim());//time
				reqParam += "&contact_no="+ PHPServiceHandler.getEncode(editNo.getText().toString().trim());//contact number
				reqParam += "&partner="+ PHPServiceHandler.getEncode(selectedGender);// gender

				Log.e("URL==>", Url);
				Log.e("NameValue==> ", reqParam + "");

				response = PHPServiceHandler.makeServiceCall(Url, reqParam,
						PHPServiceHandler.GET);

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

				Toast.makeText(RideSeekerActivity.this,"Your ride  posted successfully  ",Toast.LENGTH_LONG).show();

				Utills.dismissDialog();

				finish();

			} catch (Exception e) {

				Utills.dismissDialog();
				e.printStackTrace();
				Log.e("Warning in BackGroundTask==> PriorNotice", e + "");
			}

			// progressDialog.dismiss();
		}// end of postFetcher

	}

}// End Of Class
