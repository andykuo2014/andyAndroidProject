package tw.com.newstars.crazy;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends Activity {

	private ScreenDetectBroadcastReceiver broadcastReceiver;
	
	SharedPreferences sp1;
	SharedPreferences sp2;
	Editor editor1;
	Editor editor2;
	Button button;
	TextView textView1; //the usage text.
	SwipeRefreshLayout swipeContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		broadcastReceiver = new ScreenDetectBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(broadcastReceiver, intentFilter);
		sp1 = getSharedPreferences("sp1", this.MODE_PRIVATE);
		sp2 = getSharedPreferences("sp2", this.MODE_PRIVATE);
		editor1 = sp1.edit();
		editor2 = sp2.edit();
		textView1 = (TextView) findViewById(R.id.textView1);
		button = (Button)findViewById(R.id.button1);
		swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
		swipeContainer.setColorSchemeResources (android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
		
		swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Log.d("debug","on refresh");
				updateUsage();
				swipeContainer.setRefreshing(false);
				
			}
		});
		
			
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editor1.clear().commit();
				editor2.clear().commit();
			}
		});	
	}
	
	protected void onResume(){
		super.onResume();
		updateUsage();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void updateUsage(){
		displayUsage(getTodayUseMilliseconds());
	
	}
	public long getTodayUseMilliseconds(){
		String todayStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		long todayUsage = sp2.getLong(todayStr, 0);
		//also need to add the current usage after the last screenon.
		long lastScreenOn = sp1.getLong("lastscreenon",new Date().getTime()); 
		todayUsage+= new Date().getTime()-lastScreenOn;
		return todayUsage;
	}
	
	public void displayUsage(long usage){
		//display usage on the screen.
		textView1.setText(""+ (int)(usage/1000));
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
}
