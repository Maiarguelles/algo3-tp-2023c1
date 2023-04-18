import java.time.LocalDateTime;
import java.util.ArrayList;

public class ByDateEvent extends Event{

    private LocalDateTime expirationDate;
    public ByDateEvent(String title, String description, boolean completeDay, LocalDateTime startDate, int duration, LocalDateTime expirationDate){
        super(title, description, completeDay, startDate, duration);
        isRepeating = true;
        this.expirationDate = expirationDate;
    }

    @Override
    public ArrayList<LocalDateTime> showDatesOfEvent(LocalDateTime date1, LocalDateTime date2){
        var dates = new ArrayList<LocalDateTime>();
        if(expirationDate.isBefore(date1))
            return null;

        else if(expirationDate.isBefore(date2) && expirationDate.isAfter(date1))
            dates = frequencyStrategy.showDatesOfEvents(date1, expirationDate, startDate);
        else
            dates = frequencyStrategy.showDatesOfEvents(date1, date2, startDate);

        return dates;
    }
}
