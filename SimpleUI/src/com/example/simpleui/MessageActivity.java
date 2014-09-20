package com.example.simpleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MessageActivity extends Activity {
	public ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("debug","messageactivity!!!");
		
		setContentView(R.layout.activity_message);
		
		listView = (ListView) findViewById(R.id.listView1);
		String text = getIntent().getStringExtra("text");
		String checked = getIntent().getStringExtra("checked");
		Log.d("debug",checked.toString());
		writeFile(text,checked);
		String filestr = readFile();
		String[] strArr = filestr.split("\n");
		ArrayList al = new ArrayList();
		
		for(int i=0;i<strArr.length;i++){
			String[] elemArr = strArr[i].split(",");
			if(elemArr.length==2){
				Map<String,String> map = new HashMap<String,String>();
				map.put("text", elemArr[0]);
				map.put("checked",elemArr[1]);
				al.add(map);
			}
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this, al, android.R.layout.simple_list_item_2, new String[]{"text","checked"} , new int[]{android.R.id.text1,android.R.id.text2});
		//ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strArr);
		listView.setAdapter(adapter);
	}
	
	private void writeFile(String text,String checked){
		text = text + "," + checked + "\n";
		try {
			FileOutputStream fos = openFileOutput("history.txt",Context.MODE_APPEND);
			fos.write(text.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private String readFile(){
		try {
			FileInputStream fis = openFileInput("history.txt");
			byte[] bytes = new byte[1024];
			fis.read(bytes);
			String res = new String(bytes);
			Log.d("debug",res.toString());
			return res;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}



