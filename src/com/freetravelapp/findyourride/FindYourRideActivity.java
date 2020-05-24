package com.freetravelapp.findyourride;

import com.freetravelapp.R;
import com.freetravelapp.findyourride.search.SearchActivity;
import com.freetravelapp.register.registertype.RegisterTypeSelectionActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 
 * @author Mukesh Thakur
 *
 */
public class FindYourRideActivity extends Activity implements OnClickListener{

	//TextView
	private TextView txtFindYourRide;
	private TextView txtRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_your_ride);

		//TextView 
		txtFindYourRide=(TextView) findViewById(R.id.txtFindYourRide);
		txtRegister=(TextView) findViewById(R.id.txtRegister);
		
		txtFindYourRide.setOnClickListener(this);
		txtRegister.setOnClickListener(this);

	}//End of OnCreate

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.txtFindYourRide:
			Intent intent=new Intent(FindYourRideActivity.this, SearchActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
		case R.id.txtRegister:
			Intent intentRegister=new Intent(FindYourRideActivity.this, RegisterTypeSelectionActivity.class);
			startActivity(intentRegister);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
			
		default:
			break;
		}

	}

}//End Of Class
