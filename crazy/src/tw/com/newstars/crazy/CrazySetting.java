package tw.com.newstars.crazy;

import java.util.Date;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class CrazySetting extends Activity {
	private static final int FROM_NOTICE = 0;
	Button buttonConfirmNotice;
	Button buttonCancelNotice;
	Button resetButton;
	SharedPreferences sp1;
	Editor editor1;
	SharedPreferences sp2;
	Editor editor2;
	CheckBox checkNotice;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		sp1 = getSharedPreferences("sp1", this.MODE_PRIVATE);
		editor1 = sp1.edit();
		sp2 = getSharedPreferences("sp2", this.MODE_PRIVATE);
		editor2 = sp2.edit();
		checkNotice = (CheckBox) findViewById(R.id.checkNotice);
		buttonConfirmNotice = (Button) findViewById(R.id.buttonConfirmNotice);
		buttonCancelNotice = (Button) findViewById(R.id.buttonCancelNotice);
		resetButton = (Button) findViewById(R.id.resetButton);
		buttonConfirmNotice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editor1.putBoolean("notice",checkNotice.isChecked());
				editor1.commit();
				Log.d("debug","button clicked");
				sendNotify();
			}
		});
		
		buttonCancelNotice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(v.getContext(), "��ƥ��x�s", Toast.LENGTH_SHORT).show();
				Intent intentToMain = new Intent(v.getContext(),MainActivity.class);
				startActivity(intentToMain);
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// clear s1,s2.
				editor1.clear().commit();
				editor2.clear().commit();
				Toast.makeText(v.getContext(), "��Ƥw�M��", Toast.LENGTH_SHORT).show();
				Intent intentToMain = new Intent(v.getContext(),MainActivity.class);
				startActivity(intentToMain);
			}
		});
	}
	
	public void sendNotify(){
		
		NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
		Intent intent = new Intent(this,MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, FROM_NOTICE, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		Notification notice = new Notification.Builder(this)
        .setContentTitle("�A�Τ���Ӥ[�A�ӥ𮧤F")
        .setContentText("�A�Τ���Ӥ[�A�u���u���ӥ𮧤F")
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