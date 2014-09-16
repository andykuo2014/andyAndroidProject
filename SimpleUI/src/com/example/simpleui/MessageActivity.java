package com.example.simpleui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MessageActivity extends Activity {
	public TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("debug","messageactivity!!!");
		
		setContentView(R.layout.activity_message);
		
		textView = (TextView) findViewById(R.id.textView1);
		String text = getIntent().getStringExtra("text");
		writeFile(text);
		textView.setText(readFile());
		
		
	}
	
	private void writeFile(String text){
		text += "\n";
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
			return new String(bytes);
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



