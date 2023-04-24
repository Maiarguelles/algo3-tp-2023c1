import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;

public class Calendar {

    private ArrayList<Reminder> reminders;
    private Alarm nextAlarm;

    public Calendar(){
        this.reminders = new ArrayList<Reminder>();
        nextAlarm = null;
    }

    public boolean addReminder(Reminder reminder){
        if(reminder == null)
            return false;
        reminders.add(reminder);
        var alarms = reminder.getAlarms();
        if(alarms != null)
            updateNextAlarm();
        return true;
    }

    public void deleteReminder(Reminder reminder){
        if(reminder.getAlarms().contains(nextAlarm)){
            nextAlarm = null;
            reminders.remove(reminder);
            updateNextAlarm();
        }
        else
            reminders.remove(reminder);
    }


    private void updateNextAlarm(){
        Alarm alarm = null;
        for (Reminder reminder : reminders) {
            for (int j = 0; j < reminder.getAlarms().size(); j++) {
                alarm = reminder.getAlarms().get(j);
                if (nextAlarm == null)
                    nextAlarm = alarm;
                else if (alarm.getGoOffTime().isBefore(nextAlarm.getGoOffTime()))
                    nextAlarm = alarm;
            }
        }

    }

    public LocalDateTime nextAlarm(){
        if(nextAlarm != null)
            return nextAlarm.getGoOffTime();
        else
            return null;
    }


    public boolean searchReminder(Reminder reminder){
        Reminder searchedReminder = null;
        for (Reminder existentReminder : reminders) {
            if (existentReminder.equals(reminder)) {
                searchedReminder = existentReminder;
                return true;
            }
        }
        return false;
    }


    public boolean addAlarmToExistentReminder(Reminder reminder, Alarm alarm){
        if(reminder == null)
            return false;

        if(searchReminder(reminder)) {
            reminder.addAlarm(alarm);
            updateNextAlarm();
            return true;
        }

        return false;
    }

    public Event addRepetitionByDateToExistentEvent(Event event, LocalDateTime expirationDate, FrequencyStrategy frequencyStrategy){
        if(!searchReminder(event))
            return null;
        var newReminder = event.addRepetitionByDate(expirationDate, frequencyStrategy);
        reminders.remove(event);
        reminders.add(newReminder);
        return newReminder;
    }

    public Event addOcurrencesRepetitionToExistentEvent(Event event, int ocurrences, FrequencyStrategy frequencyStrategy){
        if(!searchReminder(event))
            return null;
        var newReminder = event.addOcurrencesRepetition(ocurrences, frequencyStrategy);
        reminders.remove(event);
        reminders.add(newReminder);
        return newReminder;
    }

    public Event addInfiniteRepetitionToExistentEvent(Event event, FrequencyStrategy frequencyStrategy) {
        if (!searchReminder(event))
            return null;
        var newReminder = event.addInfiniteRepetition(frequencyStrategy);
        reminders.remove(event);
        reminders.add(newReminder);
        return newReminder;
    }


    public boolean existReminder(LocalDate date){
        Reminder reminder = null;
        for (Reminder value : reminders) {
            reminder = value;
            if (date.isEqual(reminder.getStartDate().toLocalDate()))
                return true;
        }
        return false;
    }


    public ArrayList<Reminder> remindersBetweenTwoDates(LocalDateTime date1, LocalDateTime date2){
        var reminders = new ArrayList<Reminder>();
        var dates = new ArrayList<LocalDateTime>();
        for (Reminder reminder : this.reminders){
            dates = reminder.showDatesOfReminder(date1, date2);
            if (dates == null)
                continue;
            for (int i = 0; i< dates.size(); i++){
                reminders.add(reminder.repeatReminder(dates.get(i)));

            }
        }
        return reminders;
    }


}

















