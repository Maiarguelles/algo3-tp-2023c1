import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args){
        var calendar = new Calendar();
        var evento1 = new Event("algo3", "materia", false, LocalDateTime.of(2023, 4, 15, 15, 0), 3, null);
        var evento2 = new Event("algo2", "materia", false,  LocalDateTime.of(2023, 4, 15, 15, 0), 3, null);

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


    }
}
