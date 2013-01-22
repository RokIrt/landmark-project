package com.landmark.dianping.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.landmark.dianping.R;

/**
 * 使用方法 -->1、加载数据时候：isRequestingData置为 true
 * 
 * 2、数据加载完成时：（成功失败都是如此）置为 false
 * 
 * 3、数据异常时：执行setDefault()
 * 
 * 4、移除footer时：执行removeFooterView()
 * 
 * 5、添加footer时：执行addFooterView()
 * 
 * @author landmark
 * 
 */
public class FooterListView extends ListView implements OnScrollListener {

	protected View mFooterView = null;

	/** 是否请求更多 */
	protected boolean isRefreshFoot = false;
	/** 是否正在请求数据 */
	public boolean isRequestingData = false;
	/** 是否已移除 FooterView */
	protected boolean isHasRemovedFooterView = false;

	public boolean isFirstRequest = true;

	protected int mItemCount = 0;
	public int startItem = 0;// 起始位置
	public int endItem = 0;// 结束位置

	public FooterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	protected void init(Context context) {
		mFooterView = LayoutInflater.from(context).inflate(
				R.layout.fl_list_footer, null, false);
		this.addFooterView(mFooterView, null, false);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE:
			if (!isRefreshFoot || isHasRemovedFooterView) {
				return;
			}
			if (!isRequestingData) {
				isRequestingData = true;
				if (mLoadMoreListener != null) {
					isFirstRequest = false;
					mLoadMoreListener.loadMoreData();
				}
			}
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:

			break;
		case OnScrollListener.SCROLL_STATE_FLING:

			break;
		default:
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		mItemCount = firstVisibleItem + visibleItemCount - 1;
		startItem = firstVisibleItem - 1;
		endItem = mItemCount;

		if (firstVisibleItem + visibleItemCount == totalItemCount) {
			isRefreshFoot = true;
		} else {
			isRefreshFoot = false;
		}
	}

	private OnLoadMoreListener mLoadMoreListener;

	public interface OnLoadMoreListener {
		void loadMoreData();
	}

	public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
		mLoadMoreListener = loadMoreListener;
	}

	public void setDefault() {
		if (isHasRemovedFooterView) {
			addFooterView();
		}
		isRefreshFoot = false;
		isRequestingData = false;
	}

	public void removeFooterView() {
		this.removeFooterView(mFooterView);
		isHasRemovedFooterView = true;
	}

	public void addFooterView() {
		if (isHasRemovedFooterView) {
			this.addFooterView(mFooterView, null, false);
			isHasRemovedFooterView = false;
		}
	}

}
