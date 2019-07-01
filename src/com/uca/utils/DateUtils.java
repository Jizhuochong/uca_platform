package com.uca.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	/**
     * 得到今天的临晨时间点 00：00：00
     * 
     * @return Date
     */
    public static Date getDateCriticalForBegin() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    
    /**
     * 得到今天临界时间点 23：59：59
     * 
     * @return Date
     */
    public static Date getDateCriticalForEnd() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
    
    /**
     * 得到今天指定小时、分、秒的时间
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getDateCritical(int hour, int minute, int second){
    	Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        return c.getTime();
    }
    
    /**
     * 为指定时间设置小时、分、秒
     * @param hour
     * @param minute
     * @param second
     * @return
     * @throws ParseException 
     */
    public static Date getDateCritical(String parDate, int hour, int minute, int second) throws ParseException{
    	Calendar c = Calendar.getInstance();
    	if(StringUtils.isNotEmpty(parDate)){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	Date d = sdf.parse(parDate);
        	c.setTime(d);
    	}
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        return c.getTime();
    }
    
    /**
     * 得到指定日期的临晨时间点 00：00：00
     * 
     * @return Date
     */
    public static Date getDateCriticalForBegin(String parDate) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date d = sdf.parse(parDate);
    	Calendar c = Calendar.getInstance();
    	c.setTime(d);
    	c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    
    /**
     * 得到指定日期的临界时间点 23：59：59
     * 
     * @return Date
     */
    public static Date getDateCriticalForEnd(String parDate) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date d = sdf.parse(parDate);
    	Calendar c = Calendar.getInstance();
    	c.setTime(d);
    	c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }
    
    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param sdate 格式yyyy-MM-dd
     * @return String 1=星期日 7=星期六，其他类推
     */
    public static int getWeek(Date sdate) {
        // 再转换为时间
        Calendar c = Calendar.getInstance();
        c.setTime(sdate);
        int week = c.get(Calendar.DAY_OF_WEEK);
//        return new SimpleDateFormat("EEEE").format(c.getTime());
        return week;
    }
    
    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param sdate 格式yyyy-MM-dd
     * @return String 星期日
     */
    public static String getWeekView(Date sdate) {
        // 再转换为时间
        Calendar c = Calendar.getInstance();
        c.setTime(sdate);
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }
    
    public static void main(String[] args){
    	try {
			System.out.println(getWeek(getDateCriticalForEnd("2015-08-01")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     * @param sdate 格式yyyy-MM-dd
     * @return String
     */
    public static String getWeekStr(Date sdate) {
        String str = "";
        switch(getWeek(sdate)){
	        case 1:
	        	str = "日";
	        	break;
	        case 2:
	        	str = "一";
	        	break;
	        case 3:
	        	str = "二";
	        	break;
	        case 4:
	        	str = "三";
	        	break;
	        case 5:
	        	str = "四";
	        	break;
	        case 6:
	        	str = "五";
	        	break;
	        case 7:
	        	str = "六";
	        	break;
	        default:
	        	break;
        }
        return str;
    }
}
