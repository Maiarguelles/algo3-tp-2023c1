import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;

public class Event {
    protected LocalDateTime startDate;

    protected LocalDateTime endDate;
    private String title;
    private String description;
    private boolean completeDay; //si completeDay es true, la hora de inicio debe ser 00:00
    private final ArrayList<Alarm> alarms;
    protected boolean isRepeating;
    protected FrequencyStrategy frequencyStrategy;


    public Event(String title, String description, LocalDateTime startDate,LocalDateTime endDate, boolean completeDay){
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.alarms = new ArrayList<Alarm>();
        this.isRepeating = false;
        this.frequencyStrategy = null;
        this.completeDay = completeDay;
        if(completeDay)
            this.endDate = startDate.plusHours(24);
        else
            this.endDate = endDate;

    }


    public Event repeatEvent(LocalDateTime startDate){
        var eventDuration = this.getDuration();
        var eventRepetition = new Event(title, description, startDate, startDate.plusMinutes(eventDuration), isCompleteDay());
        for (Alarm alarm : alarms) {
            eventRepetition.addAlarm(alarm.cloneAlarm(startDate));
        }
        return eventRepetition;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration(){
        if(isCompleteDay())
            return 1440;
        else
            return (int)this.startDate.until(endDate, ChronoUnit.MINUTES);
    }

    public boolean isCompleteDay() {
        return completeDay;
    }


    public LocalDateTime getStartDate() {
        return startDate;
    }


    public ArrayList<Alarm> getAlarms(){
        return alarms;
    }

    public void addAlarm(Alarm alarm){
        alarms.add(alarm);
    }

    public void removeAlarm(Alarm alarm){
        alarms.remove(alarm);
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

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }



    public boolean isRepeating() {
        return isRepeating;
    }

    public boolean addDailyFrequency(int frequency){
        if(isRepeating) {
            frequencyStrategy = new DailyStrategy(frequency);
            return true;
        }
        return false;
    }
    public boolean addWeeklyFrequency(ArrayList<DayOfWeek> weekDays){
        if(isRepeating) {
            frequencyStrategy = new WeeklyStrategy(weekDays);
            return true;
        }
        return false;
    }

    public boolean addMonthlyFrequency(){
        if(isRepeating) {
            frequencyStrategy = new MonthlyStrategy();
            return true;
        }
        return false;
    }

    public boolean addYearlyFrequency(){
        if(isRepeating) {
            frequencyStrategy = new YearlyStrategy();
            return true;
        }
        return false;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public ArrayList<LocalDateTime> showDatesOfEvent(LocalDateTime date1, LocalDateTime date2){
        return null;
    }


}
