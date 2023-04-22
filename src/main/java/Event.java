import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;

public class Event extends  Reminder{

    protected LocalDateTime endDate;
    protected boolean isRepeating;
    protected FrequencyStrategy frequencyStrategy;


    public Event(String title, String description, LocalDateTime startDate,LocalDateTime endDate, boolean completeDay){
        super(title, description, completeDay, startDate);
        isRepeating = false;
        frequencyStrategy = null;
        this.endDate= endDate;

    }


    public Reminder repeatReminder(LocalDateTime startDate){
        var eventDuration = this.getDuration();
        var eventRepetition = new Event(title, description, startDate, startDate.plusMinutes(eventDuration), isCompleteDay());
        for (Alarm alarm : alarms) {
            eventRepetition.addAlarm(alarm.cloneAlarm(startDate));
        }
        return eventRepetition;
    }


    public int getDuration(){
        if(isCompleteDay())
            return 1440;
        else
            return (int)this.startDate.until(endDate, ChronoUnit.MINUTES);
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

    public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2){
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        if ((startDate.isBefore(date2) && startDate.isAfter(date1))
                || (endDate.isAfter(date1) && endDate.isBefore(date2)))
            dates.add(startDate);

        return dates;
    }
}
