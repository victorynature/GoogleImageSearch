package com.jiy.gridimagetsearch;

import com.loopj.android.image.SmartImageView;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ImageDisplayActivity extends Activity {
	
	SmartImageView ivResult;
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		url=getIntent().getStringExtra("url");
		SmartImageView ivImage=(SmartImageView)findViewById(R.id.ivResult);
		ivImage.setImageUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}
	
	public void onEmail(View v){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("plain/text");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { });
		intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this image!");
		
		intent.putExtra(Intent.EXTRA_TEXT, url); 
		startActivity(Intent.createChooser(intent, ""));
	}

}
