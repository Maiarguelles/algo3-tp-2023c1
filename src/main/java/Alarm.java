import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Alarm {
    private final String description;

    private final LocalDateTime goOffTime;
    private final int minBefore;
    private final Effect effect;

    //PRE: goOffTime es anterior a eventDate
    public Alarm(LocalDateTime goOffTime, Effect effect, String description, LocalDateTime eventDate){
        this.description = description;
        this.goOffTime = goOffTime;
        this.effect = effect;
        this.minBefore = calculateMinBefore(eventDate);

    }

    public Alarm(int minBefore, Effect effect, String description, LocalDateTime eventDate){
        this.description = description;
        this.minBefore = minBefore;
        this.effect = effect;
        this.goOffTime = calculateGoOffTime(eventDate);
    }

    private int calculateMinBefore(LocalDateTime eventDate){
        return (int)this.goOffTime.until(eventDate, ChronoUnit.MINUTES);
    }

    public int getMinBefore(){ //para probar
        return minBefore;
    }
    public Alarm cloneAlarm(LocalDateTime eventDate){
        Alarm alarm = new Alarm(minBefore, effect, description, eventDate);
        return alarm;
    }

    public LocalDateTime getGoOffTime() {
        return goOffTime;
    }

    private LocalDateTime calculateGoOffTime(LocalDateTime eventDate){
        return eventDate.minusMinutes(this.minBefore);
    }

    public boolean shouldTrigger(){
        return (LocalDateTime.now().isEqual(this.goOffTime));
    }

    public boolean shouldTrigger(LocalDateTime date){
        return (date.isEqual(this.goOffTime));
    }

    public Effect.typeOfEffect  trigger(){
       return  effect.produceEffect();
    }


}

