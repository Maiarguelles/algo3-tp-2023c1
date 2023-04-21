import java.time.LocalDateTime;
import java.util.ArrayList;

public class MonthlyStrategy implements FrequencyStrategy{
    private int frequency;

    public MonthlyStrategy(int frequency){
        this.frequency = frequency;
    }

    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime finalDate, LocalDateTime eventDate){
        return null;
    }

}
