package com.landmark.dianping.ui.util.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * 完成以下功能：图片缩放；
 * 
 * @version v1.0
 * 
 * @author landmark
 * 
 */
public class BitmapUtils {

	/**
	 * 加载照相机获得的图片
	 * 
	 * @param context
	 * @param photoUriPath
	 *            图片地址
	 * @return Bitmap
	 */
	public static Bitmap getCameraBitmap(Context context, Uri photoUriPath)
			throws IOException {
		Uri photoUri = photoUriPath;

		int inSampleSize = 8;
		InputStream photoStream = context.getContentResolver().openInputStream(
				photoUri);
		int filesize = photoStream.available();
		Bitmap photoBitmap = null;
		if (filesize < 102400) {
			inSampleSize = 1;
		} else if (filesize < 1024000) {
			inSampleSize = 4;
		} else if (filesize < 3024000) {
			inSampleSize = 6;
		} else {
			inSampleSize = filesize / 12800;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;
		photoBitmap = BitmapFactory.decodeStream(photoStream, null, options);
		return photoBitmap;
	}

	/**
	 * 加载并缩放图片
	 * 
	 * @param context
	 * @param photoUrlPath
	 *            图片地址
	 * @return Bitmap
	 */
	public static Bitmap getResizedBitmap(Context context, String photoUrlPath,
			int width, int height) throws IOException {
		int newHeight = height;
		int newWidth = width;
		Uri photoUri = Uri.parse(photoUrlPath);

		int inSampleSize = 8;
		InputStream photoStream = context.getContentResolver().openInputStream(
				photoUri);
		int filesize = photoStream.available();
		Bitmap photoBitmap = null;
		if (filesize < 102400) {
			inSampleSize = 1;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = inSampleSize;
			photoBitmap = BitmapFactory
					.decodeStream(photoStream, null, options);
		} else {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = inSampleSize;
			photoBitmap = BitmapFactory
					.decodeStream(photoStream, null, options);

			while (options.outHeight < height * inSampleSize
					|| options.outWidth < height * inSampleSize) {
				inSampleSize--;
				if (inSampleSize == 1) {
					break;
				}
			}

			options.inSampleSize = inSampleSize;
			photoBitmap = BitmapFactory
					.decodeStream(photoStream, null, options);
		}

		Bitmap bitmap = null;
		int h = photoBitmap.getHeight();
		int w = photoBitmap.getWidth();
		if ((w < h) && (width > height)) {
			newHeight = width;
			newWidth = height;
		}
		if ((w < newWidth) || (h < newHeight)) {
			BitmapFactory.Options options2 = new BitmapFactory.Options();
			inSampleSize--;
			options2.inSampleSize = inSampleSize;
			InputStream photoStream2 = context.getContentResolver()
					.openInputStream(photoUri);
			bitmap = BitmapFactory.decodeStream(photoStream2, null, options2);
			h = bitmap.getHeight();
			w = bitmap.getWidth();
		} else {
			bitmap = photoBitmap;
		}
		return getResizedBitmap(bitmap, newHeight, newWidth);
	}

	/**
	 * 缩放图片
	 * 
	 * @param bitmap
	 *            原始图片
	 * @param height
	 *            目标高度
	 * @param width
	 *            目标宽度
	 * @return Bitmap
	 */
	public static Bitmap getResizedBitmap(Bitmap bitmap, int height, int width) {
		int h = bitmap.getHeight();
		int w = bitmap.getWidth();
		if ((w >= h) && (w >= width)) {
			float ratio = (float) (height) / h;
			h = height;
			w = (int) (ratio * w);
		} else if ((h > w) && (h > height)) {
			float ratio = (float) (width) / w;
			w = width;
			h = (int) (ratio * h);
		}

		Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
		return resizedBitmap;

		// recreate the new Bitmap
		// Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
		// Bitmap Backgrobmp = Bitmap
		// .createBitmap(width, height, Config.ARGB_4444);
		// Canvas canvas = new Canvas(Backgrobmp);
		// Paint paint = new Paint();
		// paint.setColor(Color.GRAY);
		// paint.setStyle(Style.FILL);
		// canvas.drawColor(Color.BLACK);
		// canvas.drawRect(new Rect(0, 0, width, height), paint);
		// canvas.drawBitmap(resizedBitmap, (width - w) / 2, (height - h) / 2,
		// paint);
		// canvas.save(Canvas.ALL_SAVE_FLAG);
		// canvas.restore();
		// return Backgrobmp;
	}

	/**
	 * 获得圆角图片的方法
	 * 
	 * @param bitmap
	 *            原始图片
	 * @param roundPx
	 *            圆角角度
	 * @return Bitmap
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static Bitmap createImg(byte[] aImgBytes) {
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = false;
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeByteArray(aImgBytes, 0, aImgBytes.length,
					bitopt);
		} catch (OutOfMemoryError e) {
			bit = null;
		}

		int _resizeTimes = 0;
		int w = bitopt.outWidth / 2;
		int h = bitopt.outHeight / 2;
		while (null == bit) {
			bit = Shrinkmethod(aImgBytes, w, h);
			_resizeTimes++;
			if (_resizeTimes > 5) {
				return bit;
			}
			w = w / 2;
			h = h / 2;
		}

		return bit;

	}

	private static Bitmap Shrinkmethod(byte[] aImgByte, int width, int height) {
		Bitmap bit = null;
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(aImgByte, 0, aImgByte.length, bitopt);

		int h = (int) Math.ceil(bitopt.outHeight / width);
		int w = (int) Math.ceil(bitopt.outWidth / height);

		if (h > 1 || w > 1) {
			if (h > w) {
				bitopt.inSampleSize = h;

			} else {
				bitopt.inSampleSize = w;
			}
		}
		bitopt.inJustDecodeBounds = false;
		try {
			bit = BitmapFactory.decodeByteArray(aImgByte, 0, aImgByte.length,
					bitopt);
		} catch (OutOfMemoryError e) {
			bit = null;
		}

		return bit;
	}

	public static Bitmap createImg(File aFile) {
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = false;
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeFile(aFile.getAbsolutePath(), bitopt);
		} catch (OutOfMemoryError e) {
			bit = null;
		}
		if (null == bit) {
			bitopt.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(aFile.getAbsolutePath(), bitopt);
		}
		int _resizeTimes = 0;
		int w = bitopt.outWidth / 2;
		int h = bitopt.outHeight / 2;
		while (null == bit) {
			bit = Shrinkmethod(aFile, w, h);
			_resizeTimes++;
			if (_resizeTimes > 5) {
				return bit;
			}
			w = w / 2;
			h = h / 2;
		}

		return bit;

	}

	public static Bitmap Shrinkmethod(File aFile, int width, int height) {
		Bitmap bit = null;
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(aFile.getAbsolutePath(), bitopt);

		int h = (int) Math.ceil(bitopt.outHeight / width);
		int w = (int) Math.ceil(bitopt.outWidth / height);

		if (h > 1 || w > 1) {
			if (h > w) {
				bitopt.inSampleSize = h;

			} else {
				bitopt.inSampleSize = w;
			}
		}
		bitopt.inJustDecodeBounds = false;
		try {
			bit = BitmapFactory.decodeFile(aFile.getAbsolutePath(), bitopt);
		} catch (OutOfMemoryError e) {
			bit = null;
		}

		return bit;
	}

	/**
	 * 图片转化成圆角
	 * 
	 * @param filePath
	 * @return
	 */
	public static BitmapDrawable getRoundedDrawable(String filePath) {
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		bm = BitmapUtils.getRoundedCornerBitmap(bm, 7.0f);
		return BitmapToDrawable(bm);
	}

	/**
	 * 图片转化成圆角
	 * 
	 * @param filePath
	 * @return
	 */
	public static BitmapDrawable getRoundedDrawable(Drawable d) {
		Bitmap bm = drawableToBitmap(d);
		bm = BitmapUtils.getRoundedCornerBitmap(bm, 7.0f);
		return BitmapToDrawable(bm);
	}

	/**
	 * Bitmap -> byte
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] drawableToByte(Drawable drawable) {
		Bitmap bitmap = drawableToBitmap(drawable);
		return bitmapToByte(bitmap);
	}

	/**
	 * Drawable -> bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bm = bd.getBitmap();
		return bm;
	}

	/**
	 * Bitmap -> byte
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap bitmap) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			bitmap.recycle();
			bitmap = null;
			byte[] array = out.toByteArray();
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * byte[] -> Bitmap
	 * 
	 * @param bytes
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] bytes) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
				null);
		return bitmap;
	}

	/**
	 * bitmap -> Drawable
	 * 
	 * @param bm
	 * @return
	 */
	public static BitmapDrawable BitmapToDrawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}
}
