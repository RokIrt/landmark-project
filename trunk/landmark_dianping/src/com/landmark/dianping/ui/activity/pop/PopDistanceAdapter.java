package com.landmark.dianping.ui.activity.pop;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.landmark.dianping.R;

public class PopDistanceAdapter extends BaseAdapter {

	private Context mContext;
	public String[] text;
	public boolean[] icon = { true, false, false, false, false };

	public PopDistanceAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		text = context.getResources().getStringArray(R.array.pop_distance_text);
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
			convertView = View.inflate(mContext, R.layout.pop_distance_item,
					null);
			mHolder = new ViewHolder();
			mHolder.icon = (ImageView) convertView.findViewById(R.id.pop_icon);
			mHolder.text = (TextView) convertView.findViewById(R.id.pop_text);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < icon.length; i++) {
					if (i == position) {
						icon[i] = true;
					} else {
						icon[i] = false;
					}
				}
				notifyDataSetChanged();
			}
		});

		mHolder.icon.setSelected(icon[position]);

		mHolder.text.setText(text[position]);
		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView text;
	}

}
