import java.time.LocalDateTime;
import java.util.ArrayList;

public class MonthlyStrategy implements FrequencyStrategy{

    @Override
    public ArrayList<LocalDateTime> showDatesOfEvents(LocalDateTime date1, LocalDateTime finalDate, LocalDateTime eventDate){
        return null;
    }

    @Override
    public LocalDateTime lastDateWithOcurrences(int ocurrences, LocalDateTime starDate) {
        LocalDateTime lastPossibleDay = null;
        for (int i=0; i<ocurrences; i++){
            lastPossibleDay =starDate.plusMonths(1);
        }
        return lastPossibleDay;
    }

}
