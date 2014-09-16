package com.example.simpleui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	public EditText editText1;
	public Button button1;
	public CheckBox checkbox1;
	private SharedPreferences sp;
	private Editor e;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("setting", MODE_PRIVATE);
        e = sp.edit();
        setContentView(R.layout.activity_main);
        editText1 = (EditText) findViewById(R.id.editText1);
        button1 = (Button) findViewById(R.id.button1);
        checkbox1 = (CheckBox) findViewById(R.id.checkBox1);
        button1.setText("Send");
        
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Log.d("Debug","button 1");
				sendText();
			}
		});
        
        editText1.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				e.putString("text", editText1.getText().toString());
				e.commit();
				if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN ){
					sendText();
					editText1.setText("");
					
				}
				return false;
			}
		});
        
        checkbox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				e.putBoolean("check", isChecked);
				e.commit();
			}
		});
        editText1.setText(sp.getString("text", ""));
        checkbox1.setChecked(sp.getBoolean("check",false));
  
    }

    public void button2Click(View view){
    	Log.d("Debug","button 2");
    	sendText();
    }
    
    public void sendText(){
    	String text = editText1.getText().toString();
    	if(checkbox1.isChecked()){
    		text = "*******";
    		
    	}
    	Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    	Intent intent = new Intent();
    	intent.setClass(this, MessageActivity.class);
    	intent.putExtra("text", text);
    	startActivity(intent);
    	
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
