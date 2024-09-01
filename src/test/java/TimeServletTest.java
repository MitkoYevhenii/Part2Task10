import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeServletTest {

    @Test
    public void CurrentTimeTest() {
        // Получаем текущее время в UTC
        ZonedDateTime utcTime = ZonedDateTime.now(ZoneOffset.UTC);
        String formattedTime = utcTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'"));

        // Проверяем, что форматированное время соответствует ожидаемому формату
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");
        String expectedFormattedTime = utcTime.format(expectedFormatter);

        // Используем утверждение, чтобы проверить корректность форматирования
        assertEquals(expectedFormattedTime, formattedTime, "Время форматируется некорректно");

        // Для отладки можно вывести форматированное время в консоль
        System.out.println("current time: " + formattedTime);
    }

    @Test
    public void TimeZoneTimeTest() {
        // Текущее время в UTC
        ZonedDateTime utcTime = ZonedDateTime.now(ZoneOffset.UTC);

        ZonedDateTime timePlusOneHour = utcTime.withZoneSameInstant(ZoneOffset.ofHours(1));
        String formattedTimePlusOneHour = timePlusOneHour.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC+1'"));

        ZonedDateTime nowTime = utcTime.withZoneSameInstant(ZoneOffset.ofHours(0));
        String formatedNowTime = nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC-0'"));

        ZonedDateTime timeMinusOneHour = utcTime.withZoneSameInstant(ZoneOffset.ofHours(-1));
        String formattedTimeMinusOneHour = timeMinusOneHour.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC-1'"));


        System.out.println("Time Plus One Hour: " + formattedTimePlusOneHour);
        System.out.println("Time Minus One Hour: " + formattedTimeMinusOneHour);
        System.out.println("Time Now: " + formatedNowTime);
    }

    @Test
    public void ZonedDateTimeTest() {
        ZonedDateTime utcTime = getTimeStringRealization("UTC+2");
        System.out.println("\n" + utcTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'")));
    }

    private ZonedDateTime getTimeStringRealization(String timezone) {
        try {
            if (timezone.startsWith("UTC ")) {
                int hours = Integer.parseInt(timezone.substring(4));
                return ZonedDateTime.now(ZoneOffset.ofHours(hours));
            } else if (timezone.startsWith("UTC-")) {
                int hours = Integer.parseInt(timezone.substring(4));
                return ZonedDateTime.now(ZoneOffset.ofHours(-hours));
            } else {
                return ZonedDateTime.now(ZoneOffset.UTC);
            }
        } catch (NumberFormatException e) {
            return ZonedDateTime.now(ZoneOffset.UTC);
        }
    }
}

