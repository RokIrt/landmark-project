package com.landmark.dianping.view;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.landmark.dianping.R;

public class PullFooterListView extends ListView implements OnScrollListener {

	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;

	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;

	private LinearLayout headView;

	private TextView head_tips;
	private TextView update_time;
	private ImageView head_arrow;
	private ProgressBar progressBar;

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean isRecored;

	private int headContentWidth;
	private int headContentHeight;

	private int startY;
	private int firstItemIndex;

	private int state;

	private boolean isBack;

	private boolean isRefreshable;

	// 一下是footer
	protected View mFooterView = null;

	/** 是否请求更多 */
	protected boolean isRefreshFoot = false;
	/** 是否正在请求数据 */
	public boolean isRequestingData = false;
	/** 是否已移除 FooterView */
	protected boolean isHasRemovedFooterView = false;

	protected int mItemCount = 0;
	public int startItem = 0;// 起始位置
	public int endItem = 0;// 结束位置

	public PullFooterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	protected void init(Context context) {

		headView = (LinearLayout) View.inflate(context, R.layout.fl_pull_head,
				null);

		head_arrow = (ImageView) headView.findViewById(R.id.head_arrow);
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		head_tips = (TextView) headView.findViewById(R.id.head_tips);
		update_time = (TextView) headView.findViewById(R.id.head_update);

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		this.addHeaderView(headView, null, false);
		this.setOnScrollListener(this);

		state = DONE;
		isRefreshable = false;

		initAnimation();

		// footer
		mFooterView = LayoutInflater.from(context).inflate(
				R.layout.fl_list_footer, null, false);
		this.addFooterView(mFooterView, null, false);
	}

	private void initAnimation() {
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
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

		firstItemIndex = firstVisibleItem;
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
	public boolean onTouchEvent(MotionEvent event) {
		if (!isRefreshable) {
			return super.onTouchEvent(event);
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (firstItemIndex == 0 && !isRecored) {
				isRecored = true;
				startY = (int) event.getY();
//				Log.v(TAG, "在down时候记录当前位置‘");
			}
			break;

		case MotionEvent.ACTION_UP:

			if (state != REFRESHING && state != LOADING) {
				if (state == DONE) {
					// 什么都不做
				}
				if (state == PULL_To_REFRESH) {
					state = DONE;
					changeHeaderViewByState();

				}
				if (state == RELEASE_To_REFRESH) {
					state = REFRESHING;
					changeHeaderViewByState();
					onRefresh();

				}
			}

			isRecored = false;
			isBack = false;

			break;

		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();

			if (!isRecored && firstItemIndex == 0) {
				isRecored = true;
				startY = tempY;
			}

			if (state != REFRESHING && isRecored && state != LOADING) {

				// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

				// 可以松手去刷新了
				if (state == RELEASE_To_REFRESH) {

					setSelection(0);

					// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
					if (((tempY - startY) / RATIO < headContentHeight)
							&& (tempY - startY) > 0) {
						state = PULL_To_REFRESH;
						changeHeaderViewByState();

					}
					// 一下子推到顶了
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();

					}
					// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
					else {
						// 不用进行特别的操作，只用更新paddingTop的值就行了
					}
				}
				// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
				if (state == PULL_To_REFRESH) {

					setSelection(0);

					// 下拉到可以进入RELEASE_TO_REFRESH的状态
					if ((tempY - startY) / RATIO >= headContentHeight) {
						state = RELEASE_To_REFRESH;
						isBack = true;
						changeHeaderViewByState();

					}
					// 上推到顶了
					else if (tempY - startY <= 0) {
						state = DONE;
						changeHeaderViewByState();

					}
				}

				// done状态下
				if (state == DONE) {
					if (tempY - startY > 0) {
						state = PULL_To_REFRESH;
						changeHeaderViewByState();
					}
				}

				// 更新headView的size
				if (state == PULL_To_REFRESH) {
					headView.setPadding(0, -1 * headContentHeight
							+ (tempY - startY) / RATIO, 0, 0);

				}

				// 更新headView的paddingTop
				if (state == RELEASE_To_REFRESH) {
					headView.setPadding(0, (tempY - startY) / RATIO
							- headContentHeight, 0, 0);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			head_arrow.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			head_tips.setVisibility(View.VISIBLE);
			update_time.setVisibility(View.VISIBLE);

			head_arrow.clearAnimation();
			head_arrow.startAnimation(animation);

			head_tips.setText("松开刷新");

			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			head_tips.setVisibility(View.VISIBLE);
			update_time.setVisibility(View.VISIBLE);
			head_arrow.clearAnimation();
			head_arrow.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				head_arrow.clearAnimation();
				head_arrow.startAnimation(reverseAnimation);

				head_tips.setText("下拉刷新");
			} else {
				head_tips.setText("下拉刷新");
			}
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);

			progressBar.setVisibility(View.VISIBLE);
			head_arrow.clearAnimation();
			head_arrow.setVisibility(View.GONE);
			head_tips.setText("正在刷新...");
			update_time.setVisibility(View.VISIBLE);

			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);

			progressBar.setVisibility(View.GONE);
			head_arrow.clearAnimation();
			head_tips.setText("下拉刷新");
			update_time.setVisibility(View.VISIBLE);

			break;
		}
	}

	private OnRefreshListener refreshListener;

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public void onRefreshComplete() {
		state = DONE;
		update_time.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter) {
		update_time.setText("最近更新:" + new Date().toLocaleString());
		super.setAdapter(adapter);
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
		this.addFooterView(mFooterView, null, false);
		isHasRemovedFooterView = false;
	}
}
