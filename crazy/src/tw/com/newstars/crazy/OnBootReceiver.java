package tw.com.newstars.crazy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("debug","OnBoot receive");
		Intent startIntent = new Intent(context,OnBootService.class);
		context.startService(startIntent);
		
		
	}

}
