package com.landmark.dianping.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.landmark.dianping.R;
import com.landmark.dianping.application.AppUtil;

public class NearItemView extends RelativeLayout {

	private Context mContext;

	private TextView title;
	private ImageView arrow;

	private int title_int;
	private String title_str;
	private int title_color_int;

	private int arrow_icon;

	private RelativeLayout.LayoutParams mLayoutParams;

	public NearItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initUI();
		// 获取属性集
		TypedArray type = context.obtainStyledAttributes(attrs,
				R.styleable.search_item);

		title_int = type.getResourceId(R.styleable.search_item_title, 0);
		title_str = type.getString(R.styleable.search_item_title);
		title_color_int = type.getResourceId(
				R.styleable.search_item_title_color, 0);

		arrow_icon = type.getResourceId(R.styleable.search_item_arrow, 0);
		type.recycle();
	}

	private void initUI() {
		mContext = getContext();
		title = new TextView(mContext);
		arrow = new ImageView(mContext);
		arrow.setClickable(true);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		initData();

		// title
		mLayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		this.addView(title, mLayoutParams);

		final int bound = AppUtil.dip2px(mContext, 64);
		// 添加子组件
		mLayoutParams = new RelativeLayout.LayoutParams(bound, bound);
		mLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		mLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		this.addView(arrow, mLayoutParams);
	}

	private void initData() {
		if (title_int != 0) {
			title.setText(title_int);
		}
		if (title_str != null) {
			title.setText(title_str);
		}
		if (title_color_int != 0) {
			title.setTextColor(mContext.getResources()
					.getColor(title_color_int));
		}

		if (arrow_icon != 0) {
			arrow.setImageResource(arrow_icon);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}
}
