import java.time.LocalDate;
import java.time.LocalDateTime;

public class Alarm {
    private final String title; //debe ser el mismo titulo que un evento o tarea existente
    private final LocalDateTime goOffTime;
    private final LocalDateTime eventDate;
    private final int minBefore;
    private final Effect effect;

    public Alarm(LocalDateTime goOffTime, Effect effect, String title){
        this.title = title;
        this.goOffTime = goOffTime;
        this.minBefore = 0;
        this.effect = effect;
        this.eventDate = null;
    }

    public Alarm(int minBefore, Effect effect, String title, LocalDateTime eventDate){
        this.title = title;
        this.minBefore = minBefore;
        this.effect = effect;
        this.eventDate = eventDate;
        this.goOffTime = calculateGoOffTime();
    }

    public LocalDateTime calculateGoOffTime(){
        return null;
    }

    public boolean shouldTrigger(){
        return false;
    }

    public void trigger(){}


}
