package com.example.myapplication.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 存储的目录名/storage/telecom/log(如果SD卡存在)
 * 文件名：V320_s_04-27[13-59-47].txt(04-27[13-59-47]表示月日时分秒)
 */
public class ExceptionManage implements UncaughtExceptionHandler{
	private UncaughtExceptionHandler mDefaultHandler;
	private static ExceptionManage instance;
	private Application application;
	private String mCrashTips;
	public static final String APP_DIR_PATH = Environment.getExternalStorageDirectory().getPath() + "/drawDemo/";
	public static final String LOG_DIR_PATH = APP_DIR_PATH + "log/";
	public static void startCatchException(Application app, String crashTips) {
		if (instance == null) {
			instance = new ExceptionManage();
		}
		instance.setCrashTips(crashTips);
		instance.start(app);
	}

	private void setCrashTips(String crashTips) {
		if (crashTips != null && !crashTips.equals("")) {
			mCrashTips = crashTips;
		}
	}

	private void start(Application app) {
		application = app;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				
			}
			System.exit(0);
		}
	}

	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		new Thread() {
			@Override
			public void run() {
				Context context = application.getApplicationContext();
				saveExceptionLog(context, ex);
				Looper.prepare();
				if (mCrashTips != null) {
//					Toast.makeText(context, mCrashTips, Toast.LENGTH_LONG).show();
				}
				Looper.loop();
			}
		}.start();
		return true;
	}

	private void saveExceptionLog(Context context, Throwable ex) {
		
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String logInfo = writer.toString();
		Log.e("Crash", "app is crash", ex);
		StringBuilder sbLog = new StringBuilder();
		int versonCode = -1;
		try {
			sbLog.append("----------------------\nPhone: ");
			sbLog.append(android.os.Build.MODEL);
			sbLog.append("\nSdk: ");
			sbLog.append(android.os.Build.VERSION.SDK);
			sbLog.append("\nSdk version: ");
			sbLog.append(android.os.Build.VERSION.RELEASE);
			sbLog.append("\n----------------------\n\n");
			PackageInfo info = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
			versonCode = info.versionCode;
		} catch (Exception e) {
		}
		sbLog.append(logInfo);
		logInfo = sbLog.toString();
//		subError(logInfo);
		if (isSdCardExists()) {
			String root = LOG_DIR_PATH;
			File dir = new File(root);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			StringBuilder sbFileName = new StringBuilder();
			
			String date = TimeHelper.getFormatedTime("MM-dd[HH-mm-ss]", System.currentTimeMillis());
			
			if (versonCode != -1) {
				sbFileName.append("V");
				sbFileName.append(versonCode).append("_s");
				sbFileName.append("_");
			}
			sbFileName.append(date);
			sbFileName.append(".txt");
			File logFile = new File(dir, sbFileName.toString());
			try {
				FileOutputStream fos = new FileOutputStream(logFile);
				fos.write(logInfo.getBytes());
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logFile.setLastModified(System.currentTimeMillis());
		}
	}
	
	
	public  boolean isSdCardExists() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	

}
