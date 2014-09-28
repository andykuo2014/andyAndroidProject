package tw.com.newstars.crazy;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {

	public static final String ACTION_SEND_DAILY_REPORT = "tw.com.newstars.crazy.ACTION_SEND_DAILY_REPORT";
	public static final String ACTION_HANDLE_EVENTS = "tw.com.newstars.crazy.ACTION_HANDLE_EVENTS";
	public static final int REQUESTCODE_FROM_ONBOOTRECEIVER=1; 
	SharedPreferences sp1;
	Editor editor1;
	SharedPreferences sp2;
	Editor editor2;
	AlarmManager am;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("debug","OnBoot receive");
		Intent startIntent = new Intent(context,OnBootService.class);
		Log.d("debug","before start service");
		context.startService(startIntent);
		Log.d("debug","after service started");
		setAlarmForDailyReport(context);
		Log.d("debug","after alarm set");
		sp1 = context.getSharedPreferences("sp1", Context.MODE_PRIVATE);
		sp2 = context.getSharedPreferences("sp2", Context.MODE_PRIVATE);
		editor1 = sp1.edit();
		editor2 = sp2.edit();
		editor1.putBoolean("alreadyreboot", true);
		editor1.commit();
	}
	
	private void setAlarmForDailyReport(Context context){
		//set the alarm
		//send usage notification every day at 9pm.
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 21);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,0);
		Intent sendDailyReportIntent = new Intent(context,SendDailyReportBroadcastReceiver.class);
		sendDailyReportIntent.setAction(OnBootReceiver.ACTION_SEND_DAILY_REPORT);
		PendingIntent pi = PendingIntent.getBroadcast(context,REQUESTCODE_FROM_ONBOOTRECEIVER,sendDailyReportIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),86400*1000,pi);	
	}

}
