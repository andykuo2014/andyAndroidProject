package tw.com.newstars.crazy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.CalendarView;

public class ScreenDetectBroadcastReceiver extends BroadcastReceiver {
	
	SharedPreferences sp1;
	SharedPreferences sp2;
	Editor editor1;
	Editor editor2;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp1 = context.getSharedPreferences("sp1", context.MODE_PRIVATE);
		sp2 = context.getSharedPreferences("sp2", context.MODE_PRIVATE);
		editor1 = sp1.edit();
		editor2 = sp2.edit();
		if(intent.getAction()==Intent.ACTION_SCREEN_ON){
			editor1.putLong("lastscreenon", new Date().getTime());
			editor1.commit();
		}else if(intent.getAction()==Intent.ACTION_SCREEN_OFF){
			long lastscreenon = sp1.getLong("lastscreenon", 0);
			long currenttime = new Date().getTime();
			long duration = currenttime - lastscreenon;
			Log.d("debug","Duration:"+duration+" millisecs");
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String keystr = simpleDateFormat.format(lastscreenon);
			long useMilliSeconds = sp2.getLong(keystr,(long)0);
			useMilliSeconds+=duration;
			editor2.putLong(keystr, useMilliSeconds);
			editor2.commit();
			Log.d("debug","now useMIlliSeconds for "+keystr+" becomes "+useMilliSeconds);
			//there is a problem that calendarOff and calendarStart may be on different days.
			//ignore this problem for now.
			
			
			
		}

	}

}
