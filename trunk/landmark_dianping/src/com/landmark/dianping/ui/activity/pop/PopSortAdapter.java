package com.landmark.dianping.ui.activity.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.landmark.dianping.R;

public class PopSortAdapter extends BaseAdapter implements OnItemClickListener {

	private Context mContext;
	public String[] text;
	public boolean[] icon = { true, false, false, false, false };

	public PopSortAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		text = context.getResources().getStringArray(R.array.pop_sort_text);
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
