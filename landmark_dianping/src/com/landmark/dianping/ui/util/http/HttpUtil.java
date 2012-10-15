package com.landmark.dianping.ui.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;

public class HttpUtil {

	public static final int BUFFER_SIZE = 1024;

	protected Context mContext;
	protected String mUrl;

	protected HttpURLConnection httpurlconnection;

	protected final int MAX_RETRY_CNT = 3;
	protected final int MAX_REDIRECT_CNT = 3;

	public HttpUtil(Context context, String url) {
		this.mContext = context;
		this.mUrl = url;
	}

	public byte[] getData() {
		byte[] resultBytes = null;
		URL url;
		int httpStatusCode;
		int retryCnt = 0;
		int redirectCnt = 0;
		byte data[] = new byte[BUFFER_SIZE];
		while (redirectCnt < MAX_REDIRECT_CNT && retryCnt < MAX_RETRY_CNT) {
			try {
				url = new URL(mUrl);
				httpurlconnection = (HttpURLConnection) url.openConnection();
				httpurlconnection.setConnectTimeout(30000);
				httpurlconnection.setReadTimeout(30000);
				httpurlconnection.setRequestMethod("GET");

				httpurlconnection.setDoInput(true);

				httpurlconnection.connect();

				httpStatusCode = httpurlconnection.getResponseCode();

				if (httpStatusCode == 503 && retryCnt < this.MAX_RETRY_CNT) {
					retryCnt++;
					httpurlconnection.disconnect();
					continue;
				}

				if (httpStatusCode == 301 || httpStatusCode == 302
						|| httpStatusCode == 303 || httpStatusCode == 307) {
					redirectCnt++;
					httpurlconnection.disconnect();
					continue;
				}

				if (httpStatusCode == 200) {
					String cType = httpurlconnection.getContentType();
					if (null != cType) {
						if (cType.contains("wml") && (httpStatusCode == 200)) {
							if (redirectCnt <= MAX_REDIRECT_CNT) {
								redirectCnt++;
								httpurlconnection.disconnect();
								continue;
							}
						}
					}

					int bytesRead = 0;
					InputStream indataStream = httpurlconnection
							.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					bytesRead = indataStream.read(data);
					while (-1 != bytesRead) {
						baos.write(data, 0, bytesRead);
						bytesRead = indataStream.read(data);
						redirectCnt = 0;
						retryCnt = 0;
					}

					resultBytes = baos.toByteArray();
					baos.close();
					baos = null;
					httpurlconnection.disconnect();

					return resultBytes;
				} else if (-1 == httpStatusCode) {
					resultBytes = null;
					retryCnt++;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
					}
				} else {
					int bytesRead = 0;
					InputStream indataStream = httpurlconnection
							.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					bytesRead = indataStream.read(data);
					while (-1 != bytesRead) {
						baos.write(data, 0, bytesRead);
						bytesRead = indataStream.read(data);
						redirectCnt = 0;
						retryCnt = 0;
					}

					resultBytes = baos.toByteArray();
					baos.close();
					baos = null;
					httpurlconnection.disconnect();
					retryCnt++;
				}
			} catch (IOException e) {
				resultBytes = null;
				retryCnt++;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		return resultBytes;
	}
}
