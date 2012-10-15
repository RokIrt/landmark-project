package com.landmark.dianping.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.landmark.dianping.R;
import com.landmark.dianping.ui.info.Tarder;
import com.landmark.dianping.ui.util.image.AsyncImageLoader;

public class TarderAdapter extends ArrayAdapter<Tarder> {

	protected Activity mContext = null;
	protected LayoutInflater mInflater;
	protected ArrayList<Tarder> objects = null;

	protected HashMap<Integer, View> mViewMap = new HashMap<Integer, View>();
	protected AsyncImageLoader mAsyncImageLoader = new AsyncImageLoader();

	public TarderAdapter(Activity context, ArrayList<Tarder> objects) {
		super(context, 0, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.objects = objects;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		if (objects == null)
			return 0;
		return objects.size();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder mHolder = null;
		view = mViewMap.get(position);
		if (view == null) {
			view = mInflater.inflate(R.layout.tarder_list_item, null);
			mHolder = getViewHolder(view);
			view.setTag(mHolder);
			mViewMap.put(position, view);
		} else {
			mHolder = (ViewHolder) view.getTag();
		}
		setData(mHolder, position);
		return view;
	}

	/**
	 * 加载数据
	 * 
	 * @param mHolder
	 * @param position
	 */
	protected void setData(final ViewHolder mHolder, int position) {
		final Tarder mTarder = getItem(position);

		mAsyncImageLoader.setViewImage(mHolder.logo, mTarder.logoUrl);

		mHolder.name.setText(mTarder.name);
		mHolder.level.setRating(Integer.valueOf(mTarder.level));
		mHolder.address.setText(mTarder.address);
	}

	/**
	 * 初始化数据
	 * 
	 * @param v
	 * @return
	 */
	protected ViewHolder getViewHolder(View v) {
		ViewHolder mHolder = new ViewHolder();
		mHolder.name = (TextView) v.findViewById(R.id.name);
		mHolder.level = (RatingBar) v.findViewById(R.id.level);
		mHolder.address = (TextView) v.findViewById(R.id.address);
		mHolder.logo = (ImageView) v.findViewById(R.id.logo);
		return mHolder;
	}

	public static class ViewHolder {
		public ImageView logo;
		public TextView name;
		public RatingBar level;
		public TextView address;
	}

	public void claenViewMap() {
		mViewMap.clear();
	}
}
