package com.landmark.dianping.ui.activity.search;

import android.os.Bundle;
import android.view.View;

import com.landmark.dianping.R;
import com.landmark.dianping.base.BaseActivity;
import com.landmark.dianping.ui.util.IntentUtils;

public class SearchActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.food:
			IntentUtils.forwardTarderActivity(this);
			break;
		case R.id.snack:
			IntentUtils.forwardTarderActivity(this);
			break;
		case R.id.coffee:
			IntentUtils.forwardTarderActivity(this);
			break;
		case R.id.film:
			IntentUtils.forwardTarderActivity(this);
			break;
		case R.id.ktv:
			IntentUtils.forwardTarderActivity(this);
			break;
		case R.id.more:
			IntentUtils.forwardTarderActivity(this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initUI() {
		// TODO Auto-generated method stub

	}
}
