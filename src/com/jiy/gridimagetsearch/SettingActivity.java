package com.jiy.gridimagetsearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends Activity {
	
	Spinner image_size;
	Spinner color_filter;
	Spinner image_type;
	EditText site_filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		image_size = (Spinner)findViewById(R.id.spinner1);
		color_filter = (Spinner)findViewById(R.id.spinner2);
		image_type = (Spinner)findViewById(R.id.spinner3);
		site_filter = (EditText)findViewById(R.id.editText1);
	}
	
	public void onSave(View v){
		String img_size_val = image_size.getSelectedItem().toString();
		String col_filter_val = color_filter.getSelectedItem().toString();
		String img_type_val = image_type.getSelectedItem().toString();
		String site_filter_val = site_filter.getText().toString();
		
		Intent resultIntent = new Intent();
		resultIntent.putExtra("image_size",  img_size_val);
		resultIntent.putExtra("color_filter",  col_filter_val);
		resultIntent.putExtra("image_type",  img_type_val);
		resultIntent.putExtra("site_filter",  site_filter_val);
		setResult(RESULT_OK, resultIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

}
