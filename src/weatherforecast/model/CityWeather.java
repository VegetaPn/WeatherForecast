package weatherforecast.model;

/**
 * ����������
 * @author �����
 *
 */
public class CityWeather {
    private String city_old;			// ��������   
    private String cityid;			// ����ID
    
    private String index; 			// ����ָ��1
    private String index_d; 		// ����ָ��2
    private String index_tr;		// ����ָ��3
    private String index_xc;		// ����ָ��4
    private String index_uv;		// ����ָ��5
    private String index_co; 		// ����ָ��6
    private String date_y; 			// ��������
    private String week; 			// ��������
    private String temp1; 			// �¶�1
    private String weather1; 		// ����1
    private String wind1; 			// ����1
    
    private String temp2; 			// �¶�2
    private String weather2; 		// ����2
    private String wind2; 			// ����2
    					
    private String temp3;			// �¶�3 
    private String weather3; 		// ����3
    private String wind3; 			// ����3
    
    private String temp4;			// �¶�4 
    private String weather4; 		// ����4
    private String wind4; 			// ����4
    
    private String temp5; 			// �¶�5
    private String weather5; 		// ����5
    private String wind5; 			// ����5
    
    private String temp6; 			// �¶�6
    private String weather6; 		// ����6
    private String wind6; 			// ����6
    
    
    private String aqi, pm10, pm25, qlty;
    private String city, cnty, id, lat, lon;
    private String loc, utc;
    private String sr1, ss1, code_d1, code_n1, txt_d1, txt_n1, date1, hum1, pcpn1, pop1, pres1, max1, min1, vis1, deg1, dir1, sc1, spd1;
    private String sr2, ss2, code_d2, code_n2, txt_d2, txt_n2, date2, hum2, pcpn2, pop2, pres2, max2, min2, vis2, deg2, dir2, sc2, spd2;
    private String sr3, ss3, code_d3, code_n3, txt_d3, txt_n3, date3, hum3, pcpn3, pop3, pres3, max3, min3, vis3, deg3, dir3, sc3, spd3;
    private String sr4, ss4, code_d4, code_n4, txt_d4, txt_n4, date4, hum4, pcpn4, pop4, pres4, max4, min4, vis4, deg4, dir4, sc4, spd4;
    private String sr5, ss5, code_d5, code_n5, txt_d5, txt_n5, date5, hum5, pcpn5, pop5, pres5, max5, min5, vis5, deg5, dir5, sc5, spd5;
    private String sr6, ss6, code_d6, code_n6, txt_d6, txt_n6, date6, hum6, pcpn6, pop6, pres6, max6, min6, vis6, deg6, dir6, sc6, spd6;
    private String sr7, ss7, code_d7, code_n7, txt_d7, txt_n7, date7, hum7, pcpn7, pop7, pres7, max7, min7, vis7, deg7, dir7, sc7, spd7;
    private String hdate1, hhum1, hpop1, hpres1, htmp1, hdeg1, hdir1, hsc1, hspd1;
//    private String hdate2, hhum2, hpop2, hpres2, htmp2, hdeg2, hdir2, hsc2, hspd2;
//    private String hdate3, hhum3, hpop3, hpres3, htmp3, hdeg3, hdir3, hsc3, hspd3;
    private String ncode, ntxt, nfl, nhum, npcpn, npres, ntmp, nvis, ndeg, ndir, nsc, nspd;
    private String status;
    private String brf1, txt1, brf2, txt2, brf3, txt3, brf4, txt4, brf5, txt5, brf6, txt6, brf7, txt7;
    
    

	public CityWeather(String aqi, String pm10, String pm25, String qlty,
			String city, String cnty, String id, String lat, String lon,
			String loc, String utc, String sr1, String ss1, String code_d1,
			String code_n1, String txt_d1, String txt_n1, String date1,
			String hum1, String pcpn1, String pop1, String pres1, String max1,
			String min1, String vis1, String deg1, String dir1, String sc1,
			String spd1, String sr2, String ss2, String code_d2,
			String code_n2, String txt_d2, String txt_n2, String date2,
			String hum2, String pcpn2, String pop2, String pres2, String max2,
			String min2, String vis2, String deg2, String dir2, String sc2,
			String spd2, String sr3, String ss3, String code_d3,
			String code_n3, String txt_d3, String txt_n3, String date3,
			String hum3, String pcpn3, String pop3, String pres3, String max3,
			String min3, String vis3, String deg3, String dir3, String sc3,
			String spd3, String sr4, String ss4, String code_d4,
			String code_n4, String txt_d4, String txt_n4, String date4,
			String hum4, String pcpn4, String pop4, String pres4, String max4,
			String min4, String vis4, String deg4, String dir4, String sc4,
			String spd4, String sr5, String ss5, String code_d5,
			String code_n5, String txt_d5, String txt_n5, String date5,
			String hum5, String pcpn5, String pop5, String pres5, String max5,
			String min5, String vis5, String deg5, String dir5, String sc5,
			String spd5, String sr6, String ss6, String code_d6,
			String code_n6, String txt_d6, String txt_n6, String date6,
			String hum6, String pcpn6, String pop6, String pres6, String max6,
			String min6, String vis6, String deg6, String dir6, String sc6,
			String spd6, String sr7, String ss7, String code_d7,
			String code_n7, String txt_d7, String txt_n7, String date7,
			String hum7, String pcpn7, String pop7, String pres7, String max7,
			String min7, String vis7, String deg7, String dir7, String sc7,
			String spd7, String hdate1, String hhum1, String hpop1,
			String hpres1, String htmp1, String hdeg1, String hdir1,
			String hsc1, String hspd1, String ncode, String ntxt, String nfl,
			String nhum, String npcpn, String npres, String ntmp, String nvis,
			String ndeg, String ndir, String nsc, String nspd, String status,
			String brf1, String txt1, String brf2, String txt2, String brf3,
			String txt3, String brf4, String txt4, String brf5, String txt5,
			String brf6, String txt6, String brf7, String txt7) {
		super();
		this.aqi = aqi;
		this.pm10 = pm10;
		this.pm25 = pm25;
		this.qlty = qlty;
		this.city = city;
		this.cnty = cnty;
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.loc = loc;
		this.utc = utc;
		this.sr1 = sr1;
		this.ss1 = ss1;
		this.code_d1 = code_d1;
		this.code_n1 = code_n1;
		this.txt_d1 = txt_d1;
		this.txt_n1 = txt_n1;
		this.date1 = date1;
		this.hum1 = hum1;
		this.pcpn1 = pcpn1;
		this.pop1 = pop1;
		this.pres1 = pres1;
		this.max1 = max1;
		this.min1 = min1;
		this.vis1 = vis1;
		this.deg1 = deg1;
		this.dir1 = dir1;
		this.sc1 = sc1;
		this.spd1 = spd1;
		this.sr2 = sr2;
		this.ss2 = ss2;
		this.code_d2 = code_d2;
		this.code_n2 = code_n2;
		this.txt_d2 = txt_d2;
		this.txt_n2 = txt_n2;
		this.date2 = date2;
		this.hum2 = hum2;
		this.pcpn2 = pcpn2;
		this.pop2 = pop2;
		this.pres2 = pres2;
		this.max2 = max2;
		this.min2 = min2;
		this.vis2 = vis2;
		this.deg2 = deg2;
		this.dir2 = dir2;
		this.sc2 = sc2;
		this.spd2 = spd2;
		this.sr3 = sr3;
		this.ss3 = ss3;
		this.code_d3 = code_d3;
		this.code_n3 = code_n3;
		this.txt_d3 = txt_d3;
		this.txt_n3 = txt_n3;
		this.date3 = date3;
		this.hum3 = hum3;
		this.pcpn3 = pcpn3;
		this.pop3 = pop3;
		this.pres3 = pres3;
		this.max3 = max3;
		this.min3 = min3;
		this.vis3 = vis3;
		this.deg3 = deg3;
		this.dir3 = dir3;
		this.sc3 = sc3;
		this.spd3 = spd3;
		this.sr4 = sr4;
		this.ss4 = ss4;
		this.code_d4 = code_d4;
		this.code_n4 = code_n4;
		this.txt_d4 = txt_d4;
		this.txt_n4 = txt_n4;
		this.date4 = date4;
		this.hum4 = hum4;
		this.pcpn4 = pcpn4;
		this.pop4 = pop4;
		this.pres4 = pres4;
		this.max4 = max4;
		this.min4 = min4;
		this.vis4 = vis4;
		this.deg4 = deg4;
		this.dir4 = dir4;
		this.sc4 = sc4;
		this.spd4 = spd4;
		this.sr5 = sr5;
		this.ss5 = ss5;
		this.code_d5 = code_d5;
		this.code_n5 = code_n5;
		this.txt_d5 = txt_d5;
		this.txt_n5 = txt_n5;
		this.date5 = date5;
		this.hum5 = hum5;
		this.pcpn5 = pcpn5;
		this.pop5 = pop5;
		this.pres5 = pres5;
		this.max5 = max5;
		this.min5 = min5;
		this.vis5 = vis5;
		this.deg5 = deg5;
		this.dir5 = dir5;
		this.sc5 = sc5;
		this.spd5 = spd5;
		this.sr6 = sr6;
		this.ss6 = ss6;
		this.code_d6 = code_d6;
		this.code_n6 = code_n6;
		this.txt_d6 = txt_d6;
		this.txt_n6 = txt_n6;
		this.date6 = date6;
		this.hum6 = hum6;
		this.pcpn6 = pcpn6;
		this.pop6 = pop6;
		this.pres6 = pres6;
		this.max6 = max6;
		this.min6 = min6;
		this.vis6 = vis6;
		this.deg6 = deg6;
		this.dir6 = dir6;
		this.sc6 = sc6;
		this.spd6 = spd6;
		this.sr7 = sr7;
		this.ss7 = ss7;
		this.code_d7 = code_d7;
		this.code_n7 = code_n7;
		this.txt_d7 = txt_d7;
		this.txt_n7 = txt_n7;
		this.date7 = date7;
		this.hum7 = hum7;
		this.pcpn7 = pcpn7;
		this.pop7 = pop7;
		this.pres7 = pres7;
		this.max7 = max7;
		this.min7 = min7;
		this.vis7 = vis7;
		this.deg7 = deg7;
		this.dir7 = dir7;
		this.sc7 = sc7;
		this.spd7 = spd7;
		this.hdate1 = hdate1;
		this.hhum1 = hhum1;
		this.hpop1 = hpop1;
		this.hpres1 = hpres1;
		this.htmp1 = htmp1;
		this.hdeg1 = hdeg1;
		this.hdir1 = hdir1;
		this.hsc1 = hsc1;
		this.hspd1 = hspd1;
		this.ncode = ncode;
		this.ntxt = ntxt;
		this.nfl = nfl;
		this.nhum = nhum;
		this.npcpn = npcpn;
		this.npres = npres;
		this.ntmp = ntmp;
		this.nvis = nvis;
		this.ndeg = ndeg;
		this.ndir = ndir;
		this.nsc = nsc;
		this.nspd = nspd;
		this.status = status;
		this.brf1 = brf1;
		this.txt1 = txt1;
		this.brf2 = brf2;
		this.txt2 = txt2;
		this.brf3 = brf3;
		this.txt3 = txt3;
		this.brf4 = brf4;
		this.txt4 = txt4;
		this.brf5 = brf5;
		this.txt5 = txt5;
		this.brf6 = brf6;
		this.txt6 = txt6;
		this.brf7 = brf7;
		this.txt7 = txt7;
	}






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
	
	
	
	


	public String getAqi() {
		return aqi;
	}


	public String getPm10() {
		return pm10;
	}


	public String getPm25() {
		return pm25;
	}


	public String getQlty() {
		return qlty;
	}


	public String getCity() {
		return city;
	}


	public String getCnty() {
		return cnty;
	}


	public String getId() {
		return id;
	}


	public String getLat() {
		return lat;
	}


	public String getLon() {
		return lon;
	}


	public String getLoc() {
		return loc;
	}


	public String getUtc() {
		return utc;
	}


	public String getSr1() {
		return sr1;
	}


	public String getSs1() {
		return ss1;
	}


	public String getCode_n1() {
		return code_n1;
	}


	public String getTxt_n1() {
		return txt_n1;
	}


	public String getDate1() {
		return date1;
	}


	public String getHum1() {
		return hum1;
	}


	public String getPcpn1() {
		return pcpn1;
	}


	public String getPop1() {
		return pop1;
	}


	public String getPres1() {
		return pres1;
	}


	public String getMax1() {
		return max1;
	}


	public String getMin1() {
		return min1;
	}


	public String getVis1() {
		return vis1;
	}


	public String getDeg1() {
		return deg1;
	}


	public String getDir1() {
		return dir1;
	}


	public String getSc1() {
		return sc1;
	}


	public String getSpd1() {
		return spd1;
	}


	public String getSr2() {
		return sr2;
	}


	public String getSs2() {
		return ss2;
	}


	public String getCode_n2() {
		return code_n2;
	}


	public String getTxt_n2() {
		return txt_n2;
	}


	public String getDate2() {
		return date2;
	}


	public String getHum2() {
		return hum2;
	}


	public String getPcpn2() {
		return pcpn2;
	}


	public String getPop2() {
		return pop2;
	}


	public String getPres2() {
		return pres2;
	}


	public String getMax2() {
		return max2;
	}


	public String getMin2() {
		return min2;
	}


	public String getVis2() {
		return vis2;
	}


	public String getDeg2() {
		return deg2;
	}


	public String getDir2() {
		return dir2;
	}


	public String getSc2() {
		return sc2;
	}


	public String getSpd2() {
		return spd2;
	}


	public String getSr3() {
		return sr3;
	}


	public String getSs3() {
		return ss3;
	}


	public String getCode_n3() {
		return code_n3;
	}


	public String getTxt_n3() {
		return txt_n3;
	}


	public String getDate3() {
		return date3;
	}


	public String getHum3() {
		return hum3;
	}


	public String getPcpn3() {
		return pcpn3;
	}


	public String getPop3() {
		return pop3;
	}


	public String getPres3() {
		return pres3;
	}


	public String getMax3() {
		return max3;
	}


	public String getMin3() {
		return min3;
	}


	public String getVis3() {
		return vis3;
	}


	public String getDeg3() {
		return deg3;
	}


	public String getDir3() {
		return dir3;
	}


	public String getSc3() {
		return sc3;
	}


	public String getSpd3() {
		return spd3;
	}


	public String getSr4() {
		return sr4;
	}


	public String getSs4() {
		return ss4;
	}


	public String getCode_n4() {
		return code_n4;
	}


	public String getTxt_n4() {
		return txt_n4;
	}


	public String getDate4() {
		return date4;
	}


	public String getHum4() {
		return hum4;
	}


	public String getPcpn4() {
		return pcpn4;
	}


	public String getPop4() {
		return pop4;
	}


	public String getPres4() {
		return pres4;
	}


	public String getMax4() {
		return max4;
	}


	public String getMin4() {
		return min4;
	}


	public String getVis4() {
		return vis4;
	}


	public String getDeg4() {
		return deg4;
	}


	public String getDir4() {
		return dir4;
	}


	public String getSc4() {
		return sc4;
	}


	public String getSpd4() {
		return spd4;
	}


	public String getSr5() {
		return sr5;
	}


	public String getSs5() {
		return ss5;
	}


	public String getCode_n5() {
		return code_n5;
	}


	public String getTxt_n5() {
		return txt_n5;
	}


	public String getDate5() {
		return date5;
	}


	public String getHum5() {
		return hum5;
	}


	public String getPcpn5() {
		return pcpn5;
	}


	public String getPop5() {
		return pop5;
	}


	public String getPres5() {
		return pres5;
	}


	public String getMax5() {
		return max5;
	}


	public String getMin5() {
		return min5;
	}


	public String getVis5() {
		return vis5;
	}


	public String getDeg5() {
		return deg5;
	}


	public String getDir5() {
		return dir5;
	}


	public String getSc5() {
		return sc5;
	}


	public String getSpd5() {
		return spd5;
	}


	public String getSr6() {
		return sr6;
	}


	public String getSs6() {
		return ss6;
	}


	public String getCode_n6() {
		return code_n6;
	}


	public String getTxt_n6() {
		return txt_n6;
	}


	public String getDate6() {
		return date6;
	}


	public String getHum6() {
		return hum6;
	}


	public String getPcpn6() {
		return pcpn6;
	}


	public String getPop6() {
		return pop6;
	}


	public String getPres6() {
		return pres6;
	}


	public String getMax6() {
		return max6;
	}


	public String getMin6() {
		return min6;
	}


	public String getVis6() {
		return vis6;
	}


	public String getDeg6() {
		return deg6;
	}


	public String getDir6() {
		return dir6;
	}


	public String getSc6() {
		return sc6;
	}


	public String getSpd6() {
		return spd6;
	}


	public String getSr7() {
		return sr7;
	}


	public String getSs7() {
		return ss7;
	}


	public String getCode_n7() {
		return code_n7;
	}


	public String getTxt_n7() {
		return txt_n7;
	}


	public String getDate7() {
		return date7;
	}


	public String getHum7() {
		return hum7;
	}


	public String getPcpn7() {
		return pcpn7;
	}


	public String getPop7() {
		return pop7;
	}


	public String getPres7() {
		return pres7;
	}


	public String getMax7() {
		return max7;
	}


	public String getMin7() {
		return min7;
	}


	public String getVis7() {
		return vis7;
	}


	public String getDeg7() {
		return deg7;
	}


	public String getDir7() {
		return dir7;
	}


	public String getSc7() {
		return sc7;
	}


	public String getSpd7() {
		return spd7;
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


	/**
	 * @return the city_old
	 */
	public String getCity_old() {
		return city_old;
	}






	/**
	 * @return the hdate1
	 */
	public String getHdate1() {
		return hdate1;
	}






	/**
	 * @return the hhum1
	 */
	public String getHhum1() {
		return hhum1;
	}






	/**
	 * @return the hpop1
	 */
	public String getHpop1() {
		return hpop1;
	}






	/**
	 * @return the hpres1
	 */
	public String getHpres1() {
		return hpres1;
	}






	/**
	 * @return the htmp1
	 */
	public String getHtmp1() {
		return htmp1;
	}






	/**
	 * @return the hdeg1
	 */
	public String getHdeg1() {
		return hdeg1;
	}






	/**
	 * @return the hdir1
	 */
	public String getHdir1() {
		return hdir1;
	}






	/**
	 * @return the hsc1
	 */
	public String getHsc1() {
		return hsc1;
	}






	/**
	 * @return the hspd1
	 */
	public String getHspd1() {
		return hspd1;
	}




//
//
//	/**
//	 * @return the hdate2
//	 */
//	public String getHdate2() {
//		return hdate2;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hhum2
//	 */
//	public String getHhum2() {
//		return hhum2;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hpop2
//	 */
//	public String getHpop2() {
//		return hpop2;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hpres2
//	 */
//	public String getHpres2() {
//		return hpres2;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the htmp2
//	 */
//	public String getHtmp2() {
//		return htmp2;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hdeg2
//	 */
//	public String getHdeg2() {
//		return hdeg2;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hdir2
//	 */
//	public String getHdir2() {
//		return hdir2;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hsc2
//	 */
//	public String getHsc2() {
//		return hsc2;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hspd2
//	 */
//	public String getHspd2() {
//		return hspd2;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hdate3
//	 */
//	public String getHdate3() {
//		return hdate3;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hhum3
//	 */
//	public String getHhum3() {
//		return hhum3;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hpop3
//	 */
//	public String getHpop3() {
//		return hpop3;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hpres3
//	 */
//	public String getHpres3() {
//		return hpres3;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the htmp3
//	 */
//	public String getHtmp3() {
//		return htmp3;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hdeg3
//	 */
//	public String getHdeg3() {
//		return hdeg3;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hdir3
//	 */
//	public String getHdir3() {
//		return hdir3;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hsc3
//	 */
//	public String getHsc3() {
//		return hsc3;
//	}
//
//
//
//
//
//
//	/**
//	 * @return the hspd3
//	 */
//	public String getHspd3() {
//		return hspd3;
//	}
//
//




	/**
	 * @return the ncode
	 */
	public String getNcode() {
		return ncode;
	}






	/**
	 * @return the ntxt
	 */
	public String getNtxt() {
		return ntxt;
	}






	/**
	 * @return the nfl
	 */
	public String getNfl() {
		return nfl;
	}






	/**
	 * @return the nhum
	 */
	public String getNhum() {
		return nhum;
	}






	/**
	 * @return the npcpn
	 */
	public String getNpcpn() {
		return npcpn;
	}






	/**
	 * @return the npres
	 */
	public String getNpres() {
		return npres;
	}






	/**
	 * @return the ntmp
	 */
	public String getNtmp() {
		return ntmp;
	}






	/**
	 * @return the nvis
	 */
	public String getNvis() {
		return nvis;
	}






	/**
	 * @return the ndeg
	 */
	public String getNdeg() {
		return ndeg;
	}






	/**
	 * @return the ndir
	 */
	public String getNdir() {
		return ndir;
	}






	/**
	 * @return the nsc
	 */
	public String getNsc() {
		return nsc;
	}






	/**
	 * @return the nspd
	 */
	public String getNspd() {
		return nspd;
	}






	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}






	/**
	 * @return the brf1
	 */
	public String getBrf1() {
		return brf1;
	}






	/**
	 * @return the txt1
	 */
	public String getTxt1() {
		return txt1;
	}






	/**
	 * @return the brf2
	 */
	public String getBrf2() {
		return brf2;
	}






	/**
	 * @return the txt2
	 */
	public String getTxt2() {
		return txt2;
	}






	/**
	 * @return the brf3
	 */
	public String getBrf3() {
		return brf3;
	}






	/**
	 * @return the txt3
	 */
	public String getTxt3() {
		return txt3;
	}






	/**
	 * @return the brf4
	 */
	public String getBrf4() {
		return brf4;
	}






	/**
	 * @return the txt4
	 */
	public String getTxt4() {
		return txt4;
	}






	/**
	 * @return the brf5
	 */
	public String getBrf5() {
		return brf5;
	}






	/**
	 * @return the txt5
	 */
	public String getTxt5() {
		return txt5;
	}






	/**
	 * @return the brf6
	 */
	public String getBrf6() {
		return brf6;
	}






	/**
	 * @return the txt6
	 */
	public String getTxt6() {
		return txt6;
	}






	/**
	 * @return the brf7
	 */
	public String getBrf7() {
		return brf7;
	}






	/**
	 * @return the txt7
	 */
	public String getTxt7() {
		return txt7;
	}






	/**
	 * @return the code_d1
	 */
	public String getCode_d1() {
		return code_d1;
	}






	/**
	 * @return the txt_d1
	 */
	public String getTxt_d1() {
		return txt_d1;
	}






	/**
	 * @return the code_d2
	 */
	public String getCode_d2() {
		return code_d2;
	}






	/**
	 * @return the txt_d2
	 */
	public String getTxt_d2() {
		return txt_d2;
	}






	/**
	 * @return the code_d3
	 */
	public String getCode_d3() {
		return code_d3;
	}






	/**
	 * @return the txt_d3
	 */
	public String getTxt_d3() {
		return txt_d3;
	}






	/**
	 * @return the code_d4
	 */
	public String getCode_d4() {
		return code_d4;
	}






	/**
	 * @return the txt_d4
	 */
	public String getTxt_d4() {
		return txt_d4;
	}






	/**
	 * @return the code_d5
	 */
	public String getCode_d5() {
		return code_d5;
	}






	/**
	 * @return the txt_d5
	 */
	public String getTxt_d5() {
		return txt_d5;
	}






	/**
	 * @return the code_d6
	 */
	public String getCode_d6() {
		return code_d6;
	}






	/**
	 * @return the txt_d6
	 */
	public String getTxt_d6() {
		return txt_d6;
	}






	/**
	 * @return the code_d7
	 */
	public String getCode_d7() {
		return code_d7;
	}






	/**
	 * @return the txt_d7
	 */
	public String getTxt_d7() {
		return txt_d7;
	}

}
