package com.aldogrand.kfc.utils.general;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * <p>
 * <b>Title</b> DateUtils
 * </p>
 * <p>
 * <b>Description</b> Utility class for Dates.
 * </p>
 * <p>
 * <b>Company</b> AldoGrand Consultancy Ltd
 * </p>
 * <p>
 * <b>Copyright</b> Copyright (c) 2014
 * </p>
 * 
 * @author Aldo Grand
 * @version 1.0
 *
 */
public class DateUtils {

	private static final Logger logger = LogManager.getLogger(DateUtils.class);

	/**
	 * Private default constructor.
	 */
	private DateUtils() {
		super();
	}

	/**
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		Date date = null;

		if(strDate == null) throw new IllegalArgumentException("strDate can not be null");
		if(pattern == null) throw new IllegalArgumentException("pattern can not be null");

		date = parse(strDate, new SimpleDateFormat(pattern));

		return date;
	}

	/**
	 * 
	 * @param strDate
	 * @param dateFormat
	 * @return
	 */
	public static Date parse(String strDate, DateFormat dateFormat) {
		Date date = null;

		if(strDate == null) throw new IllegalArgumentException("strDate can not be null");
		if(dateFormat == null) throw new IllegalArgumentException("dateFormat can not be null");

		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			logger.error(String.format("Error parsing date %s using pattern %s", strDate, dateFormat), e);
		}

		return date;
	}
	
	/**
	 * 
	 * @param strDate 
	 * @return Date
	 */	
	public static Date toTime(String strDate) {
		Date date = null;		
		if (strDate != null) {
			try {
				date = new SimpleDateFormat("HH:mm:ss").parse(strDate);
			} catch (ParseException e) {
				logger.error(String.format("Error to convert string to time %s", strDate), e);
			}
		}
		return date;
	}
}
