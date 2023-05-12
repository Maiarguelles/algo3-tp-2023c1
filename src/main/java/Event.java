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


    public void addFrequency(FrequencyStrategy frequencyStrategy){
        return;
    }


    public void changeCompleteDay(LocalDateTime endDate){
        completeDay = false;
        this.endDate = endDate;
    }

    public void makeReminderCompleteDay(){
        completeDay = true;
        this.endDate = LocalDateTime.of(this.endDate.plusDays(1).toLocalDate(), LocalTime.of(0,0));
    }


    public Event addOcurrencesRepetition(int ocurrences, FrequencyStrategy frequencyStrategy){
        Event event = new OcurrencesEvent(title, description, startDate, endDate, completeDay, ocurrences);
        event.addFrequency(frequencyStrategy);
        return event;
    }


    public Event addInfiniteRepetition(FrequencyStrategy frequencyStrategy) {
        Event event = new InfiniteEvent(title, description, completeDay,startDate, endDate);
        event.addFrequency(frequencyStrategy);
        return event;
    }


    public Event addRepetitionByDate(LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy) {
        Event event = new ByDateEvent(title, description, startDate, endDate, completeDay, expirationDate);
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
