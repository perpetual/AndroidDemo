package com.example.androiddemo.tools;

import java.util.ArrayList;
import java.util.List;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2015-4-25		Create		
 * </pre>
 */
public class SuperListAdapter<T> extends BaseAdapter {

	private Context mContext = null;
	private List<T> mDataSource = new ArrayList<T>();
	
	public SuperListAdapter(Context context) {
		mContext = context;
	}
	
	public void updateData(List<T> dataSource) {
		if (null == dataSource) {
			return;
		}
		mDataSource = dataSource;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mDataSource.size();
	}

	@Override
	public T getItem(int position) {
		return mDataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
		}
		DataHolder dataHolder = DataHolder.getDataHolder(convertView);
		dataHolder.mTextView.setText(getItem(position).toString());
		return convertView;
	}
}

class DataHolder {
	public TextView mTextView = null;
	public Object data = null;
	
	private static DataHolder CreateDataHolder(View v) {
		if (null == v) {
			return null;
		}
		DataHolder dataHolder = new DataHolder();
		View tempView = v.findViewById(R.id.text1);
		if (null != tempView && tempView instanceof TextView) {
			dataHolder.mTextView = (TextView) tempView;
		}
		v.setTag(dataHolder);
		return dataHolder;
	}		
	
	public static DataHolder getDataHolder(View v) {
		DataHolder dataHolder = null;
		if (null != v && null != v.getTag()) {
			if (v.getTag() instanceof DataHolder) {
				dataHolder = (DataHolder) v.getTag();
			}
		} else {
			dataHolder = CreateDataHolder(v);
		}
		return dataHolder;
	}
}