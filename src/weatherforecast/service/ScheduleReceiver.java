package weatherforecast.service;

import weatherforecast.view.WeatherMainActivity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

/**
 * 日程通知的接受器。不需要调用。
 * 主要功能为到达设定时间后启动OnReceive函数
 * @author 延昊南
 *
 */
public class ScheduleReceiver extends BroadcastReceiver {
	private Context m_context;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		m_context = context;
		String  msg = intent.getStringExtra("msg");  
		
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
        	.setCancelable(false)
        	.setTitle("日程提醒")
        	.setMessage(msg)
        	.setPositiveButton("打开应用", new DialogInterface.OnClickListener() { 
		           public void onClick(DialogInterface dialog, int id) { 
		        	   Intent intent = new Intent();
		        	   intent.setClass(m_context, WeatherMainActivity.class);
		        	   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	   m_context.startActivity(intent);
		           } 
		     }) 
			.setNegativeButton("取消", new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dialog, int id) { 
						dialog.cancel();
					}
			 }); 
        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
	}

}
