package com.codepath.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	private static final int REQUEST_CODE = 123;
	private EditText etQuery;
	private Button btnSearch;
	private GridView gvResults;
	private String filterSize="", filterColor="", filterType="", filterSite="";
	
	private ImageResultArrayAdapter imageAdapter;
	private ArrayList<ImageResults> imageResults = new ArrayList<ImageResults>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		
		//clicking an image should open it in a new activity as a full screen
		//therefore, attaching click handler
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResults imageResult = imageResults.get(position);  //position is known as part of the click -- the item thats clicked
				
				i.putExtra("url", imageResult.getFullUrl());
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	public void setupViews(){
		etQuery = (EditText)findViewById(R.id.etQuery);
		btnSearch = (Button)findViewById(R.id.btnSearch);
		gvResults = (GridView)findViewById(R.id.gvResults);
	}
	
	public void launchSettings(MenuItem m){
		Intent i = new Intent(getBaseContext(), SettingsActivity.class);
		//i.putExtra("name", curName);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
		     //Toast.makeText(this, data.getExtras().getString("site"),
		      //  Toast.LENGTH_SHORT).show();
		     filterSize = data.getExtras().getString("size");
		     filterColor = data.getExtras().getString("color");
		     filterType = data.getExtras().getString("type");
		     filterSite = data.getExtras().getString("site");
		     
		     //System.out.println("valuesssssss: " + filterSize + ", " + filterColor + ", " + filterType + ", " + filterSite);
		  }
	}
	
	public void onImageSearch(View v){
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
		
		AsyncHttpClient client = new AsyncHttpClient();
		
		//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android
		//Site filter: as_sitesearch
		//Color filter: imgcolor
		//Size filter: imgsz
		//Type filter: imgtype
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+0+"&v=1.0&q="+Uri.encode(query)+
				"&imgsz="+filterSize+"&imgcolor="+filterColor+"&imgtype="+filterType+"&as_sitesearch="+filterSite, 
				new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject reponse){
				JSONArray imageJsonResults = null;
				
				try{
					imageJsonResults = reponse.getJSONObject("responseData").getJSONArray("results");
					
					imageResults.clear();
					
					imageAdapter.addAll(ImageResults.fromJSONArray(imageJsonResults));
					//Or
					//imageResults.addAll(ImageResults.fromJSONArray(imageJsonResults));   +   imageAdapter.notify();
					
					Log.d("DEBUG", imageResults.toString());
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
