package com.csform.android.androidwebview.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csform.android.androidwebview.BaseActivity;
import com.csform.android.androidwebview.R;
import com.csform.android.androidwebview.model.LeftMenuItem;


public class LeftMenuAdapter extends BaseAdapter {

//	private List<String> leftMenuItems;
	private LayoutInflater inflater;
	private List<LeftMenuItem> leftMenuItems;

/*	public LeftMenuAdapter(Context context, List<String> leftMenuItems) {
		this.leftMenuItems = leftMenuItems;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}*/
	
	public LeftMenuAdapter(Context context, List<LeftMenuItem> leftMenuItems) {
		this.leftMenuItems = leftMenuItems;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private static class ViewHolder {
		public TextView name;
	}

	@Override
	public int getCount() {
		return leftMenuItems.size();
	}

	@Override
	public Object getItem(int position) {
		return leftMenuItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.left_menu_item, parent,
					false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.left_menu_item_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.name.setText(leftMenuItems.get(position).getName());
		holder.name.setTypeface(BaseActivity.sRobotoThin);
		holder.name.setCompoundDrawablesWithIntrinsicBounds(leftMenuItems.get(position).getIcon(), 0, 0, 0);
		
		
		//TODO If you use pages from your site, delete this IF block
		if(position > 2) {
			holder.name.setPadding(54, 23, 26, 23);
		}

		return convertView;
	}
}
