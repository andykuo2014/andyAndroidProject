package com.example.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ListView listView;
	String[] urlArr = {"www.test1.com","www.test2.com","www.test3.com","www.test4.com"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		ArrayList<Map<String,String>> al = new ArrayList<Map<String,String>>();
		String[] textArr = {"img1","img2","img3","img4"};
		int[] imgArr = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4};
		for(int i=0;i<textArr.length;i++){
			Map<String,String> m = new HashMap<String,String>();
			m.put("text", textArr[i]);
			m.put("img", new Integer(imgArr[i]).toString());
			al.add(m);
		}
		String[] from = {"text","img"};
		int[] to =  {R.id.textView1,R.id.imageView1};
		SimpleAdapter adapter = new SimpleAdapter(this, al, R.layout.my_simple_list_item, from, to);
		
		
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, urlArr[arg2],Toast.LENGTH_LONG ).show();
				
			}
			
		});
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
}
