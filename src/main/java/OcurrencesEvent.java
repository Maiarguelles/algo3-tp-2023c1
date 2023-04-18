import java.time.LocalDateTime;

public class OcurrencesEvent extends Event{
    private int ocurrences;
    public OcurrencesEvent(String title, String description, boolean completeDay, LocalDateTime startDate, int duration, int ocurrences){
        super(title, description, completeDay, startDate, duration);
        isRepeating = true;
        this.ocurrences = ocurrences;
    }
}
