package com.landmark.dianping.ui.activity.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.landmark.dianping.R;

public class PopSubTypeAdapter extends BaseAdapter implements
		OnItemClickListener {

	private Context mContext;
	public String[] text;

	public PopSubTypeAdapter(Context context, int position) {
		mContext = context;
		initText(position);
	}

	private void initText(int position) {
		switch (position) {
		case 0:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_0);
			break;
		case 1:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_1);
			break;
		case 2:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_2);
			break;
		case 3:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_3);
			break;
		case 4:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_4);
			break;
		case 5:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_5);
			break;
		case 6:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_6);
			break;
		case 7:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_7);
			break;
		case 8:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_8);
			break;
		case 9:
			text = mContext.getResources().getStringArray(
					R.array.pop_sub_type_text_9);
			break;
		default:
			break;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	ViewHolder mHolder = null;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.pop_sort_item, null);
			mHolder = new ViewHolder();
			mHolder.text = (TextView) convertView.findViewById(R.id.pop_text);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		mHolder.text.setText(text[position]);
		return convertView;
	}

	static class ViewHolder {
		TextView text;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}
