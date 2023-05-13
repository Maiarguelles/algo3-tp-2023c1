import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Alarm alarm = new Alarm(LocalDateTime.of(1234,2,3,10,4),new Notification(), "gola", LocalDateTime.of(1000,2,3,10,4));
        Alarm alarm2 = new Alarm(LocalDateTime.of(1234,2,3,10,4),new Notification(), "gola2", LocalDateTime.of(1000,2,3,10,4));

        Event event = new Event("hola" , "holaaa", LocalDateTime.now(), LocalDateTime.now(), true);
        event.addAlarm(alarm);
        event.addAlarm(alarm2);

        alarm.writeAlarm();
    }
}