import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Alarm {
    private final String title; //debe ser el mismo titulo que un evento o tarea existente


    private final LocalDateTime goOffTime;
    private final LocalDateTime eventDate;
    private final int minBefore;
    private final Effect effect;

    //PRE: goOffTime es anterior a eventDate
    public Alarm(LocalDateTime goOffTime, Effect effect, String title, LocalDateTime eventDate){
        this.title = title;
        this.goOffTime = goOffTime;
        this.effect = effect;
        this.eventDate = eventDate;
        this.minBefore = calculateMinBefore();

    }

    public Alarm(int minBefore, Effect effect, String title, LocalDateTime eventDate){
        this.title = title;
        this.minBefore = minBefore;
        this.effect = effect;
        this.eventDate = eventDate;
        this.goOffTime = calculateGoOffTime();
    }

    private int calculateMinBefore(){
        return (int)this.goOffTime.until(eventDate, ChronoUnit.MINUTES);
    }

    public int getMinBefore(){ //para probar
        return minBefore;
    }
    public Alarm cloneAlarm(LocalDateTime eventDate){
        Alarm alarm = new Alarm(minBefore, effect, title, eventDate);
        return alarm;
    }

    public LocalDateTime getGoOffTime() {
        return goOffTime;
    }

    private LocalDateTime calculateGoOffTime(){
        return this.eventDate.minusMinutes(this.minBefore);
    }

    public boolean shouldTrigger(){
        return (LocalDateTime.now().isEqual(this.goOffTime));
    }

    public boolean shouldTrigger(LocalDateTime date){
        return (date.isEqual(this.goOffTime));
    }

    public void trigger(){
        effect.produceEffect();
    }


}
