import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void Event(){
        //Casos de equivalencia:
        //Un evento completeDay y uno con endDate == startDate.plusHours(24) deben ser lo mismo

        // arrange and act
        LocalDateTime date1 = LocalDateTime.of(2023,4,21,10,0);
        LocalDateTime date2 = date1.plusHours(24);
        LocalDateTime date3 =LocalDateTime.of(2023,4,22,10,0);
        var event1 = new Event("test1", "test1",date1,date2, false);
        var event2 = new Event("test2", "test2",date1, null, true);
        var event2EndDate = event2.getEndDate();


        //assert
        assertEquals(event2EndDate, date3);

    }

    @Test
    public void repeatEvent(){
        //Clases de equivalencia
        //El nuevo evento mantiene entre sus startDate y endDate la misma duracion que el otro
        //El nuevo evento creado a partir de un completeDay event debe tener entre su startDate
        //y endDate 24hs

        //arrange
        LocalDateTime date1 = LocalDateTime.of(2023,4,21,10,0);
        LocalDateTime date2 = date1.plusHours(24);
        LocalDateTime date3 =LocalDateTime.of(2019,7,19,1,0);
        var event1 = new Event("test1", "test1",date1,date2, false);
        var event2 = new Event("test2", "test2",date1, null, true);
        var event2EndDate = event2.getEndDate();
        var minutesExpected = 24*60;

        //act
        var eventRepetition1 = event1.repeatEvent(date3);
        var eventRepetition2 = event2.repeatEvent(date2);

        var event1Duration = event1.getDuration();
        var eventRepetition1Duration = eventRepetition1.getDuration();
        var eventRepetition2Duration = eventRepetition2.getDuration();

        //assert
        assertEquals(event1Duration, eventRepetition1Duration);
        assertEquals(minutesExpected, eventRepetition2Duration);
    }

    @Test
    public void getDuration(){
        //Clases de equivalencia
        //Un evento complete day debe devolver siempre 1440
        //Un evento creado a partir de las dos fechas debe devolver el tiempo correcto
        //tanto si son fechas muy alejadas como no

        //arrange
        var date1 = LocalDateTime.of(2023,4,21,10,30);
        var date2 = LocalDateTime.of(2023,4,21,12,29);
        var date3 = LocalDateTime.of(2023,4,23,10,30);
        var event1 = new Event("test1","test1",date1,date2, false);
        var event2 = new Event("test2","test2",date1,null, true);
        var event3 = new Event("test1","test2",date1,date3, false);
        int expectedDuration1 = 119;
        int expectedDuration2 = 24*60;
        int expectedDuration3 = 2*24*60;

        //
        int duration1 = event1.getDuration();
        int duration2 = event2.getDuration();
        int duration3 = event3.getDuration();

        //
        assertEquals(expectedDuration1, duration1);
        assertEquals(expectedDuration2, duration2);
        assertEquals(expectedDuration3, duration3);


    }

}