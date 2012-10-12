package com.landmark.dianping.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @author llc
 * 
 * @data 2011-11-11
 * 
 */
public class AppUtil {

	public static int messageNum = 0;
	public static String packagename = null;
	public static String filepath = null;

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 *            上下文
	 * @param dpValue
	 *            单位dip的数据
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = MyApp.getGlobalContext().getResources()
				.getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 *            上下文
	 * @param dpValue
	 *            单位dip的数据
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = MyApp.getGlobalContext().getResources()
				.getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕的宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getWidth(Context context) {
		DisplayMetrics dm = MyApp.getGlobalContext().getApplicationContext()
				.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getHeight(Context context) {
		DisplayMetrics dm = MyApp.getGlobalContext().getApplicationContext()
				.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * 获取屏幕密度
	 * 
	 * @return
	 */
	public static float getDensity() {
		return MyApp.getGlobalContext().getResources().getDisplayMetrics().density;
	}

	/**
	 * byte[] -> Drawable
	 * 
	 * @param bytes
	 * @return
	 */
	public static BitmapDrawable byteToDrawable(byte[] bytes) {
		// Bitmap bitmap = BitmapUtils.createImg(bytes);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
				null);
		if (null == bitmap) {
			return null;
		}
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	/**
	 * 图片灰度处理
	 * 
	 * @param bmpOriginal
	 * @return
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		final int height = bmpOriginal.getHeight();
		final int width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/**
	 * 随机数
	 */
	public static int numRandom(int mixNum, int _num) {
		return (int) (Math.random() * _num);
	}

	/**
	 * 包大小单位处理
	 * 
	 * @param length
	 * @return
	 */
	public static String formatFileSize(long length) {
		String result = null;
		int sub_string = 0;
		if (length >= 1073741824) {
			sub_string = String.valueOf((float) length / 1073741824).indexOf(
					".");
			result = ((float) length / 1073741824 + "000").substring(0,
					sub_string + 3) + "GB";
		} else if (length >= 1048576) {
			sub_string = String.valueOf((float) length / 1048576).indexOf(".");
			result = ((float) length / 1048576 + "000").substring(0,
					sub_string + 3) + "MB";
		} else if (length >= 1024) {
			sub_string = String.valueOf((float) length / 1024).indexOf(".");
			result = ((float) length / 1024 + "000").substring(0,
					sub_string + 3) + "KB";
		} else if (length < 1024)
			result = Long.toString(length) + "B";
		return result;
	}

	/**
	 * 获取百分比
	 * 
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static String getPercentage(float f1, float f2) {
		if (f2 == 0) {
			return 0.0 + "%";
		} else {
			String percent = String.valueOf(f1 / f2 * 100) + "000";
			percent = percent.substring(0, percent.indexOf(".") + 3);
			return percent + "%";
		}
	}

	/**
	 * 获取下载进度
	 * 
	 * @param downloadSize
	 * @param totalSize
	 * @return
	 */
	public static int getProgress(float downloadSize, float totalSize) {
		if (totalSize == 0)
			return 0;
		else
			return (int) (downloadSize * 100 / totalSize);
	}

	/**
	 * 获取rating
	 * 
	 * @param rate
	 * @return
	 */
	public static float getRating(String rate) {
		if (rate != null) {
			if (rate.trim().length() == 2) {
				return Float.valueOf(rate) / 10;
			} else {
				return Float.valueOf(rate);
			}
		}
		return 0;
	}

	/**
	 * 获取view在屏幕上的位置
	 * 
	 * @param v
	 * @return
	 */
	public static int[] getLocationOnScreen(View v) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		return location;
	}

	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
}
