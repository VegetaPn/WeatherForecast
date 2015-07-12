package weatherforecast.model;

/*
 * 城市天气类
 */
public class CityWeather {
    private String city;   
    private String cityid;
    
    private String index; 
    private String index_d; 
    private String index_tr;
    private String index_xc;
    private String index_uv;
    private String index_co; 
    private String date_y; 
    private String week; 
    private String temp1; 
    private String weather1; 
    private String wind1; 
    
    private String temp2; 
    private String weather2; 
    private String wind2; 
    
    private String temp3; 
    private String weather3; 
    private String wind3; 
    
    private String temp4; 
    private String weather4; 
    private String wind4; 
    
    private String temp5; 
    private String weather5; 
    private String wind5; 
    
    private String temp6; 
    private String weather6; 
    private String wind6; 
    

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


	public String getCity() {
		return city;
	}


	public String getCityid() {
		return cityid;
	}


	public String getIndex() {
		return index;
	}


	public String getIndex_d() {
		return index_d;
	}


	public String getIndex_tr() {
		return index_tr;
	}


	public String getIndex_xc() {
		return index_xc;
	}


	public String getIndex_uv() {
		return index_uv;
	}


	public String getIndex_co() {
		return index_co;
	}


	public String getDate_y() {
		return date_y;
	}


	public String getWeek() {
		return week;
	}


	public String getTemp1() {
		return temp1;
	}


	public String getWeather1() {
		return weather1;
	}


	public String getWind1() {
		return wind1;
	}


	public String getTemp2() {
		return temp2;
	}


	public String getWeather2() {
		return weather2;
	}


	public String getWind2() {
		return wind2;
	}


	public String getTemp3() {
		return temp3;
	}


	public String getWeather3() {
		return weather3;
	}


	public String getWind3() {
		return wind3;
	}


	public String getTemp4() {
		return temp4;
	}


	public String getWeather4() {
		return weather4;
	}


	public String getWind4() {
		return wind4;
	}


	public String getTemp5() {
		return temp5;
	}


	public String getWeather5() {
		return weather5;
	}


	public String getWind5() {
		return wind5;
	}


	public String getTemp6() {
		return temp6;
	}


	public String getWeather6() {
		return weather6;
	}


	public String getWind6() {
		return wind6;
	}
	
	
}
