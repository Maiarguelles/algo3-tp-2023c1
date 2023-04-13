import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args){
        var calendar = new Calendar();
        var evento1 = new Event("algo3", "materia", false, LocalDateTime.of(2023, 4, 15, 15, 0), 3, null);


        calendar.addEvent(evento1);

    }
}
