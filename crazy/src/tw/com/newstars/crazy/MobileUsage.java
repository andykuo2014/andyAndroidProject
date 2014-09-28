package tw.com.newstars.crazy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class MobileUsage {
	SharedPreferences sp1;
	Editor editor1;
	SharedPreferences sp2;
	Editor editor2;
	
	public MobileUsage(Context context){
		sp1 = context.getSharedPreferences("sp1", context.MODE_PRIVATE);
		sp2 = context.getSharedPreferences("sp2", context.MODE_PRIVATE);
		editor1 = sp1.edit();
		editor2 = sp2.edit();
	}
	
	public long getTodayUseMilliseconds(){
		String todayStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
		long todayUsage = sp2.getLong(todayStr, 0);
		//also need to add the current usage after the last screenon.
		long lastScreenOn = sp1.getLong("lastscreenon",new Date().getTime()); 
		todayUsage+= new Date().getTime()-lastScreenOn;
		return todayUsage;
	}
	
	public long getYesterdayUseMilliseconds(){
		String yesterdayStr = new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime()-86400000));
		long yesterdayUsage = sp2.getLong(yesterdayStr,0);
		return yesterdayUsage;
	}
	
	public void dumpAllSharedPreference(){
		//for debug purpose
		Log.d("debug","sp1 dump");
		Map<String,?> sp1map = sp1.getAll();
		for(Map.Entry<String, ?> entry:sp1map.entrySet()){
			Log.d("debug","key:"+entry.getKey()+";"+"value:"+entry.getValue().toString());
		}
		Log.d("debug","sp2 dump");
		Map<String,?> sp2map = sp2.getAll();
		for(Map.Entry<String, ?> entry:sp2map.entrySet()){
			Log.d("debug","key:"+entry.getKey()+";"+"value:"+entry.getValue().toString());
		}
	}
	
}
