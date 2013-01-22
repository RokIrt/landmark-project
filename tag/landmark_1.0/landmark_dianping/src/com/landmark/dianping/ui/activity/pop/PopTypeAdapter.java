package com.landmark.dianping.ui.activity.pop;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.landmark.dianping.R;

public class PopTypeAdapter extends BaseAdapter implements OnItemClickListener {

	private Context mContext;
	public String[] text;
	private TypedArray images;
	private ListView mRightList;

	public PopTypeAdapter(Context context, ListView mRightList) {
		// TODO Auto-generated constructor stub
		mContext = context;
		text = context.getResources().getStringArray(R.array.pop_type_text);
		images = mContext.getResources()
				.obtainTypedArray(R.array.pop_type_icon);
		this.mRightList = mRightList;
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
			convertView = View.inflate(mContext, R.layout.pop_type_item, null);
			mHolder = new ViewHolder();
			mHolder.icon = (ImageView) convertView.findViewById(R.id.pop_icon);
			mHolder.text = (TextView) convertView.findViewById(R.id.pop_text);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		mHolder.icon.setImageDrawable(images.getDrawable(position));
		mHolder.text.setText(text[position]);
		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView text;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		PopSubTypeAdapter mAdapter = new PopSubTypeAdapter(mContext, position);
		mRightList.setAdapter(mAdapter);
		mRightList.setOnItemClickListener(mAdapter);
	}

}
