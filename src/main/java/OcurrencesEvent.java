import java.time.LocalDateTime;

public class OcurrencesEvent extends Event{
    private int ocurrences;
    public OcurrencesEvent(String title, String description, LocalDateTime startDate,LocalDateTime endDate, boolean completeDay, int ocurrences){
        super(title, description, startDate, endDate, completeDay);
        isRepeating = true;
        this.ocurrences = ocurrences;
    }
}
