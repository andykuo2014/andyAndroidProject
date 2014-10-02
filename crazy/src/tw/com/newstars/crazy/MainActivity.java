package tw.com.newstars.crazy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends Activity {

	private ScreenDetectBroadcastReceiver broadcastReceiver;
	private UiLifecycleHelper uiHelper;
	SharedPreferences sp1;
	SharedPreferences sp2;
	Editor editor1;
	Editor editor2;
	CheckBox checkNotice;
	ImageView shareImageView; //the share button img.
	TextView textView1; //the today usage text.
	TextView textView3; //the yesterday usage text.
	SwipeRefreshLayout swipeContainer;
	Handler handler;
	Runnable runnable;
	long todayUseMilliseconds;
	long yesterdayUseMilliseconds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sp1 = getSharedPreferences("sp1", this.MODE_PRIVATE);
		sp2 = getSharedPreferences("sp2", this.MODE_PRIVATE);
		editor1 = sp1.edit();
		editor2 = sp2.edit();
		textView1 = (TextView) findViewById(R.id.textView1);
		textView3 = (TextView) findViewById(R.id.textView3);
		shareImageView = (ImageView)findViewById(R.id.shareImageView);
		
		swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
		swipeContainer.setColorSchemeResources (android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
		
		swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Log.d("debug","on refresh");
				updateUsage(MainActivity.this);
				swipeContainer.setRefreshing(false);
				
			}
		});
		
			
		shareImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//FB share here.
				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(MainActivity.this)
		        .setLink("https://developers.facebook.com/android")
		        .build();
				uiHelper.trackPendingDialogCall(shareDialog.present());
			}
		});	
		
		//see if sp1 has lastscreenon key, if not, set it.
		if(sp1.getLong("lastscreenon", 0)==0){
			editor1.putLong("lastscreenon", new Date().getTime());
			editor1.commit();
		}
		MobileUsage mu = new MobileUsage(this);
		mu.dumpAllSharedPreference();
		if(!sp1.getBoolean("alreadyreboot", false)){
			//events haven't been handled by reboot. handle them now.
			Intent i = new Intent(OnBootReceiver.ACTION_HANDLE_EVENTS);
			sendBroadcast(i);
		}
		
		//printKeyHash(this);
		
		uiHelper = new UiLifecycleHelper(this, null);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	protected void onResume(){
		super.onResume();
		uiHelper.onDestroy();
		updateUsage(this);
	}
	
	protected void onDestroy(){
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	} 

	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void updateUsage(Context context){
		MobileUsage mu = new MobileUsage(context);
		todayUseMilliseconds =mu.getTodayUseMilliseconds();
		yesterdayUseMilliseconds = mu.getYesterdayUseMilliseconds();
		Log.d("debug","yesterday usage:"+yesterdayUseMilliseconds);
		displayUsage(todayUseMilliseconds,yesterdayUseMilliseconds);
		if(handler==null){
			handler = new Handler();
			runnable = new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					todayUseMilliseconds+=1000;
					displayUsage(todayUseMilliseconds,yesterdayUseMilliseconds);
					handler.postDelayed(this, 1000);
				}
			};
			handler.postDelayed(runnable, 1000);
		}
	}
	
	public void displayUsage(long todayusage,long yesterdayusage){
		//display usage on the screen.
		TimeFormat todayTimeFormat = new TimeFormat(todayusage);
		TimeFormat yesterdayTimeFormat = new TimeFormat(yesterdayusage);
		textView1.setText(todayTimeFormat.getLongFormat());
		textView3.setText("¬Q¤Ñ¥Î¶q"+yesterdayTimeFormat.getShortFormat());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this,CrazySetting.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static String printKeyHash(Activity context) {
		PackageInfo packageInfo;
		String key = null;
		try {

			//getting application package name, as defined in manifest
			String packageName = context.getApplicationContext().getPackageName();

			//Retriving package info
			packageInfo = context.getPackageManager().getPackageInfo(packageName,
					PackageManager.GET_SIGNATURES);
			
			Log.e("Package Name=", context.getApplicationContext().getPackageName());
			
			for (Signature signature : packageInfo.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				key = new String(Base64.encode(md.digest(), 0));
			
				// String key = new String(Base64.encodeBytes(md.digest()));
				Log.d("debug","Key Hash="+key);

			}
		} catch (NameNotFoundException e1) {
			Log.e("Name not found", e1.toString());
		}

		catch (NoSuchAlgorithmException e) {
			Log.e("No such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}

		return key;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
	}
}
