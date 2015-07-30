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
 * ֪ͨ��������Ҫ�õ�����
 * @author �����
 *
 */
public class NotiService {
	
	static final int NOTIFICATION_ID = 0x123;			// ֪ͨID
	NotificationManager nm;
	private Context m_context;
	
	public NotiService(Context context) {
		m_context = context;
		nm = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
	}
	
	/**
	 * ��ʾ֪ͨ�������ת��������
	 * @param title ֪ͨ���ı���
	 * @param msg	֪ͨ������Ϣ�ַ���
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
	
	
	
	/** ��ʾ֪ͨ�������ת��ָ��Activity */
//	public void showIntentActivityNotify(){
//		// Notification.FLAG_ONGOING_EVENT --���ó�פ Flag;Notification.FLAG_AUTO_CANCEL ֪ͨ���ϵ����֪ͨ���Զ������֪ͨ
////		notification.flags = Notification.FLAG_AUTO_CANCEL; //��֪ͨ���ϵ����֪ͨ���Զ������֪ͨ
//		mBuilder.setAutoCancel(true)//�������֪ͨ����ʧ  
//				.setContentTitle("���Ա���")
//				.setContentText("�����ת")
//				.setTicker("����");
//		//�������ͼACTION����ת��Intent
//		Intent resultIntent = new Intent(this, MainActivity.class);
//		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//		mBuilder.setContentIntent(pendingIntent);
//		mNotificationManager.notify(notifyId, mBuilder.build());
//	}
//	
//	/** ��ʾ֪ͨ�������Apk */
//	public void showIntentApkNotify(){
//		// Notification.FLAG_ONGOING_EVENT --���ó�פ Flag;Notification.FLAG_AUTO_CANCEL ֪ͨ���ϵ����֪ͨ���Զ������֪ͨ
////		notification.flags = Notification.FLAG_AUTO_CANCEL; //��֪ͨ���ϵ����֪ͨ���Զ������֪ͨ
//		mBuilder.setAutoCancel(true)//�������֪ͨ����ʧ  
//				.setContentTitle("�������")
//				.setContentText("�����װ")
//				.setTicker("������ɣ�");
//		//����������Ҫ�����Ǵ�һ����װ��
//		Intent apkIntent = new Intent();
//		apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		apkIntent.setAction(android.content.Intent.ACTION_VIEW);
//		//ע�⣺��������APK�Ƿ���assets�ļ����£���ȡ·������ֱ�Ӷ�ȡ�ģ�Ҫͨ��COYP��ȥ�ڶ�����ֱ�Ӷ�ȡ�Լ����ص�PATH�����ֻ����һ����תAPK��ʵ�ʴ򲻿���
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
