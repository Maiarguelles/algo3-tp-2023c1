import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Event extends Reminder{

    protected LocalDateTime endDate;



    public Event(String title, String description, LocalDateTime startDate,LocalDateTime endDate, boolean completeDay){
        super(title, description, completeDay, startDate);
        if(completeDay && (endDate==(null))){
            LocalDate realDate = startDate.plusDays(1).toLocalDate();
            this.endDate = LocalDateTime.of(realDate, LocalTime.of(0,0)); // no es lo mas lindo pero bueno

        }
        else
            this.endDate= endDate;

    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != Event.class)
            return false;

        if(o == null)
            return false;

        if(this == o)
            return false;

        boolean isEqual = true;

        if(!(this.title.equals(((Event) o).title)))
            isEqual = false;
        if(!(this.description.equals(((Event) o).description)))
            isEqual = false;
        if(!(this.completeDay == ((Event) o).completeDay))
            isEqual = false;
        if(!(this.startDate.equals(((Event) o).startDate)))
            isEqual = false;
        if(!(this.endDate.equals(((Event) o).endDate)))
            isEqual = false;
        if(!(this.alarms.equals(((Event) o).alarms)))
            isEqual = false;

        return isEqual;
    }

    @Override
    public int hashCode(){
        int x = 31;
        if (completeDay){
            x+=42;
        }
        int y = 1;
        for (int i = 0; i<title.length(); i++){
            y+= title.charAt(i);
        }

        int z = 0;
        for (int i = 0; i<description.length(); i++){
            z+= description.charAt(i);
        }

        int date1 = startDate.getMonthValue()*startDate.getDayOfMonth()*startDate.getYear();
        int date2 = endDate.getMonthValue()+endDate.getDayOfMonth()*endDate.getYear();

        return date1*x+date2*y+z;
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
