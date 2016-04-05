package com.example.satchat;



import java.io.IOException;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class XmppConnection {

	
	Context contex;
	
	private final int replytimeout=600;
	
	private String server;
	
	private int port;
	
	private String userName;
	private String pswd;
	
	private XMPPTCPConnection connect;
	private ChatManager chmanager;
	private MessageListener msgListener;
	
	private XMPPTCPConnectionConfiguration.Builder config;
	
	public XmppConnection(String server,int port,String username,String password){
				
		this.server=server;
		this.port=port;
		this.userName=username;
		this.pswd=password;
		
		
	}
	
	
	public void makeConnection() throws XMPPException, SmackException, IOException{
		
	
	
  SmackConfiguration.setDefaultPacketReplyTimeout(replytimeout);
		
    Log.d("username in Connection", userName);
	Log.d("password in Connection", pswd);
	Log.d("server in Connection", server);
	Log.d("port in Connection", Integer.toString(port));
  
 
  
  config = XMPPTCPConnectionConfiguration.builder();
  config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
  config.setUsernameAndPassword("shahajad@chatsattar.com","s79826919952m"); 
  config.setServiceName("chatsattar.com");
  config.setHost("192.168.109.1");
  config.setPort(port);
  config.setDebuggerEnabled(true);
  config.setSocketFactory(SSLSocketFactory.getDefault());
  
  
  try{
  connect= new XMPPTCPConnection(config.build());
  
     Log.d("just","before Connect");
  
     connect.connect();
    
     Log.d("sattar", "sattar Connected to " + connect.getHost()+connect.isConnected());
     
  }catch(SmackException | IOException | XMPPException ex){
	  Log.d("XMPPClient","exception is :"+ex.toString());
  }
     
     
     
  /*  Log.d("just","after Connect");
     
     if(connect.isConnected()){
    	 Log.d("your are","Connected");
     }
     else{
    	 Log.d("your are","not Connected");
     }
   */  
     if(connect==null){
    	 
    	 Log.d("your are","connection is null");
     }
  
 
 /* 
  
  chmanager=connect.getChatManager();
  
    connect.get
  msgListener=new  myMsgListener();
  
  connect.login(userName, pswd);
  
  statusUr(true, "ya i m here");
  
 if(connect!=null&&connect.isConnected()){
		connect.login(userName, pswd);
	}
*/
  		
}
	
	
	public void  statusUr(boolean available,String status) throws NotConnectedException{
		

		 Presence.Type type=available? Type.available:Type.unavailable;
		
		Presence presence=new Presence(type);
		
	presence.setStatus(status);
	connect.sendPacket(presence);
		
		
		
	}
	
/*	
public void login(String userName, String pswd)throws XMPPException{
	
	
	if(connect!=null&&connect.isConnected()){
		connect.login(userName, pswd);
	}
}
	
	*/
	
	public void disConnect(){
		if(connect!=null&&connect.isConnected()){
			connect.disconnect();
		}
		
	}
	
	
	
	public void msgSend(String msg,String urXmppId )throws XMPPException, NotConnectedException{
		
		Chat chat=chmanager.createChat(urXmppId);
		chat.sendMessage(msg);
		
		Toast.makeText(contex,"message sent:"+msg, Toast.LENGTH_SHORT);
		
	}
	
/*	
public void 	addFrndRoster(String user,String name)throws XMPPException{
	
	Roster roster=connect.getRoster();
	roster.createEntry(user, name, null);
	
}
	*/


class myMsgListener implements MessageListener{

	
	@Override
	public void processMessage(Message arg0) {
		// TODO Auto-generated method stub
		
		String msgFrom=arg0.getFrom();
		String msgBody=arg0.getBody();
		
		System.out.print("Received from :"+msgFrom+" message is :"+msgBody);
		
	}
	
	
}
	
	
	
	
	
	
}
