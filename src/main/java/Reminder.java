import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Reminder{
    protected String title;

    protected String description;

    protected boolean completeDay;

    protected final ArrayList<Alarm> alarms;


    protected LocalDateTime startDate;


    protected Reminder(String title, String description, boolean completeDay, LocalDateTime startDate) {
        this.title = title;
        this.description = description;
        this.completeDay = completeDay;
        this.alarms = new ArrayList<>();
        this.startDate = startDate;

    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean isCompleteDay() {
        return completeDay;
    }

    public ArrayList<Alarm> getAlarms(){
        return this.alarms;
    }

    public void removeAlarm(Alarm alarm){
        this.alarms.remove(alarm);
    }

    public void addAlarm(Alarm alarm){
        this.alarms.add(alarm);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleteDay(boolean completeDay) {
        this.completeDay = completeDay;
    }

    public void setStartDate(LocalDateTime endDate){
        this.startDate = startDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    abstract public void makeReminderCompleteDay();

    abstract public void changeCompleteDay(LocalDateTime endDate);

    abstract public  Reminder repeatReminder(LocalDateTime startDate);

    abstract public Reminder addRepetitionByDate(Reminder reminder, LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy);

    abstract public Reminder addOcurrencesRepetition(Reminder reminder, int ocurrences, FrequencyStrategy frequencyStrategy);

    abstract public Reminder addInfiniteRepetition(Reminder reminder, FrequencyStrategy frequencyStrategy);

    abstract public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2);


}
