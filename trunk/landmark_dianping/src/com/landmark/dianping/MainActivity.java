package com.landmark.dianping;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.landmark.dianping.ui.more.MoreActivity;
import com.landmark.dianping.ui.search.SearchActivity;
import com.landmark.dianping.ui.sign.SignActivity;

public class MainActivity extends TabActivity {

	private TabHost mTabHost = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inint();
		initData();
	}

	private void inint() {
		mTabHost = getTabHost();
	}

	private void initData() {
		mTabHost.addTab(mTabHost
				.newTabSpec("A")
				.setIndicator(
						View.inflate(this, R.layout.tab_indicator_search, null))
				.setContent(new Intent(this, SearchActivity.class)));
		mTabHost.addTab(mTabHost
				.newTabSpec("B")
				.setIndicator(
						View.inflate(this, R.layout.tab_indicator_tuan, null))
				.setContent(new Intent(this, MoreActivity.class)));
		mTabHost.addTab(mTabHost
				.newTabSpec("C")
				.setIndicator(
						View.inflate(this, R.layout.tab_indicator_checkin, null))
				.setContent(new Intent(this, SignActivity.class)));
		mTabHost.addTab(mTabHost
				.newTabSpec("D")
				.setIndicator(
						View.inflate(this, R.layout.tab_indicator_my, null))
				.setContent(new Intent(this, MoreActivity.class)));
		mTabHost.addTab(mTabHost
				.newTabSpec("E")
				.setIndicator(
						View.inflate(this, R.layout.tab_indicator_more, null))
				.setContent(new Intent(this, MoreActivity.class)));
	}

}
