package weatherforecast.service;

import weatherforecast.view.WeatherMainActivity;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * 通知栏服务需要用到的类
 * @author 延昊南
 *
 */
public class NotiService {
	
	static final int NOTIFICATION_ID = 0x123;			// 通知ID
	NotificationManager nm;
	private Context m_context;
	
	public NotiService(Context context) {
		m_context = context;
		nm = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
	}
	
	/**
	 * 显示通知，点击跳转到主界面
	 * @param title 通知栏的标题
	 * @param msg	通知栏的消息字符串
	 */
	public void showNotify(String title, String msg){
		
		Intent intent = new Intent(m_context, WeatherMainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(m_context, 0, intent, 0);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(m_context); 
		int id=m_context.getResources().getIdentifier("warning","drawable", m_context.getPackageName());
		mBuilder
			.setAutoCancel(false)
			.setTicker(title)
			.setSmallIcon(id)
			.setContentTitle(title)
			.setContentText(msg)
			.setWhen(System.currentTimeMillis())
			.setContentIntent(pi);
		Notification notity = mBuilder.build();
		nm.notify(NOTIFICATION_ID, notity);
	}
	
	
	
	/** 显示通知栏点击跳转到指定Activity */
//	public void showIntentActivityNotify(){
//		// Notification.FLAG_ONGOING_EVENT --设置常驻 Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
////		notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
//		mBuilder.setAutoCancel(true)//点击后让通知将消失  
//				.setContentTitle("测试标题")
//				.setContentText("点击跳转")
//				.setTicker("点我");
//		//点击的意图ACTION是跳转到Intent
//		Intent resultIntent = new Intent(this, MainActivity.class);
//		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//		mBuilder.setContentIntent(pendingIntent);
//		mNotificationManager.notify(notifyId, mBuilder.build());
//	}
//	
//	/** 显示通知栏点击打开Apk */
//	public void showIntentApkNotify(){
//		// Notification.FLAG_ONGOING_EVENT --设置常驻 Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
////		notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
//		mBuilder.setAutoCancel(true)//点击后让通知将消失  
//				.setContentTitle("下载完成")
//				.setContentText("点击安装")
//				.setTicker("下载完成！");
//		//我们这里需要做的是打开一个安装包
//		Intent apkIntent = new Intent();
//		apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		apkIntent.setAction(android.content.Intent.ACTION_VIEW);
//		//注意：这里的这个APK是放在assets文件夹下，获取路径不能直接读取的，要通过COYP出去在读或者直接读取自己本地的PATH，这边只是做一个跳转APK，实际打不开的
//		String apk_path = "file:///android_asset/cs.apk";
////		Uri uri = Uri.parse(apk_path);
//		Uri uri = Uri.fromFile(new File(apk_path));
//		apkIntent.setDataAndType(uri, "application/vnd.android.package-archive");
//		// context.startActivity(intent);
//		PendingIntent contextIntent = PendingIntent.getActivity(this, 0,apkIntent, 0);
//		mBuilder.setContentIntent(contextIntent);
//		mNotificationManager.notify(notifyId, mBuilder.build());
//	}
}
