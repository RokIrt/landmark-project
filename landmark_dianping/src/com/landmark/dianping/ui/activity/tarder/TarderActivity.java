package com.landmark.dianping.ui.activity.tarder;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.landmark.dianping.R;
import com.landmark.dianping.base.BaseActivity;
import com.landmark.dianping.ui.activity.pop.DropDownBuilder;
import com.landmark.dianping.ui.adapter.TarderAdapter;
import com.landmark.dianping.ui.info.Tarder;
import com.landmark.dianping.view.FooterListView;
import com.landmark.dianping.view.FooterListView.OnLoadMoreListener;

public class TarderActivity extends BaseActivity implements OnLoadMoreListener {

	private FooterListView mFooterListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarder);
		init();
	}

	@Override
	protected void initUI() {
		mFooterListView = (FooterListView) findViewById(R.id.tarder_list);
		mFooterListView.setOnLoadMoreListener(this);
		mFooterListView.setOnItemClickListener(this);
	}

	@Override
	protected void initData() {
		mFooterListView.setAdapter(new TarderAdapter(this, getTestData()));
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
		switch (v.getId()) {
		case R.id.title_left:
			this.finish();
			break;
		case R.id.title_right:
			break;
		case R.id.filter_tool_left:
			DropDownBuilder p = DropDownBuilder.getInstance(this)
					.getDistancePopWindow();
			p.showAsDropDown(findViewById(R.id.filter_tool_left));
			break;
		case R.id.filter_tool_center:
			p = DropDownBuilder.getInstance(this).getTypePopWindow();
			p.showAsDropDown(findViewById(R.id.filter_tool_center));
			break;
		case R.id.filter_tool_right:
			p = DropDownBuilder.getInstance(this).getSortPopWindow();
			p.showAsDropDown(findViewById(R.id.filter_tool_right));
			break;
		default:
			break;
		}
	}

	@Override
	public void loadMoreData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		super.onItemClick(parent, view, position, id);
	}

}
