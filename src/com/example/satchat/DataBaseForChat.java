package com.example.satchat;

import android.R.integer;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.widget.Toast;

public class DataBaseForChat extends SQLiteOpenHelper{


	  Context context;
	  public static final int database_version = 1;
	  
	  public String sql_query="create table SatChats (id integer primary key autoincrement, name varchar(20),  number varchar(20) not NULL , date_message varchar(15), time_message varchar(15), content text(50), type varchar(20));";
	  
	
	public DataBaseForChat(Context context) {
		super(context, "messsages_operation",null, database_version);
		// TODO Auto-generated constructor stub
		
		Log.d("databse operation", "database is created");
		
	}
	
	

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sql_query);
		Log.d("databse operation", "table is created");
		
	}

	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		
	}
	  
	  
	  
	     public  void  insertionrow(DataBaseForChat ob,String name,String number, String date,String time, String content_msg , String typeofmsg ){
	    	  
	    	  SQLiteDatabase sq=ob.getWritableDatabase();
	    	  ContentValues cv=new ContentValues();
	    	
	    	  
	    	 
	    	  
	    	  cv.put("name", name);
	    	  cv.put("number", number);
	    	  cv.put("date_message", date);
	    	  cv.put("time_message", time);
	    	  cv.put("content", content_msg);
	    	  cv.put("type", typeofmsg); 
	    	  
	    	  sq.insert("SatChats", null, cv);
	    	  
	    	  Log.d("database operation", "data is inserted");
	    	  
	    	  
	      }
	   
	    
	     
	     
	     
        public Cursor getData(DataBaseForChat object,int idofsms){
	    	 
	    	 SQLiteDatabase SQ=object.getReadableDatabase();
	    	 
	    	 String sql_query="select * from SatChats where id="+idofsms;
		       	
	         Cursor cr = SQ.rawQuery(sql_query, null);
	       	
	       
	    	 return cr;
	    	 
	    	 
	     }
        
        
        public Cursor getIndividualMsgs(DataBaseForChat object,String mobileno){
	    	 
        	Cursor cr=null;
        	
	    	try{ SQLiteDatabase SQ=object.getReadableDatabase();
	    	 
	    	 String sql_query="select * from SatChats where number='"+mobileno+"'";
		       	
	          cr = SQ.rawQuery(sql_query, null);
	       	
	    	}catch(SQLiteException e){
	    		
	    		Toast.makeText(context, "sqlite exception", Toast.LENGTH_LONG).show();
	    		e.printStackTrace();
	    	}
	    	 return cr;
	    	 
	    	 
	     }
        
        
	     
	     public Cursor 	getDatatodisplay(DataBaseForChat ob){
	    	 Cursor csr=null;
	    	 try{
	    	 SQLiteDatabase SQ=ob.getReadableDatabase();
	  
	    	 
	         String sql_query="select max(id) from SatChats group by number";
	       	
	          csr = SQ.rawQuery(sql_query, null);
	       	
	    	 }catch(SQLiteException e){
		    		
		    	//	Toast.makeText(context, "sqlite exception error in max id query", Toast.LENGTH_LONG).show();
		    		e.printStackTrace();
		    	}
	         
	       	return csr;
	       }
	   	
	     
	     public int getCountSms(DataBaseForChat ob,String mono){
		     	
	    	 Context context=null;
	    	   Cursor csr;
	    	   int noSms=0;
	    	   
	    	 SQLiteDatabase SQ=ob.getReadableDatabase();
	    	
	    	 try{
	         String sql_query="select * from SatChats where number='"+mono+"'";
	       	
	          csr = SQ.rawQuery(sql_query, null);
	           noSms = csr.getCount();
	           
	           //Toast.makeText(context,String.valueOf(noSms), Toast.LENGTH_LONG).show();
	           
	           //Log.d("no of Sms","SMS"+String.valueOf(noSms));
	           
	    	 }catch(Exception e){
	    		 
	    		 //Toast.makeText(context,"no of sms not found", Toast.LENGTH_LONG).show();
	    		 e.printStackTrace();
	    	 }
	         
	         
	         
	       	return noSms;
	       }
	   	
	     
	   public Integer deleteSingleSms(DataBaseForChat obj, String id,String num){
		   
		   
		   SQLiteDatabase SQR=obj.getReadableDatabase();
		   String sql_query="select * from SatChats where number='"+num+"'";
	       	
	         Cursor csr = SQR.rawQuery(sql_query, null);
	          Integer noSms = csr.getCount();
	          
	          SQLiteDatabase SQ=obj.getWritableDatabase();
              SQ.execSQL("delete from SatChats where id='"+id+"'");

	         return noSms;
	   }
	     
	   
	   
  public void  deleteIndividualAllSms(DataBaseForChat obj, String number){
		   
	  SQLiteDatabase SQS=obj.getReadableDatabase();
	   String sql_query="select distinct number from SatChats";
      	
        Cursor csr = SQS.rawQuery(sql_query, null);
         Integer noSm = csr.getCount();
		   
		   SQLiteDatabase SQ=obj.getWritableDatabase();
		  // Toast.makeText(context,"in delete all method", Toast.LENGTH_LONG).show();
		   SQ.execSQL("delete from SatChats where number='"+number+"'");
		  
	        	 MainActivity.updateFrontList();
	         
	   }
	   
	     
	     public static String getContactName(Context context,String phonenumber){
	 		
	 		Cursor cursor=null;
	 		
	 		
	 		try{
	 		
	 		ContentResolver ctrsvr=context.getContentResolver();
	 		
	 		Uri uri=Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phonenumber));
	 		
	 		 cursor=ctrsvr.query(uri, new String[]{PhoneLookup.DISPLAY_NAME},null , null, null);
	 		
	 		}catch(Exception e){
	 			
	 			Toast.makeText(context, "contact name is not found", Toast.LENGTH_LONG).show();
	 			e.printStackTrace();
	 		}
	 		
	 		
	 		if(cursor==null){
	 			return null;
	 		}
	 		
	 		String contactname=null;
	 		
	 		
	 		
	 		if(cursor.moveToFirst()){
	 			
	 		contactname=cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));	
	 			
	 		}
	 		
	 		
	 		if(cursor!=null&&!cursor.isClosed()){
	 			
	 			cursor.close();
	 		}
	 		
	 		return contactname;
	 	}
	 	
	 	
	     
	     
	  
}
	