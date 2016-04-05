package com.example.satchat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import com.example.satchat.R.drawable;
import com.example.satchat.FrontAllChatListWrapper;
import com.example.satchat.AllChatListAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification.Action;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.YuvImage;
import android.graphics.drawable.Drawable;

public class MainActivity extends Activity {

	
    static  ArrayList<FrontAllChatListWrapper> ChatList,allChatList,dummyList;
	static  public   AllChatListAdapter mAdapter;
	static  Context context;
	static  ListView lstv;
	// SmsReceiver myReceiver;
	static DataBaseForChat obj;
	static Cursor crr;
	static int checkedCount=0; 
	 
	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		 lstv=(ListView)findViewById(R.id.listView);
	//	ImageButton newsms= (ImageButton)findViewById(R.id.newm);
		
		dummyList=new ArrayList<FrontAllChatListWrapper>();
		/*
       newsms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(MainActivity.this,NewSms.class);
				startActivity(intent);
				finish();
				
			}
		});
		*/
		
          lstv.setLongClickable(true);
		 lstv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		 
		 
		 lstv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				view.setSelected(true);
				return true;
			}
		});
		 
		 
		 
		 lstv.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				view.setSelected(true);
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lstv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
		
				Intent intent = new Intent(getApplicationContext() , IndividualChatDisplay.class);
			
			   intent.putExtra("monumber", allChatList.get(position).get_number());
			   intent.putExtra("name",allChatList.get(position).get_name());
			
				startActivity(intent);
			
				
			}});
		
		
		lstv.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				//mAdapter.removeSelection();
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				
				mode.getMenuInflater().inflate(R.menu.main, menu);
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub
				
				// Toast.makeText(context,"wtf1", Toast.LENGTH_LONG).show();
		
				 
				switch (item.getItemId()) {
				case R.id.delete:
					
					for(FrontAllChatListWrapper obje:dummyList){
						
						mAdapter.remove(obje);
						
					}
					
					checkedCount=0;
					
					mode.finish();
					return true;
		
				default:
					return false;
				}
			
				
				
			}
			
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position,
					long id, boolean checked) {
				// TODO Auto-generated method stub
				
				//checkedCount =lstv.getCheckedItemCount();
				checkedCount++;
				
				mode.setTitle(checkedCount + " Selected");
			
				
			dummyList.add(allChatList.get(position));
				
				
				//mAdapter.toggleSelection(position);
				
				
			}
		});
		
		
		
		
       context=this;
		
       obj=new DataBaseForChat(context);
	
	
	
	  crr=obj.getDatatodisplay(obj);
	 
	  if(crr!=null&& crr.moveToFirst()){
			
		  ChatList=makeMessageList();
			
		    setChatAdapter(ChatList);
		    
		}
	  else{	
	
   // Toast.makeText(context,"cursor is null", Toast.LENGTH_LONG).show();
	//Intent intent = new Intent(context, NothingToDisplay.class);
	//startActivity(intent);
	 finish();
    
    
    }
	
	}
	
	
	
 static 	ArrayList<FrontAllChatListWrapper>  makeMessageList(){
		
		
		Cursor csr;
	    int noofSms;
		
     //  DataBase obj=new DataBase(context);
	  	
		
		  crr=obj.getDatatodisplay(obj);
		 
		  
		  allChatList = new ArrayList<FrontAllChatListWrapper>();
	
    
    if(crr!=null&&crr.moveToFirst()){
		
		  	 
			 
		  do{	 
			  int id= crr.getInt(0);
			 
			  csr= obj.getData(obj, id);
			  csr.moveToFirst();
			  
			  noofSms = obj.getCountSms(obj, csr.getString(2));
			 
			  FrontAllChatListWrapper messageinstance=new FrontAllChatListWrapper();     
				
		      
		      
		      
		        messageinstance.set_id(csr.getInt(0));
		        
				messageinstance.set_name(csr.getString(1));
				 
				messageinstance.set_number(csr.getString(2));
				
				
				
				messageinstance.set_date(csr.getString(3));
				
				messageinstance.set_time(csr.getString(4));
				
				messageinstance.set_content(csr.getString(5));
				
				messageinstance.set_count(String.valueOf(noofSms));
				
				allChatList.add(messageinstance);
								
					//Toast.makeText(getApplicationContext(), csr.getString(0), Toast.LENGTH_LONG).show();
				    //Toast.makeText(getApplicationContext(),csr.getString(2), Toast.LENGTH_LONG).show(); 
			        //Toast.makeText(getApplicationContext(),String.valueOf(noofSms), Toast.LENGTH_LONG).show(); 
			  
			  
		  }while(crr.moveToNext());
			  
			 	  crr.close();
			 	  csr.close();
			 
	 }
		
	
	return allChatList;
		
		
	}
	
	
	
	
	
static	private void setChatAdapter(ArrayList<FrontAllChatListWrapper> allChatList) {
		// TODO Auto-generated method stub
		mAdapter = new AllChatListAdapter(context, allChatList);
		lstv.setAdapter(mAdapter);
		
		checkedCount=0;
		
		
		
		Collections.sort(allChatList, new Comparator<FrontAllChatListWrapper>()
				{

					@Override
					public int compare(FrontAllChatListWrapper lhs, FrontAllChatListWrapper rhs) {
						// TODO Auto-generated method stub
						return   rhs.get_id()-lhs.get_id();
					}
				   
				});
		
		
	}

	
	
	static void updateFrontList(){
		  
	 	try{  
	 		allChatList.clear();
	  ArrayList<FrontAllChatListWrapper> newList=makeMessageList();
	  
	    setChatAdapter(allChatList);
		 
	   
	    
		 lstv.setSelection(lstv.getAdapter().getCount()-1);
		 
		// mAdapter.notifyDataSetChanged();
		 		
	}catch(Exception e){
		
		Toast.makeText(context, "error in update", Toast.LENGTH_LONG).show();
		e.printStackTrace();
	}
  }

	
	
	

	
}
