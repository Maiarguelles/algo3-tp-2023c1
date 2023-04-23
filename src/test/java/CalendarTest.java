import org.junit.Test;

import java.time.LocalDateTime;

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
        calendar.addAlarmToExistentEvent(event1, alarm1);
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
        calendar.addAlarmToExistentEvent(event1, alarm1);
        calendar.addAlarmToExistentEvent(event2, alarm2);

        assertEquals(date1.minusMinutes(30), calendar.nextAlarm());
        calendar.addAlarmToExistentEvent(event2, alarm3);
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

        calendar.addAlarmToExistentEvent(event1, alarm1);
        calendar.addAlarmToExistentEvent(event2, alarm2);

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
        calendar.addAlarmToExistentEvent(event1, alarm1);


        var event2 = new Event("test", "test", date1, date2, false);
        calendar.addReminder(event2);

        assertEquals(date1.minusMinutes(30), calendar.nextAlarm());


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





}
