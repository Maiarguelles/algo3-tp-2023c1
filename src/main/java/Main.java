import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jdi.ArrayReference;

import javax.swing.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        /*Alarm alarm = new Alarm(LocalDateTime.of(1234, 2, 3, 10, 4), new Notification(), "gola", LocalDateTime.of(1000, 2, 3, 10, 4));
        Alarm alarm2 = new Alarm(LocalDateTime.of(1234, 2, 3, 10, 4), new Sound(), "gola2", LocalDateTime.of(1000, 2, 3, 10, 4));

        Event event = new Event("hola", "holaaa", LocalDateTime.of(1234, 2, 3, 10, 4), LocalDateTime.of(1234, 2, 3, 10, 4), true);
        event.addAlarm(alarm);
        event.addAlarm(alarm2);
        Event event2 = new Event("hola", "holaaa", LocalDateTime.of(1234, 2, 3, 10, 4), LocalDateTime.of(1234, 2, 3, 10, 4), false);
        event2.addAlarm(alarm2);

        Task task1 = new Task("xd", "xd", LocalDateTime.of(1234, 2, 3, 10, 4), false);
        Calendar calendar = new Calendar();

        calendar.addReminder(event);
        calendar.addReminder(event2);
        calendar.addReminder(task1);

        calendar.addInfiniteRepetitionToExistentEvent(event.hashCode(), new DailyStrategy(2));
        var dias = new HashSet<DayOfWeek>();

        dias.add(DayOfWeek.MONDAY);
        calendar.addOcurrencesRepetitionToExistentEvent(event2.hashCode(), 10, new WeeklyStrategy(dias));


        Alarm alarm3 = new Alarm(LocalDateTime.of(1000, 2, 3, 10, 4), null, "gola3", LocalDateTime.of(1000, 2, 3, 10, 4));
        calendar.addAlarmToExistentReminder(event2.hashCode(), alarm3);
        String output = null;

        try (PrintWriter writer = new PrintWriter(new FileWriter("Calendar.json"))) {
            output = calendar.writeCalendar("Calendar.json", writer);
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}

