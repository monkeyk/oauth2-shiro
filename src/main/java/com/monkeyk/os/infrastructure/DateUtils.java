package com.monkeyk.os.infrastructure;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Shengzhao Li
 */
public abstract class DateUtils {

    /**
     * Default time format :  yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Time format :  yyyy-MM-dd HH:mm
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT = "HH:mm";

    /**
     * Default date format
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * Default month format
     */
    public static final String MONTH_FORMAT = "yyyy-MM";
    /**
     * Default day format
     */
    public static final String DAY_FORMAT = "dd";

    public static final long DAY_MILLISECONDS = (1000l * 60 * 60 * 24);

    //Date pattern,  demo:  2013-09-11
    public static final String DATE_PATTERN = "^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$";


    public static boolean isDate(String dateAsText) {
        return StringUtils.isNotEmpty(dateAsText) && dateAsText.matches(DATE_PATTERN);
    }

    public static Date now() {
        return new Date();
    }

    public static String toDateText(Date date) {
        return toDateText(date, DATE_FORMAT);
    }

    public static String toDateText(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date getDate(String dateText) {
        return getDate(dateText, DATE_FORMAT);
    }


    public static Date getDate(String dateText, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateText);
        } catch (ParseException e) {
            throw new IllegalStateException("Parse date from [" + dateText + "," + pattern + "] failed", e);
        }
    }

    public static String toDateTime(Date date) {
        return toDateText(date, DATE_TIME_FORMAT);
    }


    /**
     * Return current year.
     *
     * @return Current year
     */
    public static int currentYear() {
        return calendar().get(Calendar.YEAR);
    }

    public static Calendar calendar() {
        return Calendar.getInstance();
    }

    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        String dateAsText = toDateText(date);
        String todayAsText = toDateText(now());
        return dateAsText.equals(todayAsText);
    }

    /**
     * Get tow dates period as days,
     * return -1 if the start or end is null
     *
     * @param start Start date
     * @param end   End date
     * @return Period as days or -1
     */
    public static long periodAsDays(Date start, Date end) {
        if (start == null || end == null) {
            return -1;
        }
        start = org.apache.commons.lang.time.DateUtils.truncate(start, Calendar.DAY_OF_MONTH);
        end = org.apache.commons.lang.time.DateUtils.truncate(end, Calendar.DAY_OF_MONTH);

        long periodAsMilliSeconds = end.getTime() - start.getTime();
        return (periodAsMilliSeconds / (DAY_MILLISECONDS));
    }
}