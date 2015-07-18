package weatherforecast.service;

import java.util.Calendar;
import java.util.TimeZone;

public class GetDateTime {

	private static String mYear;
	private static String mMonth;
	private static String mDay;
	private static String hour;
	private static String minute;
	
	public String stringDate() {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		mYear = String.valueOf(cal.get(Calendar.YEAR));
		mMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		mDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		if(mMonth.length() == 1)
			mMonth = "0" + mMonth;
		if(mDay.length() == 1)
			mDay = "0" + mDay;
		
		return mYear + "/" + mMonth + "/" + mDay;
	}
	
	public String stringTime() {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		minute = String.valueOf(cal.get(Calendar.MINUTE));
		if(hour.length() == 1)
			hour = "0" + hour;
		if(minute.length() == 1)
			minute = "0" + minute;
		
		return hour + ":" + minute;
	}
	
}

