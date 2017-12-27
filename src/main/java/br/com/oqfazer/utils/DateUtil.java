package br.com.oqfazer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A classe DateUtil e responsavel por controlar atributos do tipo Date
 * @author Thiago Fortunato
 * @version 1.0
 */
public class DateUtil {

    public static final String DATE = "dd/MM/yyyy";
    public static final String TIME = "HH:mm:ss";
    public static final String SHORT_TIME = "HH:mm";
    public static final String LONG_TIME = "HH:mm:ss.S";
    public static final String DATE_TIME_STRIPE_FORMAT = " dd/MM/yyyy  HH'h':mm'min':ss's'";
    public static final String DATETIME = DATE + " " + TIME;
    public static final String DATETIME_LOG_FORMAT = "ddMMyyHH:mm:ssSSS";
    public static final String DATETIME_LOG_FORMAT_TO_LOG = "dd/MM/yy HH:mm:ss.SSS";
    public static final String DATETIME_BLITZ = "yyyy-MM-dd_HH-mm-ss-SSS";
    public static final String LONG_DATETIME = DATE + " " + LONG_TIME;
    public static final int SECOND = 1000;

    private DateUtil() {
    }

    public static String format(String pattern, long timestamp) {
        return format(pattern, new Date(timestamp));
    }

    public static String format(String pattern, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String format(String pattern, Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date.getTime());
    }

    public static Calendar stringToCalendar(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = format.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String validateFormatDayMonthYear(String dateStr, String separator) throws ParseException {

        if (dateStr.replaceAll(separator, "").trim().isEmpty())
            return null;

        StringBuilder formattedDate = new StringBuilder();
        if (dateStr.contains(separator)) {
            String[] dateFormat = dateStr.split(separator);
            String day = dateFormat[0];
            String month = dateFormat[1];
            String year = dateFormat[2];

            String validateType = "Data";
            formattedDate.append(validateFormat(day, 2, validateType));
            formattedDate.append(separator);
            formattedDate.append(validateFormat(month, 2, validateType));
            formattedDate.append(separator);
            formattedDate.append(validateFormat(year, 4, validateType));

        }

        return formattedDate.toString();
    }

    public static String validateFormatHourMinute(String hourToFormat, String separator) throws ParseException {
        if (hourToFormat.replaceAll(separator, "").trim().isEmpty())
            return null;

        StringBuilder hours = new StringBuilder();
        if (hourToFormat.contains(separator)) {
            String[] hourFormat = hourToFormat.split(separator);
            String hour = hourFormat[0];
            String minute = hourFormat[1];

            String validateType = "Hora";
            hours.append(validateFormat(hour, 2, validateType));
            hours.append(separator);
            hours.append(validateFormat(minute, 2, validateType));
        }

        return hours.toString();
    }

    private static String validateFormat(String rawDate, int numLenght, String formatType) throws ParseException {
        String date = rawDate.trim();
        if (date.isEmpty())
            throw new ParseException("Favor preencher a " + formatType + " corretamente. ", 0);
        String formatPattern = String.format("%%%ds", numLenght);
        return String.format(formatPattern, date).replace(' ', '0');

    }

    public static String calendarToString(Calendar calendar, String pattern) {

        if (CommonUtil.isNullOrEmpty(calendar)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(calendar.getTime());
    }

    public static String toDatetimeLogFormat(Date date) {
        return format(DATETIME_LOG_FORMAT, date);
    }

    public static String toDatetimeLogFormat(Calendar date) {
        return format(DATETIME_LOG_FORMAT, date);
    }

}
