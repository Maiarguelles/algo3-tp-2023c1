import java.time.LocalDateTime;
import java.util.ArrayList;

public class OcurrencesEvent extends Event{
    private int ocurrences;
    public OcurrencesEvent(String title, String description, LocalDateTime startDate,LocalDateTime endDate, boolean completeDay, int ocurrences){
        super(title, description, startDate, endDate, completeDay);
        isRepeating = true;
        this.ocurrences = ocurrences;
    }

    public ArrayList<LocalDateTime> showDatesOfEvent(LocalDateTime date1, LocalDateTime date2){
        LocalDateTime lastPossibleDay= frequencyStrategy.lastDateWithOcurrences(ocurrences, this.startDate);
        if (lastPossibleDay.isBefore((date2)))
            return frequencyStrategy.showDatesOfEvents(date1, lastPossibleDay, startDate);
        else
            return frequencyStrategy.showDatesOfEvents(date1, date2, startDate);

    }

}
