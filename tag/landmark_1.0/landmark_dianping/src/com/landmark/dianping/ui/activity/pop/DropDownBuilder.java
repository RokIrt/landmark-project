package com.landmark.dianping.ui.activity.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.landmark.dianping.R;

public class DropDownBuilder {

	private static DropDownBuilder instance;
	private Context mContext;
	private PopupWindow mPopupWindow;
	private View view;

	public static DropDownBuilder getInstance(Context context) {
		if (instance == null) {
			instance = new DropDownBuilder(context);
		}
		return instance;
	}

	private DropDownBuilder(Context context) {
		this.mContext = context;
	}

	public DropDownBuilder getDistancePopWindow() {
		dismiss();
		view = View.inflate(mContext, R.layout.pop_distance, null);
		GridView mGridView = (GridView) view
				.findViewById(R.id.pop_gride_distance);
		mGridView.setAdapter(new PopDistanceAdapter(mContext));
		initPopWindow(view);
		return instance;
	}

	public DropDownBuilder getTypePopWindow() {
		dismiss();
		view = View.inflate(mContext, R.layout.pop_type, null);
		ListView mListView = (ListView) view
				.findViewById(R.id.pop_list_type_left);
		ListView mRightList = (ListView) view
				.findViewById(R.id.pop_list_type_right);
		PopTypeAdapter mAdapter = new PopTypeAdapter(mContext, mRightList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mAdapter);
		initPopWindow(view);
		return instance;
	}

	public DropDownBuilder getSortPopWindow() {
		dismiss();
		view = View.inflate(mContext, R.layout.pop_sort, null);
		ListView mListView = (ListView) view.findViewById(R.id.pop_list_sort);
		PopSortAdapter mAdapter = new PopSortAdapter(mContext);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mAdapter);
		initPopWindow(view);
		return instance;
	}

	private void initPopWindow(View view) {
		mPopupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, true);
		mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(
				R.drawable.fl_transparent));
		// mPopupWindow.setFocusable(true);
		// mPopupWindow.setOutsideTouchable(false);
	}

	public void showAsDropDown(View anchor) {
		if (mPopupWindow != null) {
			mPopupWindow.showAsDropDown(anchor);
		}
	}

	public void showAsDropDown(View anchor, int xoff, int yoff) {
		if (mPopupWindow != null) {
			mPopupWindow.showAsDropDown(anchor, xoff, yoff);
		}
	}

	public void showAtLocation(View parent, int gravity, int x, int y) {
		if (mPopupWindow != null) {
			mPopupWindow.showAtLocation(parent, gravity, x, y);
		}
	}

	private void dismiss() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}
}
