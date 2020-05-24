package com.freetravelapp.findyourride.search;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.freetravelapp.PHPServiceHandler;
import com.freetravelapp.R;
import com.freetravelapp.Utills;
import com.freetravelapp.findyourride.search.detail.SearchRideSeekerActivity;
import com.freetravelapp.findyourride.search.detail.SearchRiderActivity;
import com.freetravelapp.placeautocomplete.Location_Screen;
import com.freetravelapp.register.registertype.RegisterTypeSelectionActivity;

/**
 * 
 * @author Mukesh Thakur
 *
 */
public class SearchActivity extends Activity implements OnClickListener{

	ListView listSearch;
	List<SearchData> rowItems;

	private TextView txtStartPoint;
	private TextView txtEndPoint;

	String response;

	private Button btnDoneOne;
	private ArrayList<SearchData> listSearchActivity;
	private ArrayList<SearchData> listSearchActivityPersist;

	CustomBaseAdapter searchAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		listSearchActivity = new ArrayList<SearchData>();
		listSearchActivityPersist = new ArrayList<SearchData>();

		listSearch = (ListView) findViewById(R.id.listSearch);

		txtStartPoint=(TextView) findViewById(R.id.txtStartPoint);
		txtEndPoint=(TextView) findViewById(R.id.txtEndPoint);
		btnDoneOne=(Button) findViewById(R.id.btnDoneOne);

		txtStartPoint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i =new Intent(SearchActivity.this,Location_Screen.class);
				i.putExtra("BackgroundColor", "1");
				startActivityForResult(i, 2);
			}
		});

		txtEndPoint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i2 =new Intent(SearchActivity.this,Location_Screen.class);
				i2.putExtra("BackgroundColor", "1");
				startActivityForResult(i2, 3);
			}
		});

		btnDoneOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(Utills.checkConnectivity(SearchActivity.this)) {
					new RegisterRiderTask().execute();
				}else{
					Utills.showOneButtonDialog(SearchActivity.this,"No Internet Connection found");
				}
			}
		});

	}//End of OnCreate

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode == 2) {
				String str=data.getStringExtra("place");
				txtStartPoint.setText(str);
			}else if (requestCode == 3) {
				String str=data.getStringExtra("place");
				txtEndPoint.setText(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class CustomBaseAdapter extends BaseAdapter {
		List<SearchData> exploreListData;
		ArrayList<SearchData> arraylist;
		Context mContext;
		public CustomBaseAdapter(Context mContext, List<SearchData> items) {

			this.mContext = mContext;
			this.exploreListData = items;
			this.arraylist = new ArrayList<SearchData>();
			this.arraylist.addAll(exploreListData);
		}

		/*private view holder class*/
		private class ViewHolder {
			TextView txtName;
			TextView txtDate;
			TextView txtType;
			TextView txttime;
			LinearLayout ll1;
			LinearLayout linearMain;
		}

		@Override
		public int getCount() {
			return exploreListData.size();
		}

		@Override
		public Object getItem(int position) {
			return exploreListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.custom_search, null);
				holder = new ViewHolder();
				holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
				holder.ll1 = (LinearLayout)convertView.findViewById(R.id.ll1);
				holder.linearMain=(LinearLayout) convertView.findViewById(R.id.linearMain);
				holder.txtDate=(TextView) convertView.findViewById(R.id.txtDate);
				holder.txtType=(TextView) convertView.findViewById(R.id.txtType);
				holder.txttime=(TextView) convertView.findViewById(R.id.txttime);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			SearchData rowItem = (SearchData) getItem(position);
			holder.txtName.setText(exploreListData.get(position).getSeeker_name());
			holder.txtDate.setText(exploreListData.get(position).getSeeker_date());
			holder.txtType.setText(exploreListData.get(position).getRide_type());
			holder.txttime.setText(exploreListData.get(position).getSeeker_time());

			Animation animation = null;
			animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom);
			animation.setDuration(500);
			convertView.startAnimation(animation);
			animation = null;

			holder.txtName.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(exploreListData.get(position).getRide_type().equalsIgnoreCase("rider")){
						Intent intent=new Intent(SearchActivity.this, SearchRiderActivity.class);
						intent.putExtra("seeker_name", exploreListData.get(position).getSeeker_name());
						intent.putExtra("seeker_date", exploreListData.get(position).getSeeker_date());
						intent.putExtra("ride_type", exploreListData.get(position).getRide_type());
						intent.putExtra("seeker_time", exploreListData.get(position).getSeeker_time());
						intent.putExtra("seeker_start_point", exploreListData.get(position).getSeeker_start_point());
						intent.putExtra("seeker_end_point", exploreListData.get(position).getSeeker_end_point());
						intent.putExtra("seeker_partner", exploreListData.get(position).getSeeker_partner());
						intent.putExtra("seeker_contact", exploreListData.get(position).getSeeker_contact());
						intent.putExtra("vehicle_no", exploreListData.get(position).getVehicle_no());
						intent.putExtra("vehicle_type", exploreListData.get(position).getVehicle_type());
						startActivity(intent);
					}else {
						Intent intent=new Intent(SearchActivity.this, SearchRideSeekerActivity.class);
						intent.putExtra("seeker_name", exploreListData.get(position).getSeeker_name());
						intent.putExtra("seeker_date", exploreListData.get(position).getSeeker_date());
						intent.putExtra("ride_type", exploreListData.get(position).getRide_type());
						intent.putExtra("seeker_time", exploreListData.get(position).getSeeker_time());
						intent.putExtra("seeker_start_point", exploreListData.get(position).getSeeker_start_point());
						intent.putExtra("seeker_end_point", exploreListData.get(position).getSeeker_end_point());
						intent.putExtra("seeker_partner", exploreListData.get(position).getSeeker_partner());
						intent.putExtra("seeker_contact", exploreListData.get(position).getSeeker_contact());
						startActivity(intent);
					}
				}
			});
			
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(exploreListData.get(position).getRide_type().equalsIgnoreCase("rider")){
						Intent intent=new Intent(SearchActivity.this, SearchRiderActivity.class);
						intent.putExtra("seeker_name", exploreListData.get(position).getSeeker_name());
						intent.putExtra("seeker_date", exploreListData.get(position).getSeeker_date());
						intent.putExtra("ride_type", exploreListData.get(position).getRide_type());
						intent.putExtra("seeker_time", exploreListData.get(position).getSeeker_time());
						intent.putExtra("seeker_start_point", exploreListData.get(position).getSeeker_start_point());
						intent.putExtra("seeker_end_point", exploreListData.get(position).getSeeker_end_point());
						intent.putExtra("seeker_partner", exploreListData.get(position).getSeeker_partner());
						intent.putExtra("seeker_contact", exploreListData.get(position).getSeeker_contact());
						intent.putExtra("vehicle_no", exploreListData.get(position).getVehicle_no());
						intent.putExtra("vehicle_type", exploreListData.get(position).getVehicle_type());
						startActivity(intent);
					}else {
						Intent intent=new Intent(SearchActivity.this, SearchRideSeekerActivity.class);
						intent.putExtra("seeker_name", exploreListData.get(position).getSeeker_name());
						intent.putExtra("seeker_date", exploreListData.get(position).getSeeker_date());
						intent.putExtra("ride_type", exploreListData.get(position).getRide_type());
						intent.putExtra("seeker_time", exploreListData.get(position).getSeeker_time());
						intent.putExtra("seeker_start_point", exploreListData.get(position).getSeeker_start_point());
						intent.putExtra("seeker_end_point", exploreListData.get(position).getSeeker_end_point());
						intent.putExtra("seeker_partner", exploreListData.get(position).getSeeker_partner());
						intent.putExtra("seeker_contact", exploreListData.get(position).getSeeker_contact());
						startActivity(intent);
					}
				}
			});

			if ( position % 2 == 0) //0 even 1 odd..
				holder.linearMain.setBackgroundResource(R.drawable.layout_corner);
			else
				holder.linearMain.setBackgroundResource(R.drawable.layout_corner_green);
			return convertView;
		}


	}





	public class RegisterRiderTask extends AsyncTask<Void, Void, String> {
		JSONArray jarr = null;
		String Url ="http://www.orangetechnosoft.com/freetravelapp/?json=search_ride";

		@Override
		protected void onPreExecute() {
			Utills.showProgressDialog(SearchActivity.this,"Please wait");
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				String reqParam = "";
				reqParam += "&start_point="+ PHPServiceHandler.getEncode(txtStartPoint.getText().toString().trim());//name
				reqParam += "&end_point="+ PHPServiceHandler.getEncode(txtEndPoint.getText().toString().trim());//number
				Log.e("URL==>", Url);
				Log.e("NameValue==> ", reqParam + "");
				response = PHPServiceHandler.makeServiceCall(Url,reqParam,PHPServiceHandler.GET);
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
				Utills.dismissDialog();

				JSONObject jsonMainObject = new JSONObject(result.trim());
				Log.e("Full Responce======>", result.trim() + "");
				String status = jsonMainObject.optString("status");
				String seeker_data = jsonMainObject.optString("seeker-data");
				String message = jsonMainObject.optString("message");
				jarr = jsonMainObject.getJSONArray("rider");
				try {
					if (status.equalsIgnoreCase("success")) {
						for (int i = 0; i < jarr.length(); i++) {
							JSONObject job1 = jarr.getJSONObject(i);
							String seeker_name = job1.optString("seeker_name");
							String seeker_date = job1.optString("seeker_date");
							String seeker_time = job1.optString("seeker_time");
							String ride_type = job1.optString("ride-type");
							String seeker_start_point = job1.optString("seeker_start_point");
							String seeker_end_point = job1.optString("seeker_end_point");
							String seeker_partner = job1.optString("seeker_partner");
							String seeker_contact = job1.optString("seeker_contact");
							String vehicle_no = job1.optString("vehicle_no");
							String vehicle_type = job1.optString("vehicle_type");

							listSearchActivity.add(new SearchData(seeker_name, seeker_date, seeker_time, ride_type,seeker_start_point,seeker_end_point,seeker_partner,seeker_contact,vehicle_no,vehicle_type));
						}
						listSearchActivityPersist.addAll(listSearchActivity);
					} else {

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				searchAdapter = new CustomBaseAdapter(SearchActivity.this, listSearchActivity);
				listSearch.setAdapter(searchAdapter);

			} catch (Exception e) {
				Utills.dismissDialog();
				e.printStackTrace();
				Log.e("Warning in BackGroundTask==> PriorNotice", e + "");
			}

		}// end of postFetcher

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtFindYourRide:
			break;
		case R.id.txtRegister:
			Intent intent=new Intent(SearchActivity.this, RegisterTypeSelectionActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;

		default:
			break;
		}
	}

}//End Of Class
