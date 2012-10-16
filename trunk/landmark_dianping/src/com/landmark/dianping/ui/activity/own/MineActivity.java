package com.landmark.dianping.ui.activity.own;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.landmark.dianping.R;
import com.landmark.dianping.application.MyApp;

public class MineActivity extends Activity implements OnClickListener{
private Button login,regist;
private LinearLayout my_card,my_profile_book,my_favorie,my_checkin,my_photo,my_comment,my_focus,my_fans;
private boolean isLogin = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_mine);
		
		
	}
	
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			this.init();
		}
	
	private void init(){
		this.login = (Button) this.findViewById(R.id.login);
		this.regist = (Button) this.findViewById(R.id.regist);
		this.my_card = (LinearLayout) this.findViewById(R.id.my_card);
		this.my_profile_book = (LinearLayout) this.findViewById(R.id.my_profile_book);
		this.my_favorie =(LinearLayout)  this.findViewById(R.id.my_favorite);
		this.my_photo = (LinearLayout) this.findViewById(R.id.my_photo);
		this.my_comment = (LinearLayout) this.findViewById(R.id.my_comment);
		this.my_focus = (LinearLayout) this.findViewById(R.id.my_focus);
		this.my_fans = (LinearLayout) this.findViewById(R.id.my_fans);
		this.my_checkin = (LinearLayout) this.findViewById(R.id.my_checkin);
		this.login.setOnClickListener(this);
		this.regist.setOnClickListener(this);
		this.my_profile_book.setOnClickListener(this);
		this.my_favorie.setOnClickListener(this);
		this.my_checkin.setOnClickListener(this);
		this.my_photo.setOnClickListener(this);
		this.my_comment.setOnClickListener(this);
		this.my_fans.setOnClickListener(this);
		my_focus.setOnClickListener(this);
		my_card.setOnClickListener(this);
		isLogin = MyApp.getPre().getBoolean("isLogin");
	}

	@Override
	public void onClick(View arg0) {
		if(isLogin == false){
			this.intentToDetaile(0);
		}else{
			switch(arg0.getId())
			{
			case R.id.login:
				if(isLogin == false){
				this.intentToDetaile(0);
				}else{
					MyApp.getPre().putBoolean("isLogin", false);
					isLogin = false;
				}
				break;
			case R.id.regist:
				this.intentToDetaile(1);
				break;
			case R.id.my_card:
				this.intentToDetaile(1);
				break;
			case R.id.my_profile_book:
				this.intentToDetaile(1);
				break;
			case R.id.my_favorite:
				this.intentToDetaile(1);
				break;
			case R.id.my_photo:
				this.intentToDetaile(1);
				break;
			case R.id.my_checkin:
				this.intentToDetaile(1);
				break;
			case R.id.my_focus:
				this.intentToDetaile(1);
				break;
			case R.id.my_fans:
				this.intentToDetaile(1);
				break;
			default:
				break;
			}
		}
	}
	
	private void intentToDetaile(int type){
		switch(type){
		case 0:
			Intent intent = new Intent(this,LoginActivity.class);
			this.startActivity(intent);
			break;
		case 1:
			Intent intent1 = new Intent(this,MineDetaileActivity.class);
			this.startActivity(intent1);
			break;
		}
	}
}
