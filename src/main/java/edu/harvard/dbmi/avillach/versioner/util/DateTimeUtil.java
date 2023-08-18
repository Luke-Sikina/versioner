package edu.harvard.dbmi.avillach.versioner.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDateTime fromSQL(Date date) {
        //TODO: better date
        LocalDate localDate = date.toLocalDate();
        return LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0, 0);
    }

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}
