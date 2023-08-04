package com.example.coffee_project.common.user.custom;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class DateFormatter implements Formatter<Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        SimpleDateFormat dateFormat = createDateFormat(locale);
        return new java.sql.Date(dateFormat.parse(text).getTime());
    }

    @Override
    public String print(Date object, Locale locale) {
        SimpleDateFormat dateFormat = createDateFormat(locale);
        return dateFormat.format(object);
    }

    private SimpleDateFormat createDateFormat(Locale locale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, locale);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}
