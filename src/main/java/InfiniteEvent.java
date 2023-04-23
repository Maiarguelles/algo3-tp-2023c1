import java.time.LocalDateTime;
import java.util.ArrayList;

public class InfiniteEvent extends Event{

    public InfiniteEvent(String title, String description, boolean completeDay, LocalDateTime startDate,LocalDateTime endDate){
        super(title, description, startDate, endDate, completeDay);
        isRepeating = true;
    }

    @Override
    public void addFrequency(FrequencyStrategy frequencyStrategy){
        this.frequencyStrategy = frequencyStrategy;
    }

    @Override
    public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2){
        return frequencyStrategy.showDatesOfEvents(date1, date2, startDate);
    }

}
