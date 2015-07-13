package weatherforecast.dao;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import weatherforecast.model.CityWeather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 此类提供以城市ID查询天气信息的功能
 * 使用方法为JsonDao。getCityWeatherbyCityID(String cityID)
 * 返回CityWeather类的变量
 * @author 延昊南
 *
 */
public class JsonDao {
	
	private static  String preurl = "http://wap.youhubst.com/weather/getweather.php?ID=";	// 请求网址的前部分
	private static CityWeather cityWeather = null; 											// 返回的对象
    private volatile static String strResult = "";											// JSON结果字符串
    private static Thread thread;															// 线程——请求操作必须在线程中进行
	
    /**
     * 通过城市ID得到该城市的天气数据
     * @param cityID 城市ID
     * @return CityWeather对象
     */
	public static CityWeather getCityWeatherbyCityID(String cityID) {
		connServerForResult(cityID);
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parseJson(strResult);
	}
    
	/**
	 * HTTP GET方法获得天气数据
	 * @param strUrl 城市ID字符串
	 */
	private static void connServerForResult(String strUrl) {   
        // HttpGet对象   
        final HttpGet httpRequest = new HttpGet(preurl + strUrl); 

        thread=new Thread(new Runnable()  
        {  
            @Override  
            public void run()  
            {  
                //Log.e("1111", "111111111");  
                // TODO Auto-generated method stub  
            	Bundle bundle = new Bundle();
                
                try {   
                    // HttpClient对象   
                    HttpClient httpClient = new DefaultHttpClient();   
                    // 获得HttpResponse对象   
                    HttpResponse httpResponse = httpClient.execute(httpRequest);   
                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {   
                        // 取得返回的数据    
                        strResult = EntityUtils.toString(httpResponse.getEntity());
                    }
                    else {
						System.out.println("err");
					}
                } catch (ClientProtocolException e) {   
                    e.printStackTrace();   
                } catch (IOException e) { 
                    e.printStackTrace();   
                } 
            }  
        });  
        thread.start();
    }   
	
	/**
	 * 解析请求到的JSON数据，生成CityWeather对象
	 * @param strResult 请求到的JSON字符串
	 * @return CityWeather对象
	 */
	private static CityWeather parseJson(String strResult) {
        try {   
            JSONObject jsonObj = new JSONObject(strResult).getJSONObject("weatherinfo");  
        	//JSONTokener jsonParser = new JSONTokener(strResult);
        	//JSONObject jsonObj = (JSONObject) jsonParser.nextValue();  
            String city = jsonObj.getString("city");   
            String cityid = jsonObj.getString("cityid");
            
            String index = jsonObj.getString("index"); 
            String index_d = jsonObj.getString("index_d"); 
            String index_tr = jsonObj.getString("index_tr");
            String index_xc = jsonObj.getString("index_xc");
            String index_uv = jsonObj.getString("index_uv");
            String index_co = jsonObj.getString("index_co"); 
            String date_y = jsonObj.getString("date_y"); 
            String week = jsonObj.getString("week"); 
            String temp1 = jsonObj.getString("temp1"); 
            String weather1 = jsonObj.getString("weather1"); 
            String wind1 = jsonObj.getString("wind1"); 
            
            String temp2 = jsonObj.getString("temp2"); 
            String weather2 = jsonObj.getString("weather2"); 
            String wind2 = jsonObj.getString("wind2"); 
            
            String temp3 = jsonObj.getString("temp3"); 
            String weather3 = jsonObj.getString("weather3"); 
            String wind3 = jsonObj.getString("wind3"); 
            
            String temp4 = jsonObj.getString("temp4"); 
            String weather4 = jsonObj.getString("weather4"); 
            String wind4 = jsonObj.getString("wind4"); 
            
            String temp5 = jsonObj.getString("temp5"); 
            String weather5 = jsonObj.getString("weather5"); 
            String wind5 = jsonObj.getString("wind5"); 
            
            String temp6 = jsonObj.getString("temp6"); 
            String weather6 = jsonObj.getString("weather6"); 
            String wind6 = jsonObj.getString("wind6"); 
            
            cityWeather = new CityWeather(city, cityid, index, index_d, 
            		index_tr, index_xc, index_uv, index_co, date_y, week, temp1, 
            		weather1, wind1, temp2, weather2, wind2, temp3, weather3, wind3, 
            		temp4, weather4, wind4, temp5, weather5, wind5, temp6, weather6, wind6);
            
        } catch (JSONException e) {   
            e.printStackTrace();   
        }   
        return cityWeather;
    }   
}
