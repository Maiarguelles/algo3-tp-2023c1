import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CalendarTest {

    //Clases de equivalencia: event = null, event != null

    @Test
    public void addEvent(){
        //arrange
        var calendar = new Calendar();
        var date1 = LocalDateTime.of(2023, 4, 17, 20, 30);
        var date2 = LocalDateTime.of(2023, 4, 18, 10, 0);

        var completeEvent = new Event("test", "test", date1, null, true);
        var eventTest = new Event("test", "test", date1, date2, false);



        boolean nullEvent = calendar.addEvent(null);
        boolean completeevent = calendar.addEvent(completeEvent);
        boolean eventtest = calendar.addEvent(eventTest);

        assertEquals(nullEvent, false);
        assertEquals(completeevent, true);
        assertEquals(eventtest, true);
    }



}