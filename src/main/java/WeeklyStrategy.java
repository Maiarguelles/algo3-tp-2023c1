import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class WeeklyStrategy implements FrequencyStrategy{

    private ArrayList<DayOfWeek> weekDays;

    public WeeklyStrategy( ArrayList<DayOfWeek> weekDays){
        this.weekDays = weekDays;
    }

    @Override
    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime date2, LocalDateTime eventDate) {
        if (eventDate)
    }

    @Override
    public LocalDateTime lastDateWithOcurrences(int ocurrences, LocalDateTime starDate) {
        return null;
    }
}
