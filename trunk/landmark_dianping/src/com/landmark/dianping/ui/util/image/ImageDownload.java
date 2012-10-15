package com.landmark.dianping.ui.util.image;

import android.graphics.drawable.Drawable;

import com.landmark.dianping.application.AppUtil;
import com.landmark.dianping.application.MyApp;
import com.landmark.dianping.ui.util.LocalFileUtil;
import com.landmark.dianping.ui.util.http.HttpUtil;

public class ImageDownload {

	/**
	 * 根据imageurl获取图片
	 * 
	 * @param fileDir
	 * @param imgUrl
	 * @return
	 */
	public static Drawable loadImageFromUrl(String fileDir, String imgUrl) {
		if (null == imgUrl) {
			return null;
		}
		String filePath = LocalFileUtil.getPhotoPath(fileDir, imgUrl);
		if (LocalFileUtil.isEixstsFile(filePath)) {
			return getLocalDrawable(filePath);
		} else {
			return getNetWorkDrawable(fileDir, imgUrl);
		}
	}

	/**
	 * 获取网络图片并且保存到本地
	 * 
	 * @param fileDir
	 * @param imgUrl
	 * @return
	 */
	public static Drawable getNetWorkDrawable(String fileDir, String imgUrl) {
		HttpUtil _ht = new HttpUtil(MyApp.getGlobalContext(), imgUrl);
		System.out.println("fileDir = " + fileDir + " imageUrl = " + imgUrl);
		byte[] imgData = _ht.getData();
		if (null == imgData || imgData.length == 0) {
			return null;
		}
		boolean isSaveSuccess = LocalFileUtil.savePhotoToSDCard(imgData,
				fileDir, imgUrl);
		if (!isSaveSuccess) {
			LocalFileUtil.deleteSDcardFile(fileDir, imgUrl);
		}
		return AppUtil.byteToDrawable(imgData);
	}

	/**
	 * 读取sdcard中的图片
	 * 
	 * @param filePath
	 * @return
	 */
	public static Drawable getLocalDrawable(String filePath) {
		return Drawable.createFromPath(filePath);
	}

}
