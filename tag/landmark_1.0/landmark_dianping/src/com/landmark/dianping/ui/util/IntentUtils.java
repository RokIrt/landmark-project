package com.landmark.dianping.ui.util;

import com.landmark.dianping.ui.activity.tarder.TarderActivity;

import android.content.Context;
import android.content.Intent;

public class IntentUtils {

	public static void forwardTarderActivity(Context mContext) {
		Intent intent = new Intent(mContext, TarderActivity.class);
		mContext.startActivity(intent);
	}
}
