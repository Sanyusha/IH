package com.example.piemenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {//implements OnLongClickListener {

	// update
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RelativeLayout ly =(RelativeLayout) findViewById(R.id.main_layout);


		//ly.setOnLongClickListener(this);
		
		ly.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// Toast.makeText(getApplicationContext(), "Long click!", Toast.LENGTH_SHORT).show();
				//		    	final Dialog dialog = new Dialog(getApplicationContext());
				//				dialog.setContentView(R.layout.dlg_yes_no);
				//dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
				//		LayoutParams.WRAP_CONTENT);
				//dialog.show();
				//FragmentManager fm = MainActivity.getSupportFragmentManager();
				datePickerFragment dialog = new datePickerFragment();
				//dialog.show(getApplicationContext()); 


				return true;
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	//	@Override
	//	public boolean onLongClick(View arg0) {
	//		Toast.makeText(this, "bla" , Toast.LENGTH_LONG).show();
	//		return true;
	//	}



}
