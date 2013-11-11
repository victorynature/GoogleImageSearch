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
	String image_size;
	String color_filter;
	String image_type;
	String site_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        setupViews();
        
        gvResults.setOnScrollListener(new EndlessScrollListener(){
    	    @Override
    	    public void loadMore(int page, int totalItemsCount) {
                 queryImage(totalItemsCount);
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
    	queryImage(0);
    }
    public void queryImage(int start){
    	String query = etQuery.getText().toString();
    	
    	AsyncHttpClient client = new AsyncHttpClient();
    	String baseUrl="https://ajax.googleapis.com/ajax/services/search/images?rsz=8&";
    	if(image_size!="blank"){
    		baseUrl+="imgsz="+image_size+"&";
    	}
    	if(color_filter!="blank"){
    		baseUrl+="imgcolor="+color_filter+"&";
    	}
    	if(image_type!="blank"){
    		baseUrl+="imgtype="+image_type+"&";
    	}
    	
    	if(site_filter!=null){
    		baseUrl+="as_sitesearch="+site_filter+"&";
    	}
    	
    	baseUrl+="start="+start+"&v=1.0&q="+Uri.encode(query);
    	if(start==0){
    		imageResults.clear();
    	}
    	client.get(baseUrl,
    			new JsonHttpResponseHandler(){
    		@Override
    		public void onSuccess(JSONObject response){
    			JSONArray imageJsonResults=null;
    			try{
    				imageJsonResults=response.getJSONObject("responseData").getJSONArray("results");
    				
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
    		intent.putExtra("image_size",  image_size);
    		intent.putExtra("color_filter",  color_filter);
    		intent.putExtra("image_type",  image_type);
    		intent.putExtra("site_filter",  site_filter);
    		startActivityForResult(intent, 1);
    	}
    	
    	return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==1 && resultCode==RESULT_OK){
    		image_size = data.getExtras().getString("image_size");
    		color_filter = data.getExtras().getString("color_filter");
    		image_type = data.getExtras().getString("image_type");
    		site_filter = data.getExtras().getString("site_filter");
    		
    	}
    } 
    
}
