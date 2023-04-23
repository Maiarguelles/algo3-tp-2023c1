import javax.swing.border.EmptyBorder;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;

public class Event extends Reminder{

    protected boolean isRepeating;

    protected LocalDateTime endDate;
    protected FrequencyStrategy frequencyStrategy;


    public Event(String title, String description, LocalDateTime startDate,LocalDateTime endDate, boolean completeDay){
        super(title, description, completeDay, startDate);
        isRepeating = false;
        frequencyStrategy = null;
        if(completeDay && (endDate==(null))){
            LocalDate realDate = startDate.plusDays(1).toLocalDate();
            this.endDate = LocalDateTime.of(realDate, LocalTime.of(0,0)); // no es lo mas lindo pero bueno

        }
        else
            this.endDate= endDate;

    }


    public Event repeatReminder(LocalDateTime startDate){
        var eventDuration = this.getDuration();
        var eventRepetition = new Event(title, description, startDate, startDate.plusMinutes(eventDuration), isCompleteDay());
        for (Alarm alarm : alarms) {
            eventRepetition.addAlarm(alarm.cloneAlarm(startDate));
        }
        return eventRepetition;
    }



    public int getDuration(){
        if(isCompleteDay()){
            LocalDateTime date1= LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(0,0));
            LocalDateTime date2 = LocalDateTime.of(endDate.toLocalDate(), LocalTime.of(0,0));
            return (int) date1.until(date2, ChronoUnit.MINUTES);
        }

        else
            return (int)this.startDate.until(endDate, ChronoUnit.MINUTES);
    }


    public LocalDateTime getEndDate(){
        return this.endDate;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    /*public boolean addDailyFrequency(int frequency){
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
    }*/


    public void addFrequency(FrequencyStrategy frequencyStrategy){
        this.frequencyStrategy = frequencyStrategy;
    }


    public void changeCompleteDay(LocalDateTime endDate){
        completeDay = false;
        this.endDate = endDate;
    }

    public void makeReminderCompleteDay(){
        completeDay = true;
        this.endDate = LocalDateTime.of(this.endDate.plusDays(1).toLocalDate(), LocalTime.of(0,0));
    }


    public Event addOcurrencesRepetition(Reminder reminder, int ocurrences, FrequencyStrategy frequencyStrategy){
        var event = new OcurrencesEvent(reminder.getTitle(), reminder.getDescription(), reminder.getStartDate(), reminder.getEndDate(), reminder.isCompleteDay(), ocurrences);
        event.addFrequency(frequencyStrategy);
        return event;
    }


    public Event addInfiniteRepetition(Reminder reminder, FrequencyStrategy frequencyStrategy) {
        var event = new InfiniteEvent(reminder.getTitle(), reminder.getDescription(), reminder.isCompleteDay(), reminder.getStartDate(), reminder.getEndDate());
        event.addFrequency(frequencyStrategy);
        return event;
    }

    @Override
    public Reminder addRepetitionByDate(Reminder reminder, LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy) {
        var event = new ByDateEvent(reminder.getTitle(), reminder.getDescription(), reminder.getStartDate(), reminder.getEndDate(), reminder.isCompleteDay(), expirationDate);
        event.addFrequency(frequencyStrategy);
        return event;
    }



    public ArrayList<LocalDateTime> showDatesOfReminder(LocalDateTime date1, LocalDateTime date2){
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        if ((startDate.isBefore(date2) && startDate.isAfter(date1))
                || (endDate.isAfter(date1) && endDate.isBefore(date2)))
            dates.add(startDate);

        return dates;
    }
}
