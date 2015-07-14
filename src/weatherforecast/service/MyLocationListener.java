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
 * �ṩ����λ�������
 * @author �����
 *
 */
public class MyLocationListener implements BDLocationListener {
	
	private String cityName = "";
	private String districtName = "";
	int errcode = -1;
	

	/**
	 * @return the errcode
	 */
	public int getErrcode() {
		return errcode;
	}

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
		
		errcode = -2;
		
		if (location == null) {
			errcode = -3;
			return ;
		}
		
		cityName = location.getCity();
		districtName = location.getDistrict();
		errcode = location.getLocType();
	}
}