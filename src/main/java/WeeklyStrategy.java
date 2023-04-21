import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class WeeklyStrategy implements FrequencyStrategy{
    private int frequency;
    private ArrayList<DayOfWeek> weekDays;

    public WeeklyStrategy(int frequency, ArrayList<DayOfWeek> weekDays){
        this.frequency = frequency;
        this.weekDays = weekDays;
    }

    @Override
    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime finalDate, LocalDateTime eventDate) {
        return null;
    }
}
