package com.example.satchat;

import java.util.ArrayList;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class IndividualChatDisplay extends Activity {

	 static  IndividualChatAdapter  mAdapter;
	static ArrayList<IndividualChatWrapper> IndChatList,IndividualChatList;
     TextView individual;
     static ListView indlistv;
     static Context context;
     ImageButton back,sendBtn;
     EditText message;
   static  String	 number;
     String	 name;
    // SmsReceiver myReceiver;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_chat_display);
		
		
		context=this;
		
		Bundle bundle = getIntent().getExtras();
	    	 number = bundle.getString("monumber");
	    	 name = bundle.getString("name");
	    
	    if(name.equals("Unknown Person")){
	    	name=number;
	    }
	    
	    
	    
	    back=(ImageButton)findViewById(R.id.backbutton);
	    sendBtn=(ImageButton)findViewById(R.id.sendIt);
	    indlistv=(ListView)findViewById(R.id.listView1);
	    message=(EditText)findViewById(R.id.msg);
	    individual=(TextView)findViewById(R.id.indname);
	    individual.setText(name);
	    
	    
	    
	    back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(getApplicationContext(),MainActivity.class);
				 startActivity(intent);
				  finish();
				
			}
		});
		
	    
	    sendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Toast.makeText(getApplicationContext(),"sattar :"+number, Toast.LENGTH_LONG).show();
				
				String msg=message.getText().toString();
				
				
			if(number!=null){	
				//new NewSms().sendSms(context, number, msg); 
				
			}
			
			}
		});
	    
	    
	    
	    
	    
	    indlistv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder alert= new AlertDialog.Builder(context);
				
				alert.setTitle("Alert!");
				alert.setMessage("Are you sure to delete chat?");
				
						final String idofs= IndividualChatList.get(position).get_id();
						final String num=IndividualChatList.get(position).get_number();
				
				 alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

		                @Override
		                public void onClick(DialogInterface dialog, int which) {
		                                        
		                	
		             Integer noSMS=new DataBaseForChat(context).deleteSingleSms(new DataBaseForChat(context), idofs,num);
		                if(noSMS==1){
		                	
		                	Intent intent=new Intent(IndividualChatDisplay.this,MainActivity.class);
		    				startActivity(intent);
		    				finish();	
		                }else{
		                	update();
		                }
		                	
		                    dialog.dismiss();
		                    
		                   

		                }
		            });
		            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

		                @Override
		                public void onClick(DialogInterface dialog, int which) {

		                    dialog.dismiss();
		                }
		            });

		            alert.show();
				            
				
				return true;
			}
	    	
	    	
	    	
		});
	  
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    IndChatList=individualComMsgList();
		
	    setIndividualChatAdapter(IndChatList);
	    
	}
	 
	 
 static  ArrayList<IndividualChatWrapper> individualComMsgList(){
	    
	    
	 DataBaseForChat object=  new DataBaseForChat(context);	 
      
      
      Cursor cursor = object.getIndividualMsgs(object, number);
      
      IndividualChatList=new ArrayList<IndividualChatWrapper>();   
      
    if(cursor!=null){
      
      cursor.moveToFirst();
      
      
      do{
	   
    	  IndividualChatWrapper indMsginstance=new IndividualChatWrapper();     
		
      
      
      
      indMsginstance.set_id(cursor.getString(0));
        
      indMsginstance.set_name(cursor.getString(1));
		 
      indMsginstance.set_number(cursor.getString(2));
		
		 
		
      indMsginstance.set_date(cursor.getString(3));
		
      indMsginstance.set_time(cursor.getString(4));
		
      indMsginstance.set_content(cursor.getString(5));
		
      indMsginstance.set_type(cursor.getString(6));
		
      IndividualChatList.add(indMsginstance);
						
			//Toast.makeText(getApplicationContext(), csr.getString(0), Toast.LENGTH_LONG).show();
		    //Toast.makeText(getApplicationContext(),csr.getString(2), Toast.LENGTH_LONG).show(); 
	        //Toast.makeText(getApplicationContext(),String.valueOf(noofSms), Toast.LENGTH_LONG).show(); 
	  
	  
  }while(cursor.moveToNext());
	  
	 	  cursor.close();
	 
	 	     
    } 
    
        return  IndividualChatList;
    
	}

	  
	
	
static	private void setIndividualChatAdapter(ArrayList<IndividualChatWrapper> IndividualMsgList) {
		// TODO Auto-generated method stub
		mAdapter = new IndividualChatAdapter(context, IndividualMsgList);
		indlistv.setAdapter(mAdapter);
		 indlistv.setSelection(indlistv.getAdapter().getCount()-1);
	}
	
		
	
	
	
	
	  static void update(){
		  
		 	try{  
			
		 		IndividualChatList.clear();
		  ArrayList<IndividualChatWrapper> newList=individualComMsgList();
		     
			// Toast.makeText(context, "size of original list"+IndividualMsgList.size(), Toast.LENGTH_LONG).show();
		  
			 setIndividualChatAdapter(IndividualChatList);
			 
	 
			// mAdapter. notifyDataSetChanged();
			 		
		}catch(Exception e){
			
			//Toast.makeText(context, "error in update", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	  }
	
	
	
	  
	
	
	
}
