package com.example.satchat;


import com.example.satchat.R;
import com.example.satchat.FrontAllChatListWrapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AllChatListAdapter extends BaseAdapter {
	private final static String TAG = AllChatListAdapter.class.getSimpleName();
	Context context;
	ArrayList<FrontAllChatListWrapper> allChatList;
	LayoutInflater inflater;
	ViewHoder holder;
  
 
   
	public  AllChatListAdapter(Context context, ArrayList<FrontAllChatListWrapper> allChatList) {
		this.context = context;
		this.allChatList = allChatList;
		
       // mSelectedItemsIds=new SparseBooleanArray();
		  
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	
	}

	@Override
	public int getCount() {
		return allChatList.size();
	}
	

	@Override
	public FrontAllChatListWrapper getItem(int position) {
		return allChatList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	public void remove(FrontAllChatListWrapper object) {
	
		String mono=object.get_number();
		allChatList.remove(object);
		// Toast.makeText(context,mono, Toast.LENGTH_LONG).show();
		  
       new DataBaseForChat(context).deleteIndividualAllSms(new DataBaseForChat(context),mono);	
         
		
		
		//notifyDataSetChanged();
	}
 /*
	public void removeSelection() {
		
		 mSelectedItemsIds = new SparseBooleanArray();
			MainActivity.updateFrontList();
			//notifyDataSetChanged();
		}
	
	
	public void toggleSelection(int position) {
		
		
		selectView(position, !mSelectedItemsIds.get(position));
	
	}

	public void selectView(int position, boolean value) {
		
		Toast.makeText(context,"selected view", Toast.LENGTH_LONG).show();
		
		if (value){
			Toast.makeText(context,"false", Toast.LENGTH_LONG).show();
			mSelectedItemsIds.put(position, value);}
		else{
			Toast.makeText(context,"true", Toast.LENGTH_LONG).show();
			mSelectedItemsIds.delete(position);
		}
		
		//MainActivity.updateFrontList();
		
	}
 
	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}
 
	
	
	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}
	
	*/
	
	
	
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_all_chat_list_adapter, null);

			holder = new ViewHoder(convertView);

			convertView.setTag(holder);
		} else {

			holder = (ViewHoder) convertView.getTag();
		}

		FrontAllChatListWrapper obj = allChatList.get(position);

		
		//  Toast.makeText(context,obj.get_name(), Toast.LENGTH_LONG).show();
       // Toast.makeText(context,obj.get_number(), Toast.LENGTH_LONG).show();
		
		if(obj.get_name().equals("Unknown Person")){
		holder.addresseTv.setText(obj.get_number()); }
		else{
		holder.addresseTv.setText(obj.get_name()); }
		
		
		holder.countTv.setText(obj.get_count());
		holder.contentTv.setText(obj.get_content());
		
		 long date = System.currentTimeMillis(); 

		  SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
		  String dateString = sdf.format(date); 
		  
		 // Toast.makeText(context,dateString, Toast.LENGTH_LONG).show();
		 
		  
		  if(obj.get_date().equals(dateString)){
			  holder.dateTv.setText(obj.get_time()); }
		  else{
		      holder.dateTv.setText(obj.get_date()); }
		

		return convertView;
	}

	public static class ViewHoder {

		TextView addresseTv,contentTv,dateTv,countTv;

		public ViewHoder(View view) {
		
            
			addresseTv = (TextView) view.findViewById(R.id.personaddress);
			countTv = (TextView) view.findViewById(R.id.noofSms);
			contentTv = (TextView) view.findViewById(R.id.messagecontent);
			dateTv = (TextView) view.findViewById(R.id.dateofmsg);
			
		}
	}

}
