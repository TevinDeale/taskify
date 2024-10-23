package dev.fiinn.taskify.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String toString(LocalDate localDate) {
        return localDate.toString();
    }

    public static LocalDate parseString(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(date, dateFormatter);
    }

    public static LocalDate now() {
        return LocalDate.now();
    }

    public static Boolean validDateRange(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
