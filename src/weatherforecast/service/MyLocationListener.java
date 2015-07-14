/**
 * 
 */
package weatherforecast.service;

import weatherforecast.view.MainActivity;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.Text;

/**
 * 提供地理定位服务的类
 * @author 延昊南
 *
 */
public class MyLocationListener implements BDLocationListener {
	
	private String cityName = "";				// 城市名称
	private String districtName = "";			// 区县名称
	int errcode = -1;


	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		
		if (location == null) {
			return ;
		}
		
		cityName = location.getCity();
		districtName = location.getDistrict();
		errcode = location.getLocType();
	}
	
	
	/**
	 * @return the errcode
	 * 返回值：
		61 ： GPS定位结果
		62 ： 扫描整合定位依据失败。此时定位结果无效。
		63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。
		65 ： 定位缓存的结果。
		66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
		67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
		68 ： 网络连接失败时，查找本地离线定位时对应的返回结果
		161： 表示网络定位结果
		162~167： 服务端定位失败
		502：key参数错误
		505：key不存在或者非法
		601：key服务被开发者自己禁用
		602：key mcode不匹配
		501～700：key验证失败
	 */
	public int getErrcode() {
		return errcode;
	}
}