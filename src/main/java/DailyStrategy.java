import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DailyStrategy implements FrequencyStrategy{

    private int frequency;

    public DailyStrategy(int frequency){
        this.frequency = frequency;
    }

    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime date2, LocalDateTime eventDate){
        long days = eventDate.until(date1, ChronoUnit.DAYS);

        long numberOfOcurrences = days/frequency;
        if(days%frequency != 0)
            numberOfOcurrences++;

        var correctDate = eventDate.plusDays(frequency*numberOfOcurrences);

        return null;
    }



}
