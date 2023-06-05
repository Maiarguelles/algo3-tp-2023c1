import org.junit.Test;


import java.io.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.HashSet;

import static org.junit.Assert.*;

public class CalendarTest {

    //Clases de equivalencia: event = null, event != null

    @Test
    public void addDifferentEvents(){
        //arrange
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);

        var completeEvent = new Event("test", "test", date1, null, true);
        var eventTest = new Event("test", "test", date1, date2, false);



        boolean nullEvent = calendar.addReminder(null);
        boolean completeevent = calendar.addReminder(completeEvent);
        boolean eventtest = calendar.addReminder(eventTest);

        assertFalse(nullEvent);
        assertTrue(completeevent);
        assertTrue(eventtest);
    }

    @Test
    //Clases de equivalencia:
    // la nueva alarma es la primera alarma
    // la nueva alarma es anterior a la actual
    // la nueva alarma es porterior a la actual
    // el nuevo evento no tiene alarma
    // el nuevo evento tiene varias alarmas
    // la nueva alarma luego de eliminar eventos

    public void updateFirstAlarm(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);

        var event1 = new Event("test", "test", date1, null, true);
        var alarm1 = new Alarm(30, null, "minutosantes", date1);

        calendar.addReminder(event1);
        calendar.addAlarmToExistentReminder(event1.hashCode(), alarm1);
        assertEquals(date1.minusMinutes(30), calendar.getNextAlarm(LocalDateTime.of(2023, 1, 1, 0, 0)).getGoOffTime());
    }

    @Test
    public void updateAlarmAfterAddingMoreAlarms(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);
        var alarm1 = new Alarm(30, null, "minutosantes", date1);
        var alarm2 = new Alarm(date1, null, "fechaArbitraria", date1);
        var alarm3 = new Alarm(2880, null, "la que primero suena", date2);

        var event1 = new Event("test", "test", date1, null, true);
        var event2 = new Event("test", "test", date1, date2, false);

        calendar.addReminder(event1);
        calendar.addReminder(event2);
        calendar.addAlarmToExistentReminder(event1.hashCode(), alarm1);
        calendar.addAlarmToExistentReminder(event2.hashCode(), alarm2);

        assertEquals(date1.minusMinutes(30), calendar.getNextAlarm(LocalDateTime.of(2023, 1, 1, 0, 0)).getGoOffTime());
        calendar.addAlarmToExistentReminder(event2.hashCode(), alarm3);
        assertEquals(LocalDateTime.of(2023, 4, 16, 10, 0), calendar.getNextAlarm(LocalDateTime.of(2023, 1, 1, 0, 0)).getGoOffTime());
    }

    @Test
    public void updateAlarmAfterDeletingEvent(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);
        var alarm1 = new Alarm(30, null, "minutosantes", date1);
        var alarm2 = new Alarm(date2, null, "fechaArbitraria", date2);

        var event1 = new Event("test", "test", date1, null, true);
        var event2 = new Event("test", "test", date1, date2, false);

        calendar.addReminder(event1);
        calendar.addReminder(event2);

        calendar.addAlarmToExistentReminder(event1.hashCode(), alarm1);
        calendar.addAlarmToExistentReminder(event2.hashCode(), alarm2);

        calendar.deleteReminder(event1.hashCode());

        assertEquals(date2, calendar.getNextAlarm(LocalDateTime.of(2023, 1, 1, 0, 0)).getGoOffTime());

    }

    @Test
    public void dontUpdateAlarm(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);


        var event1 = new Event("test", "test", date1, null, true);
        calendar.addReminder(event1);
        var alarm1 = new Alarm(30, null, "minutosantes", date1);
        calendar.addAlarmToExistentReminder(event1.hashCode(), alarm1);


        var event2 = new Event("test", "test", date1, date2, false);
        calendar.addReminder(event2);

        assertEquals(date1.minusMinutes(30), calendar.getNextAlarm(LocalDateTime.of(2023, 1, 1, 0, 0)).getGoOffTime());


    }

    @Test
    public void noAlarm(){
        var calendar = new Calendar();
        assertNull(calendar.getNextAlarm(LocalDateTime.of(2023, 1, 1, 0, 0)));
    }


    @Test
    public void setNoTitle(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var event = new Event("test", "test", date1, null, true);

        calendar.addReminder(event);
        event.setTitle("");
        assertEquals("No Title", event.getTitle());
    }

    @Test
    public void updateEventAfterAddingRepetition(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var event1 = new Event("test", "test", date1, null, true);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);

        var date3 = date2.plusDays(7);

        var event2 = new Event("test", "test", date1, date2, false);

        var event3 = new Event("test", "test", date2, date3, false);

        var frequencyStrategy = new DailyStrategy(3);


        calendar.addReminder(event1);
        calendar.addReminder(event2);
        calendar.addReminder(event3);

        calendar.addInfiniteRepetitionToExistentEvent(event1.hashCode(), frequencyStrategy);
        assertNotEquals(calendar.getReminder(event1.hashCode()), event1);

        calendar.addOcurrencesRepetitionToExistentEvent(event2.hashCode(), 20, frequencyStrategy);
        assertNotEquals(calendar.getReminder(event2.hashCode()), event2);

        calendar.addRepetitionByDateToExistentEvent(event3.hashCode(),date3.plusMonths(1), frequencyStrategy);
        assertNotEquals(calendar.getReminder(event3.hashCode()), event3);

    }

    @Test
    public void addRepetitionToAnNonExistentEvent(){
        var calendar = new Calendar();

        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);



        var frequencyStrategy = new DailyStrategy(3);

        assertNull(calendar.addInfiniteRepetitionToExistentEvent(0, frequencyStrategy));
        assertNull(calendar.addOcurrencesRepetitionToExistentEvent(0, 20, frequencyStrategy));
        assertNull(calendar.addRepetitionByDateToExistentEvent(0, date2.plusDays(50), frequencyStrategy));
    }

    @Test
    public void yearlyRepeatedReminderInThreeYears(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);

        var starDate = LocalDateTime.of(2023, 1, 1, 0,0);
        var endDate = LocalDateTime.of(2026, 1,1,0,0);

        var event = new Event("title", "test", date1, date2, false);
        var frequencyStrategy = new YearlyStrategy();
        calendar.addReminder(event);
        calendar.addInfiniteRepetitionToExistentEvent(event.hashCode(), frequencyStrategy);

        int actual = calendar.remindersBetweenTwoDates(starDate, endDate).size();
        assertEquals(3, actual);
    }

    @Test
    public void monthlyRepeatedReminderInThreeMonths(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);

        var starDate = LocalDateTime.of(2023, 4, 1, 0,0);
        var endDate = LocalDateTime.of(2023, 7,1,0,0);

        var event = new Event("title", "test", date1, date2, false);
        var frequencyStrategy = new MonthlyStrategy();
        calendar.addReminder(event);
        calendar.addInfiniteRepetitionToExistentEvent(event.hashCode(), frequencyStrategy);

        int actual = calendar.remindersBetweenTwoDates(starDate, endDate).size();
        assertEquals(3, actual);
    }

    @Test
    public void dailyRepeatedReminderinAMonth(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 2, 20, 30);

        var starDate = LocalDateTime.of(2023, 4, 1, 0,0);
        var endDate = LocalDateTime.of(2023, 5,1,0,0);

        var event = new Event("title", "test", date1, null, true);
        var frequencyStrategy = new DailyStrategy(3);
        calendar.addReminder(event);
        calendar.addInfiniteRepetitionToExistentEvent(event.hashCode(), frequencyStrategy);

        int actual = calendar.remindersBetweenTwoDates(starDate, endDate).size();

        assertEquals(10, actual);
    }

    @Test
    public void dailyRepeatedTaskinAMonth(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 2, 20, 30);

        var starDate = LocalDateTime.of(2023, 4, 1, 0,0);
        var endDate = LocalDateTime.of(2023, 5,1,0,0);

        var task = new Task("title", "test", date1, true);
        calendar.addReminder(task);

        int actual = calendar.remindersBetweenTwoDates(starDate, endDate).size();

        assertEquals(1, actual);
    }

    @Test
    public void weeklyRepeatedEventinAMonth() {
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 1, 20, 30);

        var starDate = LocalDateTime.of(2023, 4, 1, 0, 0);
        var endDate = LocalDateTime.of(2023, 5, 1, 0, 0);

        var event = new Event("title", "test", date1, null, true);
        calendar.addReminder(event);
        var weekDays = new HashSet<DayOfWeek>();
        weekDays.add(DayOfWeek.THURSDAY);
        weekDays.add(DayOfWeek.SUNDAY);
        var frequencyStrategy = new WeeklyStrategy(weekDays);

        calendar.addInfiniteRepetitionToExistentEvent(event.hashCode(), frequencyStrategy);

        int actual = calendar.remindersBetweenTwoDates(starDate, endDate).size();

        assertEquals(9, actual);

    }

    @Test
    public void noRemindersBetweenTwoDates(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 1, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);


        var starDate = LocalDateTime.of(2023, 5, 1, 0, 0);
        var endDate = LocalDateTime.of(2023, 5, 10, 0, 0);

        var event = new Event("title", "test", date1, null, true);
        var task = new Task("title", "test", date2, true);

        calendar.addReminder(event);
        calendar.addReminder(task);
        var frequencyStrategy = new DailyStrategy(3);
        calendar.addRepetitionByDateToExistentEvent(event.hashCode() ,date1.plusDays(15), frequencyStrategy);

        var eventtest = new ArrayList<Event>();

        assertEquals(eventtest, calendar.remindersBetweenTwoDates(starDate, endDate));
    }



    @Test
    public void calendarReadWriteProperly() throws Exception{
        //Arrange
        Alarm alarm = new Alarm(LocalDateTime.of(1234, 2, 3, 10, 4), new Notification(), "gola", LocalDateTime.of(1000, 2, 3, 10, 4));
        Alarm alarm2 = new Alarm(LocalDateTime.of(1234, 2, 3, 10, 4), new Sound(), "gola2", LocalDateTime.of(1000, 2, 3, 10, 4));

        Event event = new Event("hola", "holaaa", LocalDateTime.of(1234, 2, 3, 10, 4), LocalDateTime.of(1234, 2, 3, 10, 4), true);
        event.addAlarm(alarm);
        event.addAlarm(alarm2);
        Event event2 = new Event("hola", "holaaa", LocalDateTime.of(1234, 2, 3, 10, 4), LocalDateTime.of(1234, 2, 3, 10, 4), false);
        event2.addAlarm(alarm2);

        Task task1 = new Task("xd", "xd", LocalDateTime.of(1234, 2, 3, 10, 4), false);
        Calendar expected = new Calendar();

        expected.addReminder(event);
        expected.addReminder(event2);
        expected.addReminder(task1);

        expected.addInfiniteRepetitionToExistentEvent(0, new DailyStrategy(2));

        Alarm alarm3 = new Alarm(LocalDateTime.of(1000, 2, 3, 10, 4), null, "gola3", LocalDateTime.of(1000, 2, 3, 10, 4));
        expected.addAlarmToExistentReminder(event2.hashCode(), alarm3);

        var writer = new StringWriter(); //Para que el test no me cree un archivo, uso un StringWriter para simular un archivo de texto
        var first = expected.writeCalendar(writer);
        writer.close();
        var reader = new StringReader(first);
        var calendar2 = Calendar.readCalendar(reader);
        reader.close();
        var writer2 = new StringWriter();

        assert calendar2 != null;
        var second = calendar2.writeCalendar(writer2); //si escribe lo mismo es porque lo leyo bien
        writer2.close();
        assertEquals(first, second);


    }
}
