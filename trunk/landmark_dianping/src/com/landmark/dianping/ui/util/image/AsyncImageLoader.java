package com.landmark.dianping.ui.util.image;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.landmark.dianping.ui.util.LocalFileUtil;

public class AsyncImageLoader {

	protected static final String TAG = "AsyncImageLoader";

	/**
	 * 为了加快速度，在内存中开启缓存
	 * 
	 * （主要应用于重复图片较多时，或者同一个图片要多次被访问，比如在ListView时来回滚动）
	 */
	private Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

	private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 5,
			TimeUnit.SECONDS, workQueue,
			new ThreadPoolExecutor.CallerRunsPolicy());

	/**
	 * 异步加载网络图片，有缓存
	 * 
	 * @param v
	 * @param url
	 * @param isRoundedCorner
	 */
	public void setViewImage(final ImageView v, final String url) {
		loadDrawable(new AsyncImageLoader.ImageCallback() {
			@Override
			public void imageLoaded(Drawable d, ImageView img) {
				if (d != null && d.getIntrinsicWidth() > 0) {
					v.setImageDrawable(d);
				}
			}
		}, v, url, false);
	}

	/**
	 * 加载图片回调
	 * 
	 * @param callback
	 * @param img
	 * @param imageUrl
	 * 
	 * @return
	 */
	public Drawable loadDrawable(final ImageCallback callback,
			final ImageView img, final String imageUrl, boolean isRound) {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				callback.imageLoaded((Drawable) msg.obj, img);
			}
		};

		threadPool.execute(new ImageThread(handler, imageUrl, isRound));

		return null;
	}

	/**
	 * 异步加载图片
	 * 
	 * @author landmark
	 * 
	 */
	class ImageThread implements Runnable {
		private String imageUrl;
		private Handler handler;

		private boolean isRound = false;

		public ImageThread(Handler handler, String imageUrl, boolean isRound) {
			this.handler = handler;
			this.imageUrl = imageUrl;
			this.isRound = isRound;
		}

		@Override
		public void run() {
			Drawable drawable = null;
			if (imageCache.containsKey(imageUrl)) { // 检查缓冲imageCache是否存在对应的KEY
				SoftReference<Drawable> softReference = imageCache
						.get(imageUrl); // 存在就获取对应的值
				if (softReference.get() != null) {
					drawable = softReference.get();
				}
			} else {
				drawable = loadImageFromUrl(imageUrl, isRound); // 使用下面的方法获取Drawable
				if (drawable != null) {
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable)); // 把图片放到HasMap中
				}
			}
			Message message = handler.obtainMessage(0, drawable);
			handler.sendMessage(message);
		}
	}

	/**
	 * 定义方法链接URL获取输入流，然后转换成Drawable
	 * 
	 * @param imageUrl
	 * @return
	 */
	protected Drawable loadImageFromUrl(String imageUrl, boolean isRound) {
		try {
			if (null == imageUrl) {
				return null;
			}
			String filePath = LocalFileUtil.getPhotoPath(imageUrl);
			if (LocalFileUtil.isEixstsFile(filePath)) {
				if (isRound)
					return BitmapUtils.getRoundedDrawable(filePath);
				return Drawable.createFromPath(filePath);
			} else {
				Drawable d = ImageDownload.getNetWorkDrawable(
						LocalFileUtil.FILE_DIR, imageUrl);
				if (d != null && isRound) {
					return BitmapUtils.getRoundedDrawable(d);
				}
				return d;
			}
		} catch (OutOfMemoryError e) {
			System.gc();
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * call back interface
	 * 
	 * @author landmark
	 * 
	 */
	public interface ImageCallback {
		public void imageLoaded(Drawable d, ImageView img);
	}

}