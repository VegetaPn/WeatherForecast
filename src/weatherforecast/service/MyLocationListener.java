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
	
	private String cityName = "";				// ��������
	private String districtName = "";			// ��������
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
	 * ����ֵ��
		61 �� GPS��λ���
		62 �� ɨ�����϶�λ����ʧ�ܡ���ʱ��λ�����Ч��
		63 �� �����쳣��û�гɹ���������������󡣴�ʱ��λ�����Ч��
		65 �� ��λ����Ľ����
		66 �� ���߶�λ�����ͨ��requestOfflineLocaiton����ʱ��Ӧ�ķ��ؽ��
		67 �� ���߶�λʧ�ܡ�ͨ��requestOfflineLocaiton����ʱ��Ӧ�ķ��ؽ��
		68 �� ��������ʧ��ʱ�����ұ������߶�λʱ��Ӧ�ķ��ؽ��
		161�� ��ʾ���綨λ���
		162~167�� ����˶�λʧ��
		502��key��������
		505��key�����ڻ��߷Ƿ�
		601��key���񱻿������Լ�����
		602��key mcode��ƥ��
		501��700��key��֤ʧ��
	 */
	public int getErrcode() {
		return errcode;
	}
}