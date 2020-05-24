package com.freetravelapp;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.freetravelapp.common.CustomDialog;

public class Utills {
	// All common object declaration part here
	public static Context c;

	static String response;
	static String method = "POST";
	public static Dialog m_dilog;

	static SharedPreferences arrayPref = null;
	static Editor arrayeditor;

	static boolean isReslutSet = false;

	// Test URL
	static Context me;

	/**
	 * Connection check internet is currently on or off
	 * 
	 * @param context
	 *            this method is call then context name is here written
	 * @return
	 */

	public static boolean checkConnectivity(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info == null) {
				return false;
			} else if (info.isConnectedOrConnecting()) {
				return true;
			}
		} catch (Exception e) {
			Log.e("Error in checkConnectivity() in OfferNotificationService", e.toString());
		}
		return false;
	}// End of checkConnectivity



	// Showing Alert Dialog
	public static void AlertDialog(String Message, Context c) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
		alertDialogBuilder.setTitle(c.getResources().getString(R.string.app_name));
		alertDialogBuilder.setMessage(Message).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		alertDialogBuilder.show();
	}// End of AlertDialog()



	/**
	 * It shows Dialog containing array of priorities. It also handles
	 * OnClickListener for the selected item from the Dailog.
	 */
	public static void showOneButtonDialog(Context c, String msg) {
		final Dialog dialog = new Dialog(c, R.style.customDialog);
		dialog.setContentView(R.layout.custom_alert_daliog_one);


		Window window = dialog.getWindow();
		window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);

		TextView txt_alert = (TextView) dialog.findViewById(R.id.txt_alert);
		txt_alert.setText(msg);

		Button btnYes = (Button) dialog.findViewById(R.id.btn_Yes);
		btnYes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
		WindowManager.LayoutParams windowManager = ((Activity) c).getWindow().getAttributes();
		windowManager.dimAmount = 0.2f;
		((Activity) c).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	/**
	 * It shows Dialog containing array of priorities. It also handles
	 * OnClickListener for the selected item from the Dailog.
	 */
	public static void showAlertDialog(Context c, String msg) {
		final Dialog dialog = new Dialog(c, R.style.customDialog);
		dialog.setContentView(R.layout.custom_alert_daliog);

		Window window = dialog.getWindow();
		window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(false);

		TextView txt_alert = (TextView) dialog.findViewById(R.id.txt_alert);
		txt_alert.setText(msg);

		Button btnYes = (Button) dialog.findViewById(R.id.btn_Yes);
		Button btnNo = (Button) dialog.findViewById(R.id.btn_No);

		btnYes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		btnNo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
		WindowManager.LayoutParams windowManager = ((Activity) c).getWindow().getAttributes();
		windowManager.dimAmount = 0.2f;
		((Activity) c).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}



	/*
	 * Sets the font on all TextViews in the ViewGroup. Searches recursively for
	 * all inner ViewGroups as well. Just add a check for any other views you
	 * want to set as well (EditText, etc.)
	 */
	public static void setFont(Context c,ViewGroup group) {

		// Sets fonts for all
		Typeface font = Typeface.createFromAsset(c.getAssets(), "opensans_regular.ttf");

		int count = group.getChildCount();
		View v;
		for (int i = 0; i < count; i++) {
			v = group.getChildAt(i);
			if (v instanceof TextView || v instanceof EditText || v instanceof Button) {
				((TextView) v).setTypeface(font);
			} else if (v instanceof ViewGroup)
				setFont(c,(ViewGroup) v);
		}
	}

	/**
	 * Hides the soft keyboard
	 */
	public static void hideSoftKeyboard(Activity me) {
		if (me.getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) me.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(me.getCurrentFocus().getWindowToken(), 0);
		}
	}



	public static void showMessage(final Context c, final String title, final String message, final View view) {
		try {
			((Activity) c).runOnUiThread(new Runnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {

					AlertDialog.Builder builder = new Builder(c);
					AlertDialog alertDialog = builder.create();
					if (!title.equals("")) {
						alertDialog.setTitle(title);
					}
					alertDialog.setMessage(message);
					alertDialog.setCancelable(false);
					alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (view != null) {
								view.requestFocus();
							}
						}
					});
					alertDialog.show();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}// End of Method showMessage


	/**
	 * This common dialog is call the web services the progressDialog is open
	 * 
	 * @param c
	 *            is particular context pass here
	 * @param m1
	 *            is string value get and set the TextView
	 */
	public static void showProgressDialog(Context c, String m1) {

		try {

			m_dilog.dismiss();

		} catch (Exception e) {
			// TODO: handle exception
		}

		m_dilog = new Dialog(c);
		m_dilog.setCancelable(false);
		m_dilog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		m_dilog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		m_dilog.setContentView(R.layout.custom_progress_dialog);

		//		ViewGroup root1 = (ViewGroup) m_dilog.findViewById(R.id.custom_diloge);
		//		Utills.setFont(c,root1);


		// // Sets fonts for all
		// Typeface mFont = Typeface.createFromAsset(c.getAssets(),
		// "fonts/ALLER_RG.TTF");
		// ViewGroup root = (ViewGroup)
		// m_dilog.findViewById(R.id.custom_diloge);
		// Utills.setFont(root, mFont);

		TextView txt_alert_message = (TextView) m_dilog.findViewById(R.id.txt_alert_message);
		m_dilog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		txt_alert_message.setText(m1);
		m_dilog.show();

	}// custom progressbar popup end

	/**
	 * This common dialog is call the web services success full call then
	 * progressDialog is close
	 */
	public static void dismissDialog() {
		try {
			if (m_dilog != null && m_dilog.isShowing()) {
				m_dilog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static String getTimeZone(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getString("timezone", "Asia/Kuwait");
	}
	
	
	/**
	 * This common dialog is call the web services 
	 * success full call then progressDialog is close
	 */
	public static void CustomLDilogeYes(Activity act, String strTitle, String strPostive, String strContent) {
		// Create the builder with required paramaters - Context, Title, Positive Text
		CustomDialog.Builder builder = new CustomDialog.Builder(act, strTitle, strPostive);
		// Now we can any of the following methods.
		builder.content(strContent);
		//  builder.negativeText(strNagative);
		//builder.darkTheme(boolean isDark);
		//builder.typeface(Typeface typeface);
		builder.titleTextSize(24);
		builder.contentTextSize(18);
		builder.buttonTextSize(15);
		//builder.titleAlignment(Alignment alignment); // Use either Alignment.LEFT, Alignment.CENTER or Alignment.RIGHT
		builder.titleColor("#09b7fc"); // int res, or int colorRes parameter versions available as well.
		builder.contentColor("#000000"); // int res, or int colorRes parameter versions available as well.
		builder.positiveColor("#000000"); // int res, or int colorRes parameter versions available as well.
		//           builder.negativeColor(String hex); // int res, or int colorRes parameter versions available as well.
		//           builder.positiveBackground(Drawable drawable); // int res parameter version also available.
		//           builder.rightToLeft(boolean rightToLeft); // Enables right to left positioning for languages that may require so.
		// Now we can build the dialog.
		CustomDialog customDialog = builder.build();
		// Show the dialog.
		customDialog.show();

		//  customDialog.setClickListener(new CustomDialog.ClickListener() {
		//   @Override
		//   public void onConfirmClick() {
		//
		//   }
		//
		//   @Override
		//   public void onCancelClick() {
		//
		//   }
		//  });
	}



}