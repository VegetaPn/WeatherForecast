package weatherforecast.view;



import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class testSucceed extends Activity{
private Button btn;
UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.succeed);
		btn=(Button) findViewById(R.id.btn_u);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//mController.openShare(testSucceed.this, false);
//				mController.postShare(testSucceed.this, SHARE_MEDIA.RENREN, new SnsPostListener() {
//					@Override
//					public void onComplete(SHARE_MEDIA arg0, int arg1,
//					SocializeEntity arg2) {
//					Toast.makeText(testSucceed.this, "分享完成", Toast.LENGTH_SHORT).show();
//					}
//					@Override
//					public void onStart() {
//					Toast.makeText(testSucceed.this, "开始分享", Toast.LENGTH_SHORT).show();
//					}
//					
//					});
				String appID = "wx967daebe835fbeac";
				String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
				// 添加微信平台
				UMWXHandler wxHandler = new UMWXHandler(testSucceed.this,appID,appSecret);
				wxHandler.addToSocialSDK();
				// 添加微信朋友圈
				UMWXHandler wxCircleHandler = new UMWXHandler(testSucceed.this,appID,appSecret);
				wxCircleHandler.setToCircle(true);
				wxCircleHandler.addToSocialSDK();
				mController.setShareContent("我在海淀，天气");
				mController.openShare(testSucceed.this, new SnsPostListener() {
					@Override
					public void onStart() {
					//分享开始
					}
					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
					//分享结束，eCode==200代表分享成功，非200代表分享失败
					}
					});
				}
			
			
			
		});
	}

}
