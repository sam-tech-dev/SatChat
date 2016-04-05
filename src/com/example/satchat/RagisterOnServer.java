package com.example.satchat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RagisterOnServer extends Activity {

	EditText mono,password;
	Button register;
	String  query;
	Context context;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ragister_on_server);
		
		
		mono=(EditText)findViewById(R.id.mobile);
		password=(EditText)findViewById(R.id.passw);
		register=(Button)findViewById(R.id.registration);
				
		context=this;
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String number=mono.getText().toString();
				String psw=password.getText().toString();
				
				//Toast.makeText(context,"in Ragister "+number+" "+psw,Toast.LENGTH_LONG).show();
				
		         new connectionAsync(number,psw).execute();
				
		         Toast.makeText(context,"You Have Connected",Toast.LENGTH_LONG).show();
		         
				//Intent intent=new Intent(getApplicationContext(), MainActivity.class);
				//startActivity(intent);
				//finish();
				
			}
		});
		
		
	}

	
	
	
	
		 
		 
		 
		 
		 
		 
		 
		 
	 
	
	
	
	
}
