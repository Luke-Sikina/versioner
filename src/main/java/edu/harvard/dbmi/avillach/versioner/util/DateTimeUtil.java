package edu.harvard.dbmi.avillach.versioner.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtil {
    public static LocalDateTime fromSQL(Date date) {
        //TODO: better date
        LocalDate localDate = date.toLocalDate();
        return LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0, 0);
    }
}
