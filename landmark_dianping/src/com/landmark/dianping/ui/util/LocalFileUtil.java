package com.landmark.dianping.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.landmark.dianping.ui.util.md5.MD5;

public class LocalFileUtil {

	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	/** 表情图片存放 **/
	public static final String FILE_FACE = SDCARD_PATH + "/data/unicom/face/";
	/** 本地图片存放 **/
	public static final String FILE_DIR = SDCARD_PATH + "/data/unicom/image/";
	/** 日志文件路径 **/
	public static final String LOG_FILE_PATH = SDCARD_PATH + "/data/unicom/log/";

	/**
	 * 文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isEixstsFile(String path) {
		if (path == null) {
			return false;
		}
		File f = new File(path);
		if (f.exists())
			return true;
		return false;
	}

	/**
	 * sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExistSdCard() {
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取文件的名字
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	/**
	 * 获取文件的路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFilePath(String filePath) {
		File file = new File(filePath);
		return file.getAbsolutePath();
	}

	/**
	 * 文件是否为目录
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isDirectory(String filePath) {
		File file = new File(filePath);
		if (file.isDirectory())
			return true;
		return false;
	}

	/**
	 * 创建文件目录
	 */
	public static void createNewDir(String fileDir) {
		File f = new File(fileDir);
		if (!f.exists()) {
			f.mkdirs();
		}
	}

	/**
	 * 获取服务器图片在SDcard中地址
	 * 
	 * @param imgUrl
	 * 
	 * @param url
	 *            图片url
	 * @return
	 */
	public static String getPhotoPath(String fileDir, String imgUrl) {
		return fileDir + MD5.toMd5(imgUrl.getBytes());
	}

	/**
	 * 获取默认图片存储路径
	 * 
	 * @param imageUrl
	 * @return
	 */
	public static String getPhotoPath(String imageUrl) {
		return LocalFileUtil.FILE_DIR + MD5.toMd5(imageUrl.getBytes());
	}

	/** =============下面需要整理=================== **/
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：/xx
	 * @param newPath
	 *            String 复制后路径 如：/xx/ss
	 * @return boolean
	 */
	public static boolean copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			String f_new = "";
			File f_old = new File(oldPath);
			if (newPath.endsWith(File.separator)) {
				f_new = newPath + f_old.getName();
			} else {
				f_new = newPath + File.separator + f_old.getName();
			}
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			new File(f_new).createNewFile(); // 如果文件不存在 则建立新文件
			// 文件存在时
			if (f_old.exists()) {
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(f_new);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.flush();
				fs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean createImageFolder(String path) {
		File f = new File(path);
		if (!(f.exists())) {
			f.mkdirs();
			f = null;
		}
		return true;
	}

	public static Bitmap getImageByFilePath(String filePath, int dtype,
			Context context) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;// 图片宽高都为原来的二分之一，即图片为原来的四分之一
		bitmap = BitmapFactory.decodeFile(filePath);
		return bitmap;
	}

	public static boolean delDir(String path) {
		return delDir(new File(path));
	}

	/**
	 * 删除文件夹
	 * 
	 * @param file
	 * @return
	 */
	public static boolean delDir(File f) {
		boolean ret = false;
		try {
			if (f.exists()) {
				File[] files = f.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						if (!delDir(files[i])) {
							return false;
						}
					} else {
						files[i].delete();
					}
				}
				f.delete(); // 删除空文件夹
				ret = true;
			}
		} catch (Exception e) {
			return false;
		}
		return ret;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param path
	 */
	public static void delFiles(String path) {
		if (path != null) {
			File file = new File(path);
			if (file.exists()) {
				try {
					Runtime.getRuntime().exec("rm -r " + path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读写日志文件
	 * 
	 * @param path
	 *            路径
	 * @param bytes
	 *            内容
	 */
	public static void writeLogFile(String fileName, byte[] bytes) {
		File f = FileUtil.createNewFile(LocalFileUtil.LOG_FILE_PATH, fileName);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f);
			fout.write(bytes);
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片到sdcrad
	 * 
	 * @param bytes
	 * @param fileDir
	 * @param imgUrl
	 * @return 图片保存成功与否
	 */
	public static boolean savePhotoToSDCard(byte[] bytes, String fileDir,
			String imgUrl) {
		File f = null;
		FileOutputStream fout = null;
		f = FileUtil.createNewFile(fileDir, MD5.toMd5(imgUrl.getBytes()));
		try {
			fout = new FileOutputStream(f, false);
			fout.write(bytes);
			fout.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fout = null;
			f = null;
		}
		return false;
	}

	/**
	 * 删除sdcard图片
	 * 
	 * @param fileDir
	 * @param imgUrl
	 * @return
	 */
	public static boolean deleteSDcardFile(String fileDir, String imgUrl) {
		File f = new File(fileDir + MD5.toMd5(imgUrl.getBytes()));
		if (f.exists()) {
			f.delete();
			return true;
		}
		return false;
	}
}
