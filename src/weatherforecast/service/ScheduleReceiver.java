package weatherforecast.service;

import weatherforecast.view.WeatherMainActivity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

/**
 * �ճ�֪ͨ�Ľ�����������Ҫ���á�
 * ��Ҫ����Ϊ�����趨ʱ�������OnReceive����
 * @author �����
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
        	.setTitle("�ճ�����")
        	.setMessage(msg)
        	.setPositiveButton("��Ӧ��", new DialogInterface.OnClickListener() { 
		           public void onClick(DialogInterface dialog, int id) { 
		        	   Intent intent = new Intent();
		        	   intent.setClass(m_context, WeatherMainActivity.class);
		        	   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	   m_context.startActivity(intent);
		           } 
		     }) 
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dialog, int id) { 
						dialog.cancel();
					}
			 }); 
        AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
	}

}
