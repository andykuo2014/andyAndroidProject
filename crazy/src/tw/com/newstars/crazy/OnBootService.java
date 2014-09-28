package tw.com.newstars.crazy;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class OnBootService extends Service {
	
	BroadcastReceiver broadcastReceiver;
	AlarmManager am;
	public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
		Log.d("debug","service is up.");
		broadcastReceiver = new ScreenDetectBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(broadcastReceiver, intentFilter);
		
		
		return Service.START_NOT_STICKY;
	}
	  
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
