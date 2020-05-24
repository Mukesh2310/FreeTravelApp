package com.freetravelapp.register.registertype;

import com.freetravelapp.R;
import com.freetravelapp.findyourride.FindYourRideActivity;
import com.freetravelapp.register.registertype.rider.RiderActivity;
import com.freetravelapp.register.registertype.rideseeker.RideSeekerActivity;

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
public class RegisterTypeSelectionActivity extends Activity implements OnClickListener{

	//TextView
	private TextView txtRideSeeker;
	private TextView txtRider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_type_selection);

		//TextView 
		txtRideSeeker=(TextView) findViewById(R.id.txtRideSeeker);
		txtRider=(TextView) findViewById(R.id.txtRider);
		
		txtRideSeeker.setOnClickListener(this);
		txtRider.setOnClickListener(this);

	}//End of OnCreate

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.txtRideSeeker:
			Intent intentRider=new Intent(RegisterTypeSelectionActivity.this, RideSeekerActivity.class);
			startActivity(intentRider);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
			
		case R.id.txtRider:
			Intent intent=new Intent(RegisterTypeSelectionActivity.this, RiderActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;
			
		default:
			break;
		}

	}

}//End Of Class
