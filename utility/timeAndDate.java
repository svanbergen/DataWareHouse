package utility;

import java.util.Calendar;
import java.util.GregorianCalendar;

import java.sql.Timestamp;



public class timeAndDate {
	
	
	//with the help of http://www.java2s.com/Tutorial/Java/0340__Database/MakeajavasqlTimestampObjectforaGivenYearMonthDayHour.htm
	//this method gets year, month, day, hour and minute and converts it into an SQL timeStamp
	public static Timestamp makeTimestamp(int year, int month, int day, int hour, int minute) 
			throws Exception, InvalidDateException {
		
			if (year<1900 || month<1 || month >12 || day<1 || day>31 || hour<0 || hour>24 || minute>59 || minute<0){
				throw new InvalidDateException();
			}
		
		
		    Calendar cal = new GregorianCalendar();
		    cal.set(Calendar.YEAR, year);
		    cal.set(Calendar.MONTH, month - 1);
		    cal.set(Calendar.DATE, day);
		    cal.set(Calendar.HOUR_OF_DAY, hour);
		    cal.set(Calendar.MINUTE, minute);
		    cal.set(Calendar.SECOND, 0);
		    cal.set(Calendar.MILLISECOND, 0);

		    return new Timestamp(cal.getTimeInMillis());
		  }


}
