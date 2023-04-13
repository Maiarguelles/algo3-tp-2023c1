import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleteDay() {
        return completeDay;
    }

    public int getDuration() {
        return duration;
    }

    private String title;
    private String description;

    private boolean completeDay; //si completeDay es true, la hora de inicio debe ser 00:00

    public LocalDateTime getStartDate() {
        return startDate;
    }

    private LocalDateTime startDate;

    private int duration;

    public Repetition getRepetition() {
        return repetition;
    }

    private Repetition repetition;

    private final ArrayList<Alarm> alarms;


    public Event(String title, String description, boolean completeDay, LocalDateTime startDate, int duration, Repetition repetition) {
        this.title = title;
        this.description = description;
        this.completeDay = completeDay;
        this.startDate = startDate;
        this.duration = duration;
        this.repetition = repetition;
        this.alarms = new ArrayList<Alarm>();

    }

    public ArrayList<Alarm> getAlarms(){
        return alarms;
    }
    public void addAlarm(){}

    public void removeAlarm(){}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleteDay(boolean completeDay) {
        this.completeDay = completeDay;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }

    public ArrayList<LocalDateTime> showDatesOfEvent(LocalDateTime startDate, LocalDateTime endDate){
        return repetition.showDatesOfEvents(startDate, endDate);
    }




}
