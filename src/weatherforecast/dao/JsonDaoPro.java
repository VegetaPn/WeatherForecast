package weatherforecast.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.os.Bundle;
import weatherforecast.model.CityWeather;

public class JsonDaoPro {

	private final static String appkey = "a2258557594344cd90cb1592e4602da9";
	private static String preurl = "https://api.heweather.com/x3/weather?cityid=CN";		// 请求网址的前部分
	private static CityWeather cityWeather = null; 											// 返回的对象
    private volatile static String strResult = "";											// JSON结果字符串
    private static Thread thread;															// 线程——请求操作必须在线程中进行
    
    public static String getWeatherJSON(String cityID) {
		try {
			connServerForResult(cityID);
			thread.join();
			//parseJson(strResult);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return (strResult);
	}
        
    /**
	 * HTTP GET方法获得天气数据
	 * @param strUrl 城市ID字符串 不带CN
	 */
	public static void connServerForResult(final String strUrl) {   
        // HttpGet对象   
        final HttpGet httpRequest = new HttpGet(preurl + strUrl + "&key=" + appkey); 
        
        thread=new Thread(new Runnable()  
        {  
            @Override  
            public void run()  
            {  
                // TODO Auto-generated method stub  
            	Bundle bundle = new Bundle();
            	BufferedReader reader = null;
            	StringBuffer sbf = new StringBuffer();
                
                try {   
                	//设置SSLContext 
                	SSLContext sslcontext = SSLContext.getInstance("TLS"); 
                	sslcontext.init(null, new TrustManager[]{myX509TrustManager}, null);
                	URL url = new URL(preurl + strUrl + "&key=" + appkey);
                	HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                	connection.setSSLSocketFactory(sslcontext.getSocketFactory());
                	connection.setRequestMethod("GET");
                	connection.connect();
                	InputStream is = connection.getInputStream();
                	reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                	String strRead = null;
                	while ((strRead = reader.readLine()) != null) {
                		sbf.append(strRead); sbf.append("\r\n");
                	}
                	reader.close();
                	strResult = sbf.toString();
                } catch (ClientProtocolException e) {   
                    e.printStackTrace();   
                    return;
                } catch (IOException e) { 
                    e.printStackTrace();
                    return;
                } catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            }  
        });  
        thread.start();
    }   
	
	public static CityWeather parseJson(String strResult) {
//		System.out.println(strResult);
        try {   
            JSONObject jsonObj = new JSONObject(strResult);//.getJSONObject("weatherinfo");  
        	//JSONTokener jsonParser = new JSONTokener(strResult);
        	//JSONObject jsonObj = (JSONObject) jsonParser.nextValue();  
            
            JSONArray jsonArr = jsonObj.getJSONArray("HeWeather data service 3.0");
            JSONObject weaObj = (JSONObject) jsonArr.get(0);
            JSONObject aqiObj = weaObj.getJSONObject("aqi");
            JSONObject cityObj = aqiObj.getJSONObject("city");
            
            String aqi = cityObj.getString("aqi");
//            String co = cityObj.getString("co");
//            String no2 = cityObj.getString("no2");
//            String o3 = cityObj.getString("o3");
            String pm10 = cityObj.getString("pm10");
            String pm25 = cityObj.getString("pm25");
            String qlty = cityObj.getString("qlty");
//            String so2 = cityObj.getString("so2");
            
            
            JSONObject basicObj = weaObj.getJSONObject("basic");
            
            String city = basicObj.getString("city");
            String cnty = basicObj.getString("cnty");
            String id = basicObj.getString("id");
            String lat = basicObj.getString("lat");
            String lon = basicObj.getString("lon");
            JSONObject updateObj = basicObj.getJSONObject("update");
            String loc = updateObj.getString("loc");
            String utc = updateObj.getString("utc");
            
            
            JSONArray dailyArr = weaObj.getJSONArray("daily_forecast");
            
            JSONObject dailObj1 = dailyArr.getJSONObject(0);
            JSONObject astroObj1 = dailObj1.getJSONObject("astro");
            
            String sr1 = astroObj1.getString("sr");
            String ss1 = astroObj1.getString("ss");
            
            JSONObject condObj1 = dailObj1.getJSONObject("cond");
            
            // if night, is null
            boolean isDaylight;                  // Experimental var
            String code_d1 = null , code_n1 = null, txt_d1 = null, txt_n1 = null ;
            try {
            	code_d1 = condObj1.getString( "code_d" );
            	code_n1 = condObj1.getString( "code_n" );
            	txt_d1 = condObj1.getString( "txt_d" );
            	txt_n1 = condObj1.getString( "txt_n" );
                           
                isDaylight = true ;
        	} catch (Exception e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        		isDaylight = false ;
        	}
            
            String date1 = dailObj1.getString("date");
            String hum1 = dailObj1.getString("hum");
            String pcpn1 = dailObj1.getString("pcpn");
            String pop1 = dailObj1.getString("pop");
            String pres1 = dailObj1.getString("pres");
            
            JSONObject tmpObj1 = dailObj1.getJSONObject("tmp");
            
            String max1 = tmpObj1.getString("max");
            String min1 = tmpObj1.getString("min");
            
            String vis1 = dailObj1.getString("vis");
            
            JSONObject windObj1 = dailObj1.getJSONObject("wind");
            
            String deg1 = windObj1.getString("deg");
            String dir1 = windObj1.getString("dir");
            String sc1 = windObj1.getString("sc");
            String spd1 = windObj1.getString("spd");
            
            
            JSONObject dailObj2 = dailyArr.getJSONObject(1);
            JSONObject astroObj2 = dailObj2.getJSONObject("astro");
            
            String sr2 = astroObj2.getString("sr");
            String ss2 = astroObj2.getString("ss");
            
            JSONObject condObj2 = dailObj2.getJSONObject("cond");
            
            String code_d2 = condObj1.getString("code_d");
            String code_n2 = condObj2.getString("code_n");
            String txt_d2 = condObj1.getString("txt_d");
            String txt_n2 = condObj2.getString("txt_n");
            
            String date2 = dailObj2.getString("date");
            String hum2 = dailObj2.getString("hum");
            String pcpn2 = dailObj2.getString("pcpn");
            String pop2 = dailObj2.getString("pop");
            String pres2 = dailObj2.getString("pres");
            
            JSONObject tmpObj2 = dailObj2.getJSONObject("tmp");
            
            String max2 = tmpObj2.getString("max");
            String min2 = tmpObj2.getString("min");
            
            String vis2 = dailObj2.getString("vis");
            
            JSONObject windObj2 = dailObj2.getJSONObject("wind");
            
            String deg2 = windObj2.getString("deg");
            String dir2 = windObj2.getString("dir");
            String sc2 = windObj2.getString("sc");
            String spd2 = windObj2.getString("spd");
            
            
            JSONObject dailObj3 = dailyArr.getJSONObject(2);
            JSONObject astroObj3 = dailObj3.getJSONObject("astro");
            
            String sr3 = astroObj3.getString("sr");
            String ss3 = astroObj3.getString("ss");
            
            JSONObject condObj3 = dailObj3.getJSONObject("cond");
            
            String code_d3 = condObj1.getString("code_d");
            String code_n3 = condObj3.getString("code_n");
            String txt_d3 = condObj1.getString("txt_d");
            String txt_n3 = condObj3.getString("txt_n");
            
            String date3 = dailObj3.getString("date");
            String hum3 = dailObj3.getString("hum");
            String pcpn3 = dailObj3.getString("pcpn");
            String pop3 = dailObj3.getString("pop");
            String pres3 = dailObj3.getString("pres");
            
            JSONObject tmpObj3 = dailObj3.getJSONObject("tmp");
            
            String max3 = tmpObj3.getString("max");
            String min3 = tmpObj3.getString("min");
            
            String vis3 = dailObj3.getString("vis");
            
            JSONObject windObj3 = dailObj3.getJSONObject("wind");
            
            String deg3 = windObj3.getString("deg");
            String dir3 = windObj3.getString("dir");
            String sc3 = windObj3.getString("sc");
            String spd3 = windObj3.getString("spd");
            
            
            JSONObject dailObj4 = dailyArr.getJSONObject(3);
            JSONObject astroObj4 = dailObj4.getJSONObject("astro");
            
            String sr4 = astroObj4.getString("sr");
            String ss4 = astroObj4.getString("ss");
            
            JSONObject condObj4 = dailObj4.getJSONObject("cond");
            
            String code_d4 = condObj4.getString("code_d");
            String code_n4 = condObj4.getString("code_n");
            String txt_d4 = condObj4.getString("txt_d");
            String txt_n4 = condObj4.getString("txt_n");
            
            String date4 = dailObj4.getString("date");
            String hum4 = dailObj4.getString("hum");
            String pcpn4 = dailObj4.getString("pcpn");
            String pop4 = dailObj4.getString("pop");
            String pres4 = dailObj4.getString("pres");
            
            JSONObject tmpObj4 = dailObj4.getJSONObject("tmp");
            
            String max4 = tmpObj4.getString("max");
            String min4 = tmpObj4.getString("min");
            
            String vis4 = dailObj4.getString("vis");
            
            JSONObject windObj4 = dailObj4.getJSONObject("wind");
            
            String deg4 = windObj4.getString("deg");
            String dir4 = windObj4.getString("dir");
            String sc4 = windObj4.getString("sc");
            String spd4 = windObj4.getString("spd");
            
            
            JSONObject dailObj5 = dailyArr.getJSONObject(4);
            JSONObject astroObj5 = dailObj5.getJSONObject("astro");
            
            String sr5 = astroObj5.getString("sr");
            String ss5 = astroObj5.getString("ss");
            
            JSONObject condObj5 = dailObj5.getJSONObject("cond");
            
            String code_d5 = condObj5.getString("code_n");
            String code_n5 = condObj5.getString("code_n");
            String txt_d5 = condObj5.getString("txt_n");
            String txt_n5 = condObj5.getString("txt_n");
            
            String date5 = dailObj5.getString("date");
            String hum5 = dailObj5.getString("hum");
            String pcpn5 = dailObj5.getString("pcpn");
            String pop5 = dailObj5.getString("pop");
            String pres5 = dailObj5.getString("pres");
            
            JSONObject tmpObj5 = dailObj5.getJSONObject("tmp");
            
            String max5 = tmpObj5.getString("max");
            String min5 = tmpObj5.getString("min");
            
            String vis5 = dailObj5.getString("vis");
            
            JSONObject windObj5 = dailObj5.getJSONObject("wind");
            
            String deg5 = windObj5.getString("deg");
            String dir5 = windObj5.getString("dir");
            String sc5 = windObj5.getString("sc");
            String spd5 = windObj5.getString("spd");
            
            
            JSONObject dailObj6 = dailyArr.getJSONObject(5);
            JSONObject astroObj6 = dailObj6.getJSONObject("astro");
            
            String sr6 = astroObj6.getString("sr");
            String ss6 = astroObj6.getString("ss");
            
            JSONObject condObj6 = dailObj6.getJSONObject("cond");
            
            String code_d6 = condObj6.getString("code_d");
            String code_n6 = condObj6.getString("code_n");
            String txt_d6 = condObj6.getString("txt_d");
            String txt_n6 = condObj6.getString("txt_n");
            
            String date6 = dailObj6.getString("date");
            String hum6 = dailObj6.getString("hum");
            String pcpn6 = dailObj6.getString("pcpn");
            String pop6 = dailObj6.getString("pop");
            String pres6 = dailObj6.getString("pres");
            
            JSONObject tmpObj6 = dailObj6.getJSONObject("tmp");
            
            String max6 = tmpObj6.getString("max");
            String min6 = tmpObj6.getString("min");
            
            String vis6 = dailObj6.getString("vis");
            
            JSONObject windObj6 = dailObj6.getJSONObject("wind");
            
            String deg6 = windObj6.getString("deg");
            String dir6 = windObj6.getString("dir");
            String sc6 = windObj6.getString("sc");
            String spd6 = windObj6.getString("spd");
            
            
            JSONObject dailObj7 = dailyArr.getJSONObject(6);
            JSONObject astroObj7 = dailObj7.getJSONObject("astro");
            
            String sr7 = astroObj7.getString("sr");
            String ss7 = astroObj7.getString("ss");
            
            JSONObject condObj7 = dailObj7.getJSONObject("cond");
            
            String code_d7 = condObj7.getString("code_d");
            String code_n7 = condObj7.getString("code_n");
            String txt_d7 = condObj7.getString("txt_d");
            String txt_n7 = condObj7.getString("txt_n");
            
            String date7 = dailObj7.getString("date");
            String hum7 = dailObj7.getString("hum");
            String pcpn7 = dailObj7.getString("pcpn");
            String pop7 = dailObj7.getString("pop");
            String pres7 = dailObj7.getString("pres");
            
            JSONObject tmpObj7 = dailObj7.getJSONObject("tmp");
            
            String max7 = tmpObj7.getString("max");
            String min7 = tmpObj7.getString("min");
            
            String vis7 = dailObj7.getString("vis");
            
            JSONObject windObj7 = dailObj7.getJSONObject("wind");
            
            String deg7 = windObj7.getString("deg");
            String dir7 = windObj7.getString("dir");
            String sc7 = windObj7.getString("sc");
            String spd7 = windObj7.getString("spd");
            
            
            JSONArray hourlyArr = weaObj.getJSONArray("hourly_forecast");
            JSONObject hourlyObj1 = hourlyArr.getJSONObject(0);
            
            String hdate1 = hourlyObj1.getString("date");
            String hhum1 = hourlyObj1.getString("hum");
            String hpop1 = hourlyObj1.getString("pop");
            String hpres1 = hourlyObj1.getString("pres");
            String htmp1 = hourlyObj1.getString("tmp");
            
            JSONObject hwindObj1 = hourlyObj1.getJSONObject("wind");
            String hdeg1 = hwindObj1.getString("deg");
            String hdir1 = hwindObj1.getString("dir");
            String hsc1 = hwindObj1.getString("sc");
            String hspd1 = hwindObj1.getString("spd");
            
            
//            JSONObject hourlyObj2 = hourlyArr.getJSONObject(1);
//            
//            String hdate2 = hourlyObj2.getString("date");
//            String hhum2 = hourlyObj2.getString("hum");
//            String hpop2 = hourlyObj2.getString("pop");
//            String hpres2 = hourlyObj2.getString("pres");
//            String htmp2 = hourlyObj2.getString("tmp");
//            
//            JSONObject hwindObj2 = hourlyObj2.getJSONObject("wind");
//            String hdeg2 = hwindObj2.getString("deg");
//            String hdir2 = hwindObj2.getString("dir");
//            String hsc2 = hwindObj2.getString("sc");
//            String hspd2 = hwindObj2.getString("spd");
//            
//            
//            JSONObject hourlyObj3 = hourlyArr.getJSONObject(2);
//            
//            String hdate3 = hourlyObj3.getString("date");
//            String hhum3 = hourlyObj3.getString("hum");
//            String hpop3 = hourlyObj3.getString("pop");
//            String hpres3 = hourlyObj3.getString("pres");
//            String htmp3 = hourlyObj3.getString("tmp");
//            
//            JSONObject hwindObj3 = hourlyObj3.getJSONObject("wind");
//            String hdeg3 = hwindObj3.getString("deg");
//            String hdir3 = hwindObj3.getString("dir");
//            String hsc3 = hwindObj3.getString("sc");
//            String hspd3 = hwindObj3.getString("spd");
            
            
            JSONObject nowObj = weaObj.getJSONObject("now");
            JSONObject condObj = nowObj.getJSONObject("cond");
            
            String ncode = condObj.getString("code");
            String ntxt = condObj.getString("txt");
            
            String nfl = nowObj.getString("fl");
            String nhum = nowObj.getString("hum");
            String npcpn = nowObj.getString("pcpn");
            String npres = nowObj.getString("pres");
            String ntmp = nowObj.getString("tmp");
            String nvis = nowObj.getString("vis");
            
            JSONObject nwindObj = nowObj.getJSONObject("wind");
            
            String ndeg = nwindObj.getString("deg");
            String ndir = nwindObj.getString("dir");
            String nsc = nwindObj.getString("sc");
            String nspd = nwindObj.getString("spd");
            
            
            String status = weaObj.getString("status");
            
            
            JSONObject suggestionObj = weaObj.getJSONObject("suggestion");
            JSONObject comfObj = suggestionObj.getJSONObject("comf");
            
            String brf1 = comfObj.getString("brf");
            String txt1 = comfObj.getString("txt");
            
            JSONObject cwObj = suggestionObj.getJSONObject("cw");
            
            String brf2 = cwObj.getString("brf");
            String txt2 = cwObj.getString("txt");
            
            JSONObject drsgObj = suggestionObj.getJSONObject("drsg");
            
            String brf3 = drsgObj.getString("brf");
            String txt3 = drsgObj.getString("txt");
            
            JSONObject fluObj = suggestionObj.getJSONObject("flu");
            
            String brf4 = fluObj.getString("brf");
            String txt4 = fluObj.getString("txt");
            
            JSONObject sportObj = suggestionObj.getJSONObject("sport");
            
            String brf5 = sportObj.getString("brf");
            String txt5 = sportObj.getString("txt");
            

            JSONObject travObj = suggestionObj.getJSONObject("trav");
            
            String brf6 = travObj.getString("brf");
            String txt6 = travObj.getString("txt");
            
            JSONObject uvObj = suggestionObj.getJSONObject("uv");
            
            String brf7 = uvObj.getString("brf");
            String txt7 = uvObj.getString("txt");
            
//            cityWeather = new CityWeather(city, cityid, index, index_d, 
//            		index_tr, index_xc, index_uv, index_co, date_y, week, temp1, 
//            		weather1, wind1, temp2, weather2, wind2, temp3, weather3, wind3, 
//            		temp4, weather4, wind4, temp5, weather5, wind5, temp6, weather6, wind6);
            
            cityWeather = new CityWeather(aqi, pm10, pm25, qlty, city, cnty, id, lat, lon, loc, utc, 
            		sr1, ss1, code_d1, code_n1, txt_d1, txt_n1, date1, hum1, pcpn1, pop1, pres1, max1, min1, vis1, deg1, dir1, sc1, spd1, 
            		sr2, ss2, code_d2, code_n2, txt_d2, txt_n2, date2, hum2, pcpn2, pop2, pres2, max2, min2, vis2, deg2, dir2, sc2, spd2, 
            		sr3, ss3, code_d3, code_n3, txt_d3, txt_n3, date3, hum3, pcpn3, pop3, pres3, max3, min3, vis3, deg3, dir3, sc3, spd3, 
            		sr4, ss4, code_d4, code_n4, txt_d4, txt_n4, date4, hum4, pcpn4, pop4, pres4, max4, min4, vis4, deg4, dir4, sc4, spd4, 
            		sr5, ss5, code_d5, code_n5, txt_d5, txt_n5, date5, hum5, pcpn5, pop5, pres5, max5, min5, vis5, deg5, dir5, sc5, spd5, 
            		sr6, ss6, code_d6, code_n6, txt_d6, txt_n6, date6, hum6, pcpn6, pop6, pres6, max6, min6, vis6, deg6, dir6, sc6, spd6, 
            		sr7, ss7, code_d7, code_n7, txt_d7, txt_n7, date7, hum7, pcpn7, pop7, pres7, max7, min7, vis7, deg7, dir7, sc7, spd7, 
            		hdate1, hhum1, hpop1, hpres1, htmp1, hdeg1, hdir1, hsc1, hspd1,
            		ncode, ntxt, nfl, nhum, npcpn, npres, ntmp, nvis, ndeg, ndir, nsc, nspd, status, 
            		brf1, txt1, brf2, txt2, brf3, txt3, brf4, txt4, brf5, txt5, brf6, txt6, brf7, txt7);
            System.out.println("parse finished");
        } catch (JSONException e) {  
        	System.out.println("parse error:"+e.getMessage());
            e.printStackTrace();  
            return null;
        }   
        return cityWeather;
    }   
	
	
	private static TrustManager myX509TrustManager = new X509TrustManager() { 
		//
		@Override 
		public X509Certificate[] getAcceptedIssuers() { 
		    return null; 
		} 
		
		@Override 
		public void checkServerTrusted(X509Certificate[] chain, String authType) 
				throws CertificateException { 
		} 
		
		@Override 
		public void checkClientTrusted(X509Certificate[] chain, String authType) 
				throws CertificateException { 
		} 
	};
}
