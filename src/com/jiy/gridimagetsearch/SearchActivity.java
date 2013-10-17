package com.jiy.gridimagetsearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class SearchActivity extends Activity {
	
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        setupViews();
        
        gvResults.setOnScrollListener(new EndlessScrollListener(){
    	    @Override
    	    public void loadMore(int page, int totalItemsCount) {
                 
    	    }
            });
        
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId){
        		Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
        		ImageResult imageResult = imageResults.get(position);
        		i.putExtra("url", imageResult.getFullUrl());
        		startActivity(i);
        	}
		});
    }
    
    public void setupViews(){
    	etQuery = (EditText)findViewById(R.id.etQuery);
    	btnSearch = (Button)findViewById(R.id.btnSearch);
    	gvResults = (GridView) findViewById(R.id.gvResults);
    }
    
    public void onImageSearch(View v){
    	String query = etQuery.getText().toString();
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+0+"&v=1.0&q="+Uri.encode(query), 
    			new JsonHttpResponseHandler(){
    		@Override
    		public void onSuccess(JSONObject response){
    			JSONArray imageJsonResults=null;
    			try{
    				imageJsonResults=response.getJSONObject("responseData").getJSONArray("results");
    				imageResults.clear();
    				imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    				Log.d("DEBUG", imageResults.toString());
    			}
    			catch(JSONException e){
    				e.printStackTrace();
    			}
    		}
    		
    	});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	int id =item.getItemId();
    	
    	if(id==R.id.action_settings){
    		String etQ=etQuery.getText().toString();
    		Intent intent = new Intent(this, SettingActivity.class);
    		intent.putExtra("etQ", etQ);
    		startActivityForResult(intent, 1);
    	}
    	
    	return true;
    }
    
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==1 && resultCode==RESULT_OK){
    		String etNameStr = data.getExtras().getString("etNameStr");
    		tv.setText(etNameStr);
    	}
    } */ 
    
}
