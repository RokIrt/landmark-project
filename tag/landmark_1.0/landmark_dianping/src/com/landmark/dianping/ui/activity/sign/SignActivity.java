package com.landmark.dianping.ui.activity.sign;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.landmark.dianping.R;
import com.landmark.dianping.base.BaseActivity;
import com.landmark.dianping.ui.adapter.TarderAdapter;
import com.landmark.dianping.ui.info.Tarder;
import com.landmark.dianping.view.PullFooterListView;
import com.landmark.dianping.view.PullFooterListView.OnLoadMoreListener;
import com.landmark.dianping.view.PullFooterListView.OnRefreshListener;

public class SignActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

	private PullFooterListView mPullFooterListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		init();
	}

	@Override
	protected void initUI() {
		mPullFooterListView = (PullFooterListView) findViewById(R.id.sign_list);
		mPullFooterListView.setOnItemClickListener(this);
		mPullFooterListView.setonRefreshListener(this);
		mPullFooterListView.setOnLoadMoreListener(this);
	}

	@Override
	protected void initData() {
		mPullFooterListView.setAdapter(new TarderAdapter(this, getTestData()));
	}

	String[] url = { "http://t02.pic.sogou.com/2cb39f31afd937b5.jpg",
			"http://t02.pic.sogou.com/771b3a9fce359a71.jpg",
			"http://t03.pic.sogou.com/00563c50503cf356.jpg",
			"http://t02.pic.sogou.com/6b199bdca0f124c5.jpg",
			"http://t02.pic.sogou.com/97d9247a61440cf5.jpg" };

	private ArrayList<Tarder> getTestData() {
		ArrayList<Tarder> tarders = new ArrayList<Tarder>();
		for (int i = 0; i < 15; i++) {
			Tarder tarder = new Tarder();
			tarder.logoUrl = url[i % 5];
			tarder.name = "测试名称--" + i;
			tarder.level = i % 5 + "";
			tarder.address = "测试地址--" + i;
			tarders.add(tarder);
		}
		return tarders;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadMoreData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this,DetailActivity.class);
		this.startActivity(intent);
	}
}
