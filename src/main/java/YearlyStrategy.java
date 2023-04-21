import java.time.LocalDateTime;
import java.util.ArrayList;

public class YearlyStrategy implements FrequencyStrategy{

    private int frequency;

    public YearlyStrategy(int frequency){
        this.frequency = frequency;
    }

    @Override
    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime finalDate, LocalDateTime eventDate) {
        return null;
    }
}
