package com.murm.murmanskbus;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Build;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ActionBarActivity {
	
	public static MyJSONParser myJSONParser;
	public static BusList busList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		myJSONParser= new MyJSONParser(this);
		busList = new BusList();
		
		//listActivity.setListAdapter(new ArrayAdapter<String>(this, R.layout.bus_item, 
		//		getResources().getStringArray(R.array.buses)));
		final ListView lv = (ListView)findViewById(R.id.busListView);	
		
		lv.setAdapter(new ArrayAdapter<String>(this, R.layout.bus_item, busList.getRouteNamesArray())); 
				
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub					
				showPopupMenu(view, (int)id);				
			}});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showPopupMenu(View view, int routeIndex){
		PopupMenu popupMenu = new PopupMenu(this, view);
		popupMenu.getMenu().add(routeIndex,Constants.intDirectionRepr(Constants.Directon.FROM_NORTH),0,
				busList.getBus(routeIndex).endPts(Constants.Directon.FROM_NORTH));
		popupMenu.getMenu().add(routeIndex,Constants.intDirectionRepr(Constants.Directon.FROM_SOUTH),0,
				busList.getBus(routeIndex).endPts(Constants.Directon.FROM_SOUTH));
		
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(),
						//String.valueOf(item.getItemId()) + 
						//'+'  + String.valueOf(buses[item.getGroupId()]), Toast.LENGTH_SHORT).show();
				showStations(item.getGroupId(), Constants.DirectonDirRepr(item.getItemId()));

				return false;
			}});
		
		popupMenu.show();
		
		
	}
	private void showStations(int routeIndex, Constants.Directon direction){
		Intent intent = new Intent(this, StationsActivity.class);
		intent.putExtra("routeIndex", routeIndex);
		intent.putExtra("direction", direction);
		startActivity(intent);
	}
	
	
	

}
