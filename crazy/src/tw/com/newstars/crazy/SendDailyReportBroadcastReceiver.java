package tw.com.newstars.crazy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

public class SendDailyReportBroadcastReceiver extends BroadcastReceiver {
	SharedPreferences sp1;
	Editor editor1;
	SharedPreferences sp2;
	Editor editor2;
	
	private static final int FROM_NOTICE = 0;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp1 = context.getSharedPreferences("sp1", Context.MODE_PRIVATE);
		editor1 = sp1.edit();
		sp2 = context.getSharedPreferences("sp2", Context.MODE_PRIVATE);
		editor2 = sp2.edit();
		Log.d("debug","senddailyreport receiver receives.");
		if(intent.getAction()==OnBootReceiver.ACTION_SEND_DAILY_REPORT){
			Toast.makeText(context, "GOt it",Toast.LENGTH_LONG).show();
			sendNotify(context);
		}
	}
	
	public void sendNotify(Context context){
		MobileUsage mu = new MobileUsage(context);
		TimeFormat timeFormat = new TimeFormat(mu.getTodayUseMilliseconds());
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(context,MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context,FROM_NOTICE, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		Notification notice = new Notification.Builder(context)
        .setContentTitle("今天手機使用量")
        .setContentText("今天共使用了"+timeFormat.getShortFormat())
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentIntent(pIntent)
        .setAutoCancel(true)
        .build();
		int notifyid = sp1.getInt("notifyid", 0);
		notifyid++;
		editor1.putInt("notifyid", notifyid).commit();
		notificationManager.notify(notifyid, notice);
		
	}

}
