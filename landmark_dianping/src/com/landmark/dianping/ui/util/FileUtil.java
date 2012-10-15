package com.landmark.dianping.ui.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;

/**
 * 文件修改，追加，拷贝，各种重命名。SD卡是否存在。验证文件名是否合法。
 * 
 * @version v1.0
 * 
 * @author neil lizhize
 * 
 */
public class FileUtil {

	/**
	 * SD卡路径
	 */
	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	/** 日志文件路径 **/
	public static final String LOG_FILE_PATH = SDCARD_PATH + "/data/log/";

	/****
	 * 重命名文件，不改变文件后缀。
	 * 
	 * @param aFromFilePath
	 *            源文件。
	 * @param aToFileName
	 *            目标名称。
	 * @return
	 */
	public static String reNameFilePrefix(String aFromFilePath,
			String aToFileName) {
		File f = new File(aFromFilePath);
		if (!f.exists()) {
			return null;
		}
		int _pathLength = aFromFilePath.indexOf(f.getParent());
		String _fileName = aFromFilePath.substring(_pathLength);
		String _ext = null;
		if (_fileName != null) {
			int _dot = _fileName.lastIndexOf(".");
			_ext = _fileName.substring(_dot);
		}
		String _newPath = f.getParent() + File.separatorChar + aToFileName
				+ _ext;
		File _toFile = new File(_newPath);
		if (_toFile.exists()) {
			return null;
		} else if (f.renameTo(_toFile)) {
			return _toFile.getAbsolutePath();
		}
		return null;
	}

	/****
	 * 重命名文件
	 * 
	 * @param aFromFilePath
	 *            文件源
	 * @param aToFilePath
	 *            目标文件
	 * @return TRUE 成功
	 */
	public static boolean reNameFile(String aFromFilePath, String aToFilePath) {
		File _formFile = new File(aFromFilePath);
		if (!_formFile.exists()) {
			return false;
		}
		File _toFile = new File(aToFilePath);
		if (_toFile.exists()) {
			return false;
		} else {
			return _formFile.renameTo(new File(aToFilePath));
		}
	}

	/***
	 * 验证文件名是否合法，将不合法的用下划线替换掉。
	 * 
	 * @return 正确的文件名
	 * */
	public static String validFileName(String inputFileNameStr) {
		String patternStr = "[" + '/' + '\n' + '\r' + ' ' + '\t' + '\0' + '\f'
				+ '`' + '?' + '*' + '\\' + '<' + '>' + '|' + '\"' + ':' + "]";
		String replacementStr = "_";
		// Compile regular expression
		Pattern pattern = Pattern.compile(patternStr);
		// Replace all occurrences of pattern in input
		Matcher matcher = pattern.matcher(inputFileNameStr);
		String output = matcher.replaceAll(replacementStr);
		return output;
	}

	/**
	 * 读出文件的内容
	 * 
	 * @param filePath
	 *            文件的路径。
	 * @return 文件的二进制内容
	 * */
	public static byte[] getFileContent(String filePath) {
		File file = FileUtil.createNewFile(filePath);
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			int length = (int) file.length();
			byte[] temp = new byte[length];
			in.read(temp, 0, length);
			in.close();
			return temp;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			file = null;
		}
		return null;
	}

	/**
	 * 拷贝文件
	 * 
	 * @param fromPath
	 *            被拷贝文件路径。
	 * @param toPath
	 *            要拷贝到的路径。
	 * @return 成功TRUE 失败FALSE
	 * */
	public static boolean copyfile(String fromPath, String toPath) {
		try {
			pipe(new FileInputStream(fromPath), new FileOutputStream(toPath,
					false));
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	/***
	 * 为新文件追加新内容。
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param bytes
	 *            文件的内容
	 * */
	public static void append2File(String filePath, byte[] bytes) {
		File f = FileUtil.createNewFile(filePath);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f, true);
			fout.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fout = null;
			f = null;
		}

	}

	/***
	 * 为新文件追加新内容。
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param fileName
	 *            文件的名称
	 * @param bytes
	 *            文件的内容
	 * */
	public static void append2File(String filePath, String fileName,
			byte[] bytes) {
		File f = FileUtil.createNewFile(filePath, fileName);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f, true);
			fout.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fout = null;
			f = null;
		}
	}

	/***
	 * 创建一个新文件。覆盖原有文件
	 * 
	 * @param filePath
	 *            文件的路径
	 * @param bytes
	 *            文件的内容
	 * */
	public static void createNewFile(String filePath, byte[] bytes) {
		File f = FileUtil.createNewFile(filePath);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f, false);
			fout.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fout = null;
			f = null;
		}
	}

	/***
	 * 创建一个新文件。覆盖原有文件
	 * 
	 * @param filePath
	 *            文件的路径
	 * 
	 * @param fileName
	 *            文件的名称
	 * @param bytes
	 *            文件的内容
	 * */
	public static void createNewFile(String filePath, String fileName,
			byte[] bytes) {
		File f = FileUtil.createNewFile(filePath, fileName);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f, false);
			fout.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fout = null;
			f = null;
		}
	}

	/***
	 * 从一个输入流input 把数据拷到另一个输出流 output
	 * 
	 * @param
	 * */
	private static void pipe(InputStream in, OutputStream out)
			throws IOException {
		byte[] buf = new byte[1024];
		int nread;
		synchronized (in) {
			while ((nread = in.read(buf, 0, buf.length)) >= 0) {
				out.write(buf, 0, nread);
			}
		}
		out.flush();
		buf = null;
	}

	/**
	 * 创建目录
	 * 
	 * @param filePath
	 *            目录路径
	 * 
	 */
	public static boolean createFolder(String filePath) {
		File f = new File(filePath);
		if (f.exists()) {
			return true;
		} else {
			f.mkdirs();
			return false;
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static File createNewFile(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}

	/**
	 * 创建文件
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static File createNewFile(String filePath, String fileName) {
		FileUtil.createFolder(filePath);
		File f = new File(filePath, fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}

	/**
	 * 文件是否存在
	 * 
	 * @param path
	 * @return 存在TRUE 不存在FALSE
	 * */
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
	 * sdcard是否存在。
	 * 
	 * @return 存在TRUE 不存在FALSE
	 * */
	public static boolean isExistSdCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

	/**
	 * 文件的路径。
	 * 
	 * @param filePath
	 * 
	 * @return
	 */
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	/**
	 * 文件的绝对路径。
	 * 
	 * @param filePath
	 * 
	 * @return
	 */
	public static String getFilePath(String filePath) {
		File file = new File(filePath);
		return file.getAbsolutePath();
	}

	/**
	 * 判断目录是否存在
	 * 
	 * @param filePath
	 *            目录名称。
	 * @return 是则返回TRUE
	 */
	public static boolean isDirectory(String filePath) {
		File file = new File(filePath);
		if (file.isDirectory())
			return true;
		return false;
	}

	/**
	 * 删除一个文件
	 * 
	 * @param path
	 * @return 己删除则返回TRUE 没删除返回FALSE
	 * */
	public static boolean delFile(String path) {
		if (path == null) {
			return false;
		}
		File f = new File(path);
		if (f.exists()) {
			return f.delete();
		}
		return false;
	}

	/**
	 * 删除一个文件或目录。
	 * 
	 * @param fileDirPath
	 */
	public static void delFiles(String fileDirPath) {
		if (isEixstsFile(fileDirPath)) {
			try {
				Runtime.getRuntime().exec("rm -r " + fileDirPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
