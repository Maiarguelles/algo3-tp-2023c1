import org.junit.Test;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

        assertEquals(nullEvent, false);
        assertEquals(completeevent, true);
        assertEquals(eventtest, true);
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
        calendar.addAlarmToExistentReminder(event1, alarm1);
        assertEquals(date1.minusMinutes(30), calendar.nextAlarm());
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
        calendar.addAlarmToExistentReminder(event1, alarm1);
        calendar.addAlarmToExistentReminder(event2, alarm2);

        assertEquals(date1.minusMinutes(30), calendar.nextAlarm());
        calendar.addAlarmToExistentReminder(event2, alarm3);
        assertEquals(LocalDateTime.of(2023, 4, 16, 10, 0), calendar.nextAlarm());
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

        calendar.addAlarmToExistentReminder(event1, alarm1);
        calendar.addAlarmToExistentReminder(event2, alarm2);

        calendar.deleteReminder(event1);

        assertEquals(date2, calendar.nextAlarm());

    }

    @Test
    public void dontUpdateAlarm(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);


        var event1 = new Event("test", "test", date1, null, true);
        calendar.addReminder(event1);
        var alarm1 = new Alarm(30, null, "minutosantes", date1);
        calendar.addAlarmToExistentReminder(event1, alarm1);


        var event2 = new Event("test", "test", date1, date2, false);
        calendar.addReminder(event2);

        assertEquals(date1.minusMinutes(30), calendar.nextAlarm());


    }

    @Test
    public void noAlarm(){
        var calendar = new Calendar();
        assertEquals(null, calendar.nextAlarm());
    }


    //Exist Event:
    // El evento existe
    // El evento no existe

    @Test
    public void existsEvent(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);

        var event1 = new Event("test", "test", date1, null, true);
        calendar.addReminder(event1);

        assertEquals(true, calendar.existReminder(date1.toLocalDate()));
    }

    @Test
    public void doesntExistEvent(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);

        var event1 = new Event("test", "test", date1, null, true);
        calendar.addReminder(event1);

        assertEquals(false, calendar.existReminder(date2.toLocalDate()));
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

        calendar.addInfiniteRepetitionToExistentEvent(event1, frequencyStrategy);
        assertEquals(false,calendar.searchReminder(event1));

        calendar.addOcurrencesRepetitionToExistentEvent(event2, 20, frequencyStrategy);
        assertEquals(false, calendar.searchReminder(event2));

        calendar.addRepetitionByDateToExistentEvent(event3,date3.plusMonths(1), frequencyStrategy);
        assertEquals(false, calendar.searchReminder(event3));

    }

    @Test
    public void addRepetitionToAnNonExistentEvent(){
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);

        var event= new Event("test", "test", date1, date2, false);

        var frequencyStrategy = new DailyStrategy(3);

        assertEquals(null, calendar.addInfiniteRepetitionToExistentEvent(event, frequencyStrategy));
        assertEquals(null, calendar.addOcurrencesRepetitionToExistentEvent(event, 20, frequencyStrategy));
        assertEquals(null, calendar.addRepetitionByDateToExistentEvent(event, date2.plusDays(50), frequencyStrategy));
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
        calendar.addInfiniteRepetitionToExistentEvent(event, frequencyStrategy);

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
        calendar.addInfiniteRepetitionToExistentEvent(event, frequencyStrategy);

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
        calendar.addInfiniteRepetitionToExistentEvent(event, frequencyStrategy);

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
        var weekDays = new ArrayList<DayOfWeek>();
        weekDays.add(DayOfWeek.THURSDAY);
        weekDays.add(DayOfWeek.SUNDAY);
        var frequencyStrategy = new WeeklyStrategy(weekDays);

        calendar.addInfiniteRepetitionToExistentEvent(event, frequencyStrategy);

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
        calendar.addRepetitionByDateToExistentEvent(event ,date1.plusDays(15), frequencyStrategy);

        var eventtest = new ArrayList<Event>();

        assertEquals(eventtest, calendar.remindersBetweenTwoDates(starDate, endDate));
    }



}
