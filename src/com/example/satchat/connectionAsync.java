package com.example.satchat;

import java.io.IOException;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.example.satchat.XmppConnection;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public  class connectionAsync  extends AsyncTask<Void, Void, Void> {

	
	     public  String userName,pswd;
	
	    public connectionAsync(String UserName,String Password) {
		// TODO Auto-generated constructor stub
		   super();
		   this.userName=UserName;
		   this.pswd=Password;
	   }
	
	
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		
 
		
		try {
			
			//Toast.makeText(getA,"You Have Connected",Toast.LENGTH_LONG).show();
			
			
			
			XmppConnection checkIt=new XmppConnection("Sattar",5222, userName,pswd);
			
			//XMPPConnection.DEBUG_ENABLED = true;
			
			checkIt.makeConnection();
			checkIt.statusUr(true, "Sam");
			
			Log.d("in Async", "after  Connection");
			
			//System.out.print("in try block");
			
		} catch (XMPPException |SmackException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Xmpp exception is caught");
			//Toast.makeText(context, "Xmpp Exception is found", Toast.LENGTH_LONG);
		}
		return null;
		
		
		
	}

	
}
