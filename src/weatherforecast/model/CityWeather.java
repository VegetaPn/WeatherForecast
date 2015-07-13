package weatherforecast.model;

/**
 * 城市天气类
 * @author 延昊南
 *
 */
public class CityWeather {
    private String city;			// 城市名称   
    private String cityid;			// 城市ID
    
    private String index; 			// 天气指数1
    private String index_d; 		// 天气指数2
    private String index_tr;		// 天气指数3
    private String index_xc;		// 天气指数4
    private String index_uv;		// 天气指数5
    private String index_co; 		// 天气指数6
    private String date_y; 			// 当天日期
    private String week; 			// 当天星期
    private String temp1; 			// 温度1
    private String weather1; 		// 天气1
    private String wind1; 			// 风力1
    
    private String temp2; 			// 温度2
    private String weather2; 		// 天气2
    private String wind2; 			// 风力2
    					
    private String temp3;			// 温度3 
    private String weather3; 		// 天气3
    private String wind3; 			// 风力3
    
    private String temp4;			// 温度4 
    private String weather4; 		// 天气4
    private String wind4; 			// 风力4
    
    private String temp5; 			// 温度5
    private String weather5; 		// 天气5
    private String wind5; 			// 风力5
    
    private String temp6; 			// 温度6
    private String weather6; 		// 天气6
    private String wind6; 			// 风力6
    

    
	public CityWeather(String city, String cityid, String index,
			String index_d, String index_tr, String index_xc, String index_uv,
			String index_co, String date_y, String week, String temp1,
			String weather1, String wind1, String temp2, String weather2,
			String wind2, String temp3, String weather3, String wind3,
			String temp4, String weather4, String wind4, String temp5,
			String weather5, String wind5, String temp6, String weather6,
			String wind6) {
		super();
		this.city = city;
		this.cityid = cityid;
		this.index = index;
		this.index_d = index_d;
		this.index_tr = index_tr;
		this.index_xc = index_xc;
		this.index_uv = index_uv;
		this.index_co = index_co;
		this.date_y = date_y;
		this.week = week;
		this.temp1 = temp1;
		this.weather1 = weather1;
		this.wind1 = wind1;
		this.temp2 = temp2;
		this.weather2 = weather2;
		this.wind2 = wind2;
		this.temp3 = temp3;
		this.weather3 = weather3;
		this.wind3 = wind3;
		this.temp4 = temp4;
		this.weather4 = weather4;
		this.wind4 = wind4;
		this.temp5 = temp5;
		this.weather5 = weather5;
		this.wind5 = wind5;
		this.temp6 = temp6;
		this.weather6 = weather6;
		this.wind6 = wind6;
	}



	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}



	/**
	 * @return the cityid
	 */
	public String getCityid() {
		return cityid;
	}



	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}



	/**
	 * @return the index_d
	 */
	public String getIndex_d() {
		return index_d;
	}



	/**
	 * @return the index_tr
	 */
	public String getIndex_tr() {
		return index_tr;
	}



	/**
	 * @return the index_xc
	 */
	public String getIndex_xc() {
		return index_xc;
	}



	/**
	 * @return the index_uv
	 */
	public String getIndex_uv() {
		return index_uv;
	}



	/**
	 * @return the index_co
	 */
	public String getIndex_co() {
		return index_co;
	}



	/**
	 * @return the date_y
	 */
	public String getDate_y() {
		return date_y;
	}



	/**
	 * @return the week
	 */
	public String getWeek() {
		return week;
	}



	/**
	 * @return the temp1
	 */
	public String getTemp1() {
		return temp1;
	}



	/**
	 * @return the weather1
	 */
	public String getWeather1() {
		return weather1;
	}



	/**
	 * @return the wind1
	 */
	public String getWind1() {
		return wind1;
	}



	/**
	 * @return the temp2
	 */
	public String getTemp2() {
		return temp2;
	}



	/**
	 * @return the weather2
	 */
	public String getWeather2() {
		return weather2;
	}



	/**
	 * @return the wind2
	 */
	public String getWind2() {
		return wind2;
	}



	/**
	 * @return the temp3
	 */
	public String getTemp3() {
		return temp3;
	}



	/**
	 * @return the weather3
	 */
	public String getWeather3() {
		return weather3;
	}



	/**
	 * @return the wind3
	 */
	public String getWind3() {
		return wind3;
	}



	/**
	 * @return the temp4
	 */
	public String getTemp4() {
		return temp4;
	}



	/**
	 * @return the weather4
	 */
	public String getWeather4() {
		return weather4;
	}



	/**
	 * @return the wind4
	 */
	public String getWind4() {
		return wind4;
	}



	/**
	 * @return the temp5
	 */
	public String getTemp5() {
		return temp5;
	}



	/**
	 * @return the weather5
	 */
	public String getWeather5() {
		return weather5;
	}



	/**
	 * @return the wind5
	 */
	public String getWind5() {
		return wind5;
	}



	/**
	 * @return the temp6
	 */
	public String getTemp6() {
		return temp6;
	}



	/**
	 * @return the weather6
	 */
	public String getWeather6() {
		return weather6;
	}



	/**
	 * @return the wind6
	 */
	public String getWind6() {
		return wind6;
	}

}
