package com.landmark.dianping.ui.activity.own;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.landmark.dianping.R;
import com.landmark.dianping.application.MyApp;
import com.landmark.dianping.application.SharePreferenceUtil;

public class LoginActivity extends Activity implements OnClickListener{
	private String[] use_other_id = {"用新浪微博账号登陆","用新浪微博账号登陆","用新浪微博账号登陆","用新浪微博账号登陆"};
	private Drawable[] use_other_id_photo = new Drawable[4];
	private ListView listview;
	private Button loginBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		this.use_other_id_photo[0] = this.getResources().getDrawable(R.drawable.ic_chk_kaixin_selected);
		this.use_other_id_photo[1] = this.getResources().getDrawable(R.drawable.ic_chk_qq_selected);
		this.use_other_id_photo[2] = this.getResources().getDrawable(R.drawable.ic_chk_renren_selected);
		this.use_other_id_photo[3] = this.getResources().getDrawable(R.drawable.ic_chk_sina_selected);
		
		listview = (ListView) this.findViewById(R.id.use_orther_id_login_list);
		MyAdapter myadapter = new MyAdapter();
		listview.setAdapter(myadapter);
		
		loginBtn = (Button) this.findViewById(R.id.login_in_loginactivity);
		loginBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SharePreferenceUtil share = MyApp.getPre();
		share.putBoolean("isLogin",true);
	}
	
//=================================俺是那华丽的分割线=============================================	
	class MyAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		
		 public MyAdapter() {
	            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        }
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return use_other_id.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder = null;
            if (arg1 == null) {
                arg1 = mInflater.inflate(R.layout.list_item_in_login, null);
                holder = new ViewHolder();
                holder.textView = (TextView)arg1.findViewById(R.id.textview_of_login_list_item);
                holder.imageView = (ImageView) arg1.findViewById(R.id.imageview_of_login_list_item);
                arg1.setTag(holder);
            } else {
                holder = (ViewHolder)arg1.getTag();
            }
            holder.textView.setText(use_other_id[arg0]);
            holder.imageView.setImageDrawable(use_other_id_photo[arg0]);
            return arg1;
		}
	}
	 public static class ViewHolder {
	        public TextView textView;
	        public ImageView imageView;
	    }
}
