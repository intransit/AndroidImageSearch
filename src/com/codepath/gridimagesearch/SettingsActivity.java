package com.codepath.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends Activity {

	private EditText etSizeValue;
	private EditText etColorValue;
	private EditText etTypeValue;
	private EditText etSiteValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		etSizeValue = (EditText)findViewById(R.id.etSizeValue);
		etColorValue = (EditText)findViewById(R.id.etColorValue);
		etTypeValue = (EditText)findViewById(R.id.etTypeValue);
		etSiteValue = (EditText)findViewById(R.id.etSiteValue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	public void sendSearchFilters(View v){
		Intent data = new Intent();
		data.putExtra("size", etSizeValue.getText().toString());
		data.putExtra("color", etColorValue.getText().toString());
		data.putExtra("type", etTypeValue.getText().toString());
		data.putExtra("site", etSiteValue.getText().toString());
		
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); 
	}
}
