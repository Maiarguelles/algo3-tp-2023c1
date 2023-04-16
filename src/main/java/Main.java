import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){


        LocalDateTime date0 =LocalDateTime.of(2023,7,3,10,30);
        var z= date0.toLocalDate();

        LocalDate date1 = LocalDate.of(2023,9,30);
        LocalDate date2 = LocalDate.of(2023,8,1);
        var x = z.until(date1);
        var c = z.until(date2);
        System.out.println(x.getDays());
        System.out.println(x.getMonths());

        System.out.println(c.getDays());
        System.out.println(c.getMonths());

        ArrayList<DayOfWeek> weekdays = new ArrayList<>();
        weekdays.add(DayOfWeek.MONDAY);
        weekdays.add(DayOfWeek.THURSDAY);

        LocalDateTime prueba= LocalDateTime.of(2023,4,17,10,20);

        int i = 0;
        while(!weekdays.contains(prueba.getDayOfWeek())) {
            prueba = prueba.plusDays(1);
            System.out.println(prueba.getDayOfWeek());

        }
    }
}
