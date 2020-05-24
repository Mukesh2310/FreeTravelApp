package com.freetravelapp.findyourride.search.detail;

import com.freetravelapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SearchRideSeekerActivity extends Activity {
	
	TextView txtNameSearch;
	TextView txtToSearch;
	TextView txtFromSearch;
	TextView txtRiderDateSearch;
	TextView txtRiderTimeSearch;
	TextView txteditNoSearch;
	TextView txtseekerPar;
	
	private Button btnSave;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ride_seeker_search);
		
		Intent intent = getIntent();
		
		txtNameSearch=(TextView) findViewById(R.id.txtNameSearch);
		txtToSearch=(TextView) findViewById(R.id.txtToSearch);
		txtFromSearch=(TextView) findViewById(R.id.txtFromSearch);
		txtRiderDateSearch=(TextView) findViewById(R.id.txtRiderDateSearch);
		txtRiderTimeSearch=(TextView) findViewById(R.id.txtRiderTimeSearch);
		txteditNoSearch=(TextView) findViewById(R.id.txteditNoSearch);
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
		
		txtRiderDateSearch.setText(seeker_date);
		txtRiderTimeSearch.setText(seeker_time);
		txtNameSearch.setText(seeker_name);
		txteditNoSearch.setText(seeker_contact);
		txtToSearch.setText(seeker_start_point);
		txtFromSearch.setText(seeker_end_point);
		if(seeker_partner.equalsIgnoreCase("M")){
			txtseekerPar.setText("Male");
		}else {
			txtseekerPar.setText("Female");
		}
	}

}
