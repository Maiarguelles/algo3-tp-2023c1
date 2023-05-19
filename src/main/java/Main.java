import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jdi.ArrayReference;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Alarm alarm = new Alarm(LocalDateTime.of(1234,2,3,10,4),new Notification(), "gola", LocalDateTime.of(1000,2,3,10,4));
        Alarm alarm2 = new Alarm(LocalDateTime.of(1234,2,3,10,4), new Sound(), "gola2", LocalDateTime.of(1000,2,3,10,4));

        Event event = new Event("hola" , "holaaa", LocalDateTime.now(), LocalDateTime.now(), true);
        event.addAlarm(alarm);
        event.addAlarm(alarm2);
        Event event2 = new Event("hola" , "holaaa", LocalDateTime.now(), LocalDateTime.now(), false);
        event2.addAlarm(alarm2);

        Task task1 = new Task("xd", "xd", LocalDateTime.now(), false);
        Calendar calendar = new Calendar();

        calendar.addReminder(event);
        System.out.println(event.getID());
        calendar.addReminder(event2);
        System.out.println(event2.getID());
        calendar.addReminder(task1);

        calendar.addInfiniteRepetitionToExistentEvent(0, new DailyStrategy(2));
        var dias = new ArrayList<DayOfWeek>();
        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.MONDAY);
        calendar.addOcurrencesRepetitionToExistentEvent(1, 10, new WeeklyStrategy(dias));

        calendar.writeCalendar(null);
        Calendar copy = calendar.readCalendar(null);

        System.out.println();
        System.out.println();

        Alarm alarm3 = new Alarm(LocalDateTime.of(1000,2,3,10,4),null, "gola3", LocalDateTime.of(1000,2,3,10,4));
        copy.addAlarmToExistentReminder(1, alarm3);
        copy.writeCalendar(null);

    }
}

//hola