package com.example.simpleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MessageActivity extends Activity {
	public ListView listView;
	public ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_message);
		
		listView = (ListView) findViewById(R.id.listView1);
		String text = getIntent().getStringExtra("text");
		String checked = getIntent().getStringExtra("checked");
		
		//writeFile(text,checked);
		writeToParse(text,checked);
		pd = new ProgressDialog(this);
		pd.setTitle("loading.........");
		pd.show();
	}
	
	private void updateListView(List<ParseObject> elemList){
		ArrayList al = new ArrayList();
		for(int i=0;i<elemList.size();i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("text", elemList.get(i).getString("text"));
			map.put("checked", ""+elemList.get(i).getString("checked"));
			al.add(map);
    	}
		SimpleAdapter adapter = new SimpleAdapter(this, al, android.R.layout.simple_list_item_2, new String[]{"text","checked"} , new int[]{android.R.id.text1,android.R.id.text2});
		//ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strArr);
		listView.setAdapter(adapter);
		
	}
	private void loadFromParse(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> elemList, ParseException e) {
		    	updateListView(elemList);
		    	pd.dismiss();
		    }
		});
		
	}
	
	private void writeToParse(String text,String checked){
		ParseObject testObject = new ParseObject("TestObject");
        testObject.put("text", text);
        testObject.put("checked", checked);
        testObject.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				loadFromParse();
			}
		});
		
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



