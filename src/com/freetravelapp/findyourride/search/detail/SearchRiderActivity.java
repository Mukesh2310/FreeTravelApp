package com.freetravelapp.findyourride.search.detail;

import com.freetravelapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SearchRiderActivity extends Activity {

	TextView txtNameSearch;
	TextView txtVehicleNameSearch;
	TextView txtCityNameSearch;
	TextView txtEndPointCityNameSearch;
	TextView txtDateSearch;
	TextView txtTimeSearch;
	TextView txtContactNoSearch;
	TextView txtseekerPar;
	
	private Button btnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rider_search);

		Intent intent = getIntent();
		
		txtNameSearch=(TextView) findViewById(R.id.txtNameSearch);
		txtVehicleNameSearch=(TextView) findViewById(R.id.txtVehicleNameSearch);
		txtCityNameSearch=(TextView) findViewById(R.id.txtCityNameSearch);
		txtEndPointCityNameSearch=(TextView) findViewById(R.id.txtEndPointCityNameSearch);
		txtDateSearch=(TextView) findViewById(R.id.txtDateSearch);
		txtTimeSearch=(TextView) findViewById(R.id.txtTimeSearch);
		txtContactNoSearch=(TextView) findViewById(R.id.txtContactNoSearch);
		txtseekerPar=(TextView) findViewById(R.id.txtseeker_partner);
		
		btnSave=(Button) findViewById(R.id.btnSave);
		btnSave.setVisibility(View.INVISIBLE);
		
		String seeker_name = intent.getStringExtra("seeker_name");
		String seeker_date = intent.getStringExtra("seeker_date");
		String ride_type = intent.getStringExtra("ride_type");
		String seeker_time = intent.getStringExtra("seeker_time");
		String seeker_start_point = intent.getStringExtra("seeker_start_point");
		String seeker_end_point = intent.getStringExtra("seeker_end_point");
		String seeker_partner = intent.getStringExtra("seeker_partner");
		String seeker_contact = intent.getStringExtra("seeker_contact");
		String vehicle_no = intent.getStringExtra("vehicle_no");
		String vehicle_type = intent.getStringExtra("vehicle_type");
		
		txtDateSearch.setText(seeker_date);
		txtTimeSearch.setText(seeker_time);
		txtNameSearch.setText(seeker_name);
		txtContactNoSearch.setText(seeker_contact);
		txtCityNameSearch.setText(seeker_start_point);
		txtEndPointCityNameSearch.setText(seeker_end_point);
		txtVehicleNameSearch.setText(vehicle_no);
		if(seeker_partner.equalsIgnoreCase("M")){
			txtseekerPar.setText("Male");
		}else {
			txtseekerPar.setText("Female");
		}
		
	}

}
