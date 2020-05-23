package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Administrator
 */
public class DateUtil {

    public static String getCurrentTime() {
        Date date = new Date();
        return parseToDefaultDateTimeString(date);
    }

    public static String parseToDefaultDateTimeString(Date date) {
        return parseToFormatDateString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String parseToFormatDateString(Date date, String patten) {
        SimpleDateFormat format = new SimpleDateFormat(patten);
        return date != null ? format.format(date) : "";
    }
}
